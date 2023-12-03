package comp1110.ass2;

import java.util.Random;

// Yubeng
public class Dice {
    private final Random r = new Random();

    // Dice bounds (inclusive)
    private int lowerBound; //Lower bound added to make dice more customizable
    private int upperBound; //Maximum limit of dice roll

    public Dice(int lowerBound, int upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    // Adjust the bounds after dice is created
    public void updateLowerBound(int lowerBound){
        this.lowerBound = lowerBound;
    }

    public void updateUpperBound(int upperBound){
        this.upperBound = upperBound;
    }

    // Find the range of the dice roll
    public int diceRange() {
        return upperBound - lowerBound;
    }

    // Roll the dice, resulting in random int between lower and upper bound (inclusive)
    // e.g. a dice roll of between 1 - 3 can produce 1,2 or 3.
    public int rollDice(){
        return r.nextInt(this.diceRange()) + lowerBound;
    }

}
