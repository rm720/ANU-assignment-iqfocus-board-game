package comp1110.ass2;

import java.util.ArrayList;

public class RandomChallenge {

    public static void RandomChallenge() {

        Dice tileColour = new Dice(0,4);
        ArrayList<Cell> challenge = new ArrayList<>();
        String colour = new String();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = tileColour.rollDice();
                int[] xyc = {i, j, x};
                Cell c = new Cell(xyc);
                challenge.add(c);

                colour = colour+Cell.decodeColour(x);
            }
        }
        System.out.println(challenge);
        System.out.println(colour);

    }

    public static void main(String[] args) {
        RandomChallenge();
    }

}
