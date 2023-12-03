package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Set;
import java.util.TreeSet;

import static comp1110.ass2.Solution.SOLUTIONS;
import static org.junit.Assert.assertTrue;

public class SolutionsTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(120000);


    private void test(String objective, Set<String> expected) {
        String out = FocusGame.getSolution(objective);
        assertTrue("No solutions returned for objective " + objective + ", expected " + expected, out != null);
        TreeSet<String> outSet = new TreeSet<>();
        for (int i = 0; i < out.length(); i += 4) {
            outSet.add(out.substring(i, i + 4));
        }
        String expstr = expected.toString();
        String outstr = outSet.toString();
        assertTrue("For objective " + objective + ", was expecting " + expstr + ", but got " + outstr, expstr.equals(outstr));
    }

    @Test
    public void test_starter() {
        for (int i = 0; i < (SOLUTIONS.length / 5); i++) {
            TreeSet<String> outSet = new TreeSet<>();
            for (int j = 0; j < SOLUTIONS[i].placement.length(); j += 4) {
                outSet.add(SOLUTIONS[i].placement.substring(j, j + 4));
            }
            test(SOLUTIONS[i].objective, outSet);
        }
    }

    @Test
    public void test_junior() {
        for (int i = (SOLUTIONS.length / 5); i < (2 * (SOLUTIONS.length / 5)); i++) {
            TreeSet<String> outSet = new TreeSet<>();
            for (int j = 0; j < SOLUTIONS[i].placement.length(); j += 4) {
                outSet.add(SOLUTIONS[i].placement.substring(j, j + 4));
            }
            test(SOLUTIONS[i].objective, outSet);
        }
    }

    @Test
    public void test_expert() {
        for (int i = (2 * (SOLUTIONS.length / 5)); i < (3 * (SOLUTIONS.length / 5)); i++) {
            TreeSet<String> outSet = new TreeSet<>();
            for (int j = 0; j < SOLUTIONS[i].placement.length(); j += 4) {
                outSet.add(SOLUTIONS[i].placement.substring(j, j + 4));
            }
            test(SOLUTIONS[i].objective, outSet);
        }
    }

    @Test
    public void test_master() {
        for (int i = (3 * (SOLUTIONS.length / 5)); i < (4 * (SOLUTIONS.length / 5)); i++) {
            TreeSet<String> outSet = new TreeSet<>();
            for (int j = 0; j < SOLUTIONS[i].placement.length(); j += 4) {
                outSet.add(SOLUTIONS[i].placement.substring(j, j + 4));
            }
            test(SOLUTIONS[i].objective, outSet);
        }
    }

    @Test
    public void test_wizard() {
        for (int i = (4 * (SOLUTIONS.length / 5)); i < SOLUTIONS.length; i++) {
            TreeSet<String> outSet = new TreeSet<>();
            for (int j = 0; j < SOLUTIONS[i].placement.length(); j += 4) {
                outSet.add(SOLUTIONS[i].placement.substring(j, j + 4));
            }
            test(SOLUTIONS[i].objective, outSet);
        }
    }
}
