package comp1110.ass2;


import comp1110.ass2.gui.Board;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class AllChallenge {

    // Ra u6650903
    // complexity chalenges map. challenges are sorted by 3 complexity levels: (1,2,3)
    public static HashMap<Integer, HashSet<String>> challengeFinder = new HashMap<>();


    // Ra u6650903
    // save hashset with challenges and solutions into csv file
    public static void saver() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("challengeLibraryNew.csv", "UTF-8");
        String record = "";
        for (Map.Entry<String, HashSet<String>> entry : challengesWithSolutionNew.entrySet()) {
            record = entry.getKey() + ":" + setToString(entry.getValue());
            writer.println(record);
        }
        writer.close();
    }

    // Ra u6650903
    // transforms hashset of strings into coma separated strings
    public static String setToString(HashSet<String> hashset) {
        String result = "";
        for (String s : hashset) {
            if (s.length() != 0) {
                if (result.length() == 0)
                    result = s;
                else
                    result = result + "," + s;
            }
        }
        return result;
    }


    // Ra 6650903
    // reads from file back to HashMap
    public static HashMap<String, HashSet<String>> fileToMapReader() {
        HashMap<String, HashSet<String>> result = new HashMap<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("challengeLibrary.csv"));
            String[] values;
            String line;
            while (((line = in.readLine()) != null)) {
                values = line.split(":");
                String challenge = values[0];
                String solutions = values[1];
                result.put(challenge, stringToSet(solutions));
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Ra u6650903
    // initialises the hashmap solution dictionary (HashMap <challenge, set of solutions>)
    // initialises dictionary of complexity tasks (HashMap <complexity, challenge>)
    // lowest number is 1 refers to highest complexity because it means challenge has only one solution
    public static void initialiseSolutionMap() {
        challengesWithSolution = fileToMapReader();
        for (Map.Entry<String, HashSet<String>> entry : challengesWithSolution.entrySet()) {
            int numberOfSolutions = entry.getValue().size();
            int complexityLevel =0;
            if (numberOfSolutions>=10) complexityLevel = 4; // challenges have 10 or more solutions
            if ((numberOfSolutions>=6)&&(numberOfSolutions<10)) complexityLevel = 3; // challenges have 6-9 or more solutions
            if ((numberOfSolutions>1)&&(numberOfSolutions<6)) complexityLevel = 2; // challenges have 2-5 solutions
            if (numberOfSolutions ==1) complexityLevel = 1; //challenges have a unique solution
            String challenge = entry.getKey();
            if (challengeFinder.containsKey(complexityLevel)) {
                HashSet<String> temp = challengeFinder.get(complexityLevel);
                temp.add(challenge);
                challengeFinder.put(complexityLevel, temp);
            }
            else {
                HashSet<String> temp = new HashSet<>();
                temp.add(challenge);
                challengeFinder.put(complexityLevel, temp);
            }
        }
    }



    // Ra u6650903
    // converts string to HashSet of strings
    // string is divided by coma into different entries
    private static HashSet<String> stringToSet(String s) {
        HashSet<String> result = new HashSet<>();
        if (s.contains(",")) {
            String[] solutions = s.split(",");
            for (int i = 0; i < solutions.length; i++) {
                result.add(solutions[i]);
            }
        }
        else result.add(s);
        return result;
    }

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    // Yubeng
    // find all the challenges and solutions
    public static HashMap<String, HashSet<String>> challengesWithSolution = new HashMap<>();
    public static HashMap<String, HashSet<String>> challengesWithSolutionNew = new HashMap<>();
    public static void AllChallenge() {
        challengesWithSolution = fileToMapReader();
        //System.out.println(challengesWithSolution);

        String digits = "";
        String colours = "";

        // Ra u6650903
        // termination by time limit
        int runForHours = 100; // for how many hours to run the code
        long start = System.currentTimeMillis();
        long end = start + runForHours*60*60*1000; // 60 seconds * 1000 ms/sec

        // Yubeng
        // generates all possible challenges composing colors from 1 to 4, and the total number of all challenges are 4 to the power of 9
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k = 1; k <= 4; k++) {
                    for (int l = 1; l <= 4; l++) {
                        for (int m = 1; m <= 4; m++) {
                            for (int n = 1; n <= 4; n++) {
                                for (int o = 1; o <= 4; o++) {
                                    for (int p = 1; p <= 4; p++) {
                                        for (int q = 1; q <= 4; q++) {

                                            digits = ""+i + j + k + l + m + n + o + p + q;
                                            int currentLevel = Integer.parseInt(digits);
                                            if (currentLevel >= 114233311) {

                                                // decodes color of each letter
                                                for (int r = 0; r < digits.length(); r++) {
                                                    colours = colours + Cell.decodeColour((Integer.parseInt("" + digits.charAt(r))));
                                                }

                                                //System.out.println(colours);
                                                //boolean alredyHaveIt = challengesWithSolution.containsKey(colours);
                                                //System.out.println(colours + " alredy have -  " alredyHaveIt);


                                                // generates solution(s) of  a challenge and puts them in a set
                                                HashSet<String> solutions = new HashSet<>();
                                                solutions = FocusGame.getSolutions(colours);
                                                if (solutions.size() != 0) {
                                                    challengesWithSolutionNew.put(colours, FocusGame.getSolutions(colours));
                                                    System.out.println(colours + ":" + solutions);
                                                }
                                                // Ra u6650903
                                                // termination by time limit
                                                //if(System.currentTimeMillis() >= end) return;


                                                colours = "";
                                                // Yubeng
                                            }


                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    //u6918231 Baoyu Ma Another way to find all solutions, because it has 4^9 kinds of challenges, so we could transfer challenges to Quad, and this just avoid 9 loops, just for looking concise, not really optimise codes.
    public static String getDigits(int num) {
        String digits = Integer.toString(num, 4);
        String zero = "0";
        int n = digits.length();
        for (int i = 0; i < 9 - n; i++) {
            digits = zero.concat(digits);
        }
        char[] digit = digits.toCharArray();
        for (int i = 0; i < digits.length(); i++) {
            digit[i] = (char)(digit[i]+1);
        }
        return String.valueOf(digit);
    }
    public static void AllChallenge2(int n) {

        String digits = "";
        String colours = "";
        FocusGame nfg = new FocusGame();

        // generates all possible challenges composing colors from 1 to 4, and the total number of all challenges are 4 to the power of 9
        try (FileWriter writer = new FileWriter("challengeLibrary"+n+".csv", true)) {
            for (int i = n*(int)(Math.pow(4,9)/32); i < (n+1)*(int)(Math.pow(4,9)/32); i++) {
                digits = getDigits(i);
                // decodes color of each letter
                colours = "";
                for (int r = 0; r < digits.length(); r++) {
                    colours = colours + Cell.decodeColour((Integer.parseInt("" + digits.charAt(r))));
                }

                // generates solution(s) of  a challenge and puts them in a set
                HashSet<String> solutions = new HashSet<>();
                solutions = nfg.getSolutions(colours);
                if (solutions.size() != 0) {
                    writer.write(colours + ",");
                    challengesWithSolution.put(colours, nfg.getSolutions(colours));
                    System.out.println(colours + ":" + solutions);
                    for (String solution : solutions) {
                        writer.write(solution + ",");
                    }
                    writer.write("\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Ra u6650903
    // draw a challenge picture
    public static Group drawChallenge(String challenge, int x0, int y0, int scale){
        // Cell (grid on the empty board)
        int cellSize = 40;
        int gapWidth = 10;
        int startX = x0 + gapWidth;
        int startY = y0 + gapWidth;
        HashSet<Rectangle> result = new HashSet<>();
        Group resultGroup = new Group();
        if (challenge.length() != 9) {challenge = "DDDDDDDDD";}
            ArrayList<Color> colours = Cell.challengeToCol(challenge);
            int colNumber = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int coordX = startX + j * (cellSize + gapWidth);
                    int coordY = startY + i * (cellSize + gapWidth);
                    Rectangle rec = new Rectangle(coordX, coordY, cellSize, cellSize);
                    rec.setArcWidth(gapWidth);
                    rec.setArcHeight(gapWidth);
                    rec.setFill(colours.get(colNumber));
                    result.add(rec);
                    colNumber++;
                }
            }
        Rectangle backpplate = new Rectangle(x0,y0,cellSize*3+gapWidth*4,cellSize*3+gapWidth*4 );
        backpplate.setFill(Color.rgb(189,189,189));
        backpplate.setArcWidth(gapWidth);
        backpplate.setArcHeight(gapWidth);
        resultGroup.getChildren().add(backpplate);
        resultGroup.getChildren().addAll(result);
        return resultGroup;
    }


    // Ra u6650903
    // finding a good hint tile
    public static String getHint(String challenge, HashSet<String> curentState){
        String currenPlacement = Board.setToString(curentState);
        String result = "";
        HashSet<String> solutions = challengesWithSolution.get(challenge);
        ArrayList<HashSet<String>> solutionsSets = new ArrayList<>();
        // for every solution string in solutions set
        for (String solution : solutions){
            //split string up into the tiles and put into a tile set
            HashSet<String> placements = new HashSet<>();
            for (int i = 0; i<solution.length(); i=i+4){
                String tile = solution.substring(i,i+4);
                // we take only those ones which player did not put on the board already
                if (!curentState.contains(tile))
                placements.add(tile);
            }
            // add solution (tile set) into Arraylist of solutions set
            solutionsSets.add(placements);
        }

        // sort to find closest solution to what player did, i.e. smallest set of tiles
        Collections.sort(solutionsSets, new Comparator<HashSet<String>>() {
            @Override
            public int compare(HashSet<String> o1, HashSet<String> o2) {
                return Integer.valueOf(o1.size()).compareTo(o2.size());
            }
        });

        for (HashSet<String> plasements : solutionsSets){
            for (String placement : plasements){
                String glued = currenPlacement + placement;
                boolean isHintGood = FocusGame.isPlacementStringValid(glued);
                if (isHintGood){
                    //result = placement;
                    return placement;
                }
            }
        }
        return  result;

    }


    // Ra u6650903
    // finds several hints
    // recursively calls findHint method
    public static HashSet<String> getHints(int counter, String challenge, HashSet<String> currentHints){
        //HashSet<String> result = new HashSet(currentHints);
        if (counter > 0){
            String hint = getHint(challenge,currentHints);
            if (!hint.isEmpty()) {
                currentHints.add(hint);
                return getHints(counter - 1, challenge, currentHints);
            }
        }
        return currentHints;
    }


    // Ra u6650903
    // for testing the operation
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        initialiseSolutionMap();
        System.out.println("hard "+challengeFinder.get(1).size());
        System.out.println("medium "+challengeFinder.get(2).size());
        System.out.println("easy "+challengeFinder.get(3).size());
        System.out.println("too easy "+challengeFinder.get(4).size());

    }







}
