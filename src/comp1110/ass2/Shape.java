package comp1110.ass2;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

// Ra u6650903
// abstract class for shapes
public abstract class Shape {

    // class properties
    public  int x; // column
    public int y; // row
    public int colour; // colour
    public int[] coords = {x,y};

    // class methods
    public static double distance(double x, double y, Rectangle cell) {
        return 0;
    }

    public abstract Rectangle draw(int size, int shiftX, int shiftY);
    public abstract void shift(int shiftX, int shiftY);
    public abstract boolean isEqual(Cell otherCell);
    public abstract void turn90deg();
    public abstract String coordsToString();
    public abstract boolean isCellconsistentToCell(Cell other);

    public static int encodeColour(char c) {
        return 0;
    }

    public static char decodeColour(int c) {
        return 0;
    }

}


