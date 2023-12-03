package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.TestUtility.*;
import static org.junit.Assert.assertTrue;

public class Testfor6 {
    // Original duration < 0.05 sec * 10 = 0.5 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(10000);

    private void test(String start, String objective, int xLoc, int yLoc, String expected) {
        Set<String> outSet = FocusGame.getViablePiecePlacements(start, objective, xLoc, yLoc);
        Boolean isvalid = false;
        if(outSet!=null) {
            for (String s : outSet) {
                if(s.equals(expected))
                    isvalid = true;
            }
            assertTrue(  start + ", but expected " + expected, isvalid);
        }

    }

    /* This test is to use the ViablePlacement, at firs, we remove a solution of the all solutions,and then we use
    *  the test to find the removed solutio, if we can find it, the test pass, and test5 is right
    */

    @Test
    public void test_lastP() {
        for (int i = 0; i<SOLUTIONS.length;i++){
            int r = (int)(Math.random()*10.0);
            String str1 = SOLUTIONS[i].placement.substring(0,r*4);
            String str2 = SOLUTIONS[i].placement.substring(r*4+4,40);
            String result = SOLUTIONS[i].placement.substring(r*4,r*4+4);

            String testStr = str1+str2;
            test(testStr, SOLUTIONS[i].objective, result.charAt(2)-'0', result.charAt(1)-'0', result);
        }
    }
}
