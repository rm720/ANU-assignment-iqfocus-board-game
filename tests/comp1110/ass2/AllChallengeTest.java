package comp1110.ass2;

import org.junit.Test;

import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

public class AllChallengeTest {

    // Ra u6650903
    // testing if all the solutions comply with the centre colours requirements
    @Test
    public void testAllSolutions() {
        BoardState testBoard = new BoardState();
        AllChallenge mychall = new AllChallenge();
        mychall.initialiseSolutionMap();
        String challenge;
        String colours;
        for (Map.Entry<String, HashSet<String>> entry : mychall.challengesWithSolution.entrySet()) {
            challenge = entry.getKey(); // + ":" + setToString(entry.getValue());
            for (String solution : entry.getValue()){
                testBoard.zeroBoard();
                testBoard.updateBoard(solution);
                colours = testBoard.middleSquare();
                assertTrue("Expected " + challenge + ", but got " + colours, challenge.equals(colours));
            }
        }
    }

}

