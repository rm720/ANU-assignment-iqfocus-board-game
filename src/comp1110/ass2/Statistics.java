package comp1110.ass2;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

// Ra u6650903
// challenges statistics
public class Statistics extends Application {

    public static void distributionHash(HashMap<String, HashSet<String>> data) {
        int[] bkts = new int[20];
        for (Map.Entry<String, HashSet<String>> entry : data.entrySet()) {
            int numberOfSolutions = entry.getValue().size();
            System.out.println();
            bkts[numberOfSolutions]++;
        }

        for (int i = 0; i < 20; i++) {
            System.out.println(bkts[i] + " challenges have " + i + " solutions");
        }
        new Histogram("IQ Focus", bkts, Color.GREEN).show();
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
		AllChallenge.initialiseSolutionMap();
		distributionHash(AllChallenge.challengesWithSolution);
	}
	public static void main(String[] args) {
		launch(args);
	}

}

