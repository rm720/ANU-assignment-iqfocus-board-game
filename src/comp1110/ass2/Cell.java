package comp1110.ass2;


import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Ra u6650903
public class Cell extends Shape{
    public int x; // column
    public int y; // row
    public int colour; // colour
    public int[] coords = {x,y};

    // colour codes
    //    0 empty
    //    1 white
    //    2 green
    //    3 red
    //    4 blue

    public Cell(int[] xyc){  // passing an array to make int easier to instantiate a cell in tile
        this.x = xyc[0];
        this.y = xyc[1];
        this.colour = xyc[2];
    }


    // Ra u6650903
    // method returns distance from the cell of the playfield grid (rectangle)(top left corner to the given coordinates

    public static double distance(double x, double y, Rectangle cell) {

        Bounds bounds = cell.getLayoutBounds();
        double topLeftX = bounds.getMinX();
        double topLeftY = bounds.getMinY();
        double vx = topLeftX - x;
        double vy = topLeftY - y;
        double result = Math.sqrt(Math.pow(vx,2)+Math.pow(vy,2));
        return result;


    }

    public String coordsToString(){
        return "" + x + "" + y;
    }
    // Ra u6650903
    // turns cell 90 degree around the origin (0,0)
    public void turn90deg(){
        int oldx = this.x;
        int oldy = this.y;
        // new values
        this.x = -1*oldy;
        this.y = oldx;
    }
    // Ra u6650903
    // shift the cell by the input coordinates
    public void shift(int shiftX, int shiftY){
        this.x = this.x+shiftX;
        this.y = this.y+shiftY;
    }

    // Ra u6650903
    // checks if the cell is equal to the other cell
    public boolean isEqual(Cell otherCell){
        if ((otherCell.x == this.x) && (otherCell.y == this.y)) return  true;
        else return false;
    }

    //Ra u6650903
    // check if one cell contradicts the colour of the imaginary cell of the constraint.
    public boolean isCellconsistentToCell(Cell other){
        if ((this.x == other.x)&&(this.y==other.y)){
            if (this.colour != other.colour) return false;
        }
        return true;
    }

    // Ra u6650903
    // encodes colour to number: character to int code
    //    0 empty or not one of our colours
    //    1 white
    //    2 green
    //    3 red
    //    4 blue
    public static int encodeColour(char c){
        if ((c == 'W')||(c=='w')) return 1;
        if ((c == 'G')||(c=='g')) return 2;
        if ((c == 'R')||(c=='r')) return 3;
        if ((c == 'B')||(c=='b')) return 4;
        return  0;
    }
    // Ra u6650903
    // colour decode int code to char
    public static char decodeColour(int c){
        if  (c == 1) return 'W';
        if  (c == 2) return 'G';
        if  (c == 3) return 'R';
        if  (c == 4) return 'B';
        return  'E';
    }


    // Ra u6650903
    // transform constraint into arraylist of cells (imaginary - desirable)
    public static ArrayList<Cell> constraintToCells(String constraint){
        int n = 0; // string index
        int c = 0; // colour code
        ArrayList<Cell> result = new ArrayList<>();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                c = encodeColour(constraint.charAt(n));
                result.add(new Cell(new int[] {j+3,i+1,c})); // shift to centre
                n++;
            }
        }
        return result;
    }

    // Ra 6650903
    // Checks if cell is consistent to the constraint
    public boolean isConsistentToConstraint(String constraint){
        ArrayList<Cell> constr = Cell.constraintToCells(constraint);
        for (Cell cell: constr){
            if (!this.isCellconsistentToCell(cell)) return false;
        }
        return true;
    }

    // Ra 6650903
    @Override
    public String toString(){
        String str = "x = " + x + ", y = " + y + ", color = " + colour;
        return str;
    }
    // Ra 6650903
    // draws a cell, takes mesh size and required  shift from the origin
    // returns one coloured rectangle
    public Rectangle draw(int size, int shiftX, int shiftY){
        int squaresize = (int)(size*0.75); // size of the coloured part
        int rad = size/5;
        int d = (size - squaresize)/2;
        Rectangle square = new Rectangle(this.x*size+shiftX+d,this.y*size+shiftY+d,squaresize,squaresize);
        if (this.colour == 1){
            square.setFill(Color.rgb(245,246,241));
            square.setArcWidth(rad);
            square.setArcHeight(rad);
            return square;
        }
        if (this.colour == 2){
            square.setFill(Color.rgb(152,200,30));
            square.setArcWidth(rad);
            square.setArcHeight(rad);
            return square;
        }
        if (this.colour == 3){
            square.setFill(Color.rgb(233,44,38));
            square.setArcWidth(rad);
            square.setArcHeight(rad);
            return square;
        }
        if (this.colour == 4){
            square.setFill(Color.rgb(1,105,200));
            square.setArcWidth(rad);
            square.setArcHeight(rad);;
            return square;
        }
        else return  null;
    }

    // Ra u6650903
    // find code for colour
    public static ArrayList<Color> challengeToCol(String challenge){
        ArrayList<Color> result = new ArrayList<>();
        for (int i = 0; i<challenge.length(); i++){
            if (challenge.charAt(i) == 'W') result.add(Color.rgb(245,246,241));
            if (challenge.charAt(i) == 'G') result.add(Color.rgb(152,200,30));
            if (challenge.charAt(i) == 'R') result.add(Color.rgb(233,44,38));
            if (challenge.charAt(i) == 'B') result.add(Color.rgb(1,105,200));
            if (challenge.charAt(i) == 'D') result.add(Color.rgb(231,231,231));
        }
        return  result;
    }


    // Ra 6650903
    // for testing
    public static void main(String[] args) {
        Cell c0 = new Cell(new int[]{3,1,3});
        Cell c1 = new Cell(new int[]{3,1,3});
        System.out.println(c0.isCellconsistentToCell(c1));
    }

}
