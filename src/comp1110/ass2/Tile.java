package comp1110.ass2;


import gittest.A;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Tile {


    // Ra u6650903
    // private tileType to cell coord-colour map for only zero orientation
    // initial orientation (0) tile layout in terms of x,y,colour.
    // each line is: letter is a type of tile (a-j) then each 3 elements array is a cell.
    // digits in cell: x,y,colour. Tile is made of several cells.
    // colour codes
    //    0 empty
    //    1 white
    //    2 green
    //    3 red
    //    4 blue


    // field to keep tiles ( Character  - type of tile, Cell array - colored squares tile is made of)
    public static HashMap<Character, Cell[]> tileMap = new HashMap<>();


    // // Ra u6650903
    // Structure of the tiles. Cells each tile is made of
    public void initializeTileMap() {
        tileMap.put('a', new Cell[]{new Cell(new int[]{0, 0, 2}), new Cell(new int[]{1, 0, 1}), new Cell(new int[]{2, 0, 3}), new Cell(new int[]{1, 1, 3})});
        tileMap.put('b', new Cell[]{new Cell(new int[]{1, 0, 4}), new Cell(new int[]{2, 0, 2}), new Cell(new int[]{3, 0, 2}), new Cell(new int[]{0, 1, 1}), new Cell(new int[]{1, 1, 1})});
        tileMap.put('c', new Cell[]{new Cell(new int[]{0, 1, 3}), new Cell(new int[]{1, 1, 3}), new Cell(new int[]{2, 1, 1}), new Cell(new int[]{3, 1, 4}), new Cell(new int[]{2, 0, 2})});
        tileMap.put('d', new Cell[]{new Cell(new int[]{0, 0, 3}), new Cell(new int[]{1, 0, 3}), new Cell(new int[]{2, 0, 3}), new Cell(new int[]{2, 1, 4})});
        tileMap.put('e', new Cell[]{new Cell(new int[]{0, 0, 4}), new Cell(new int[]{1, 0, 4}), new Cell(new int[]{2, 0, 4}), new Cell(new int[]{0, 1, 3}), new Cell(new int[]{1, 1, 3})});
        tileMap.put('f', new Cell[]{new Cell(new int[]{0, 0, 1}), new Cell(new int[]{1, 0, 1}), new Cell(new int[]{2, 0, 1})});
        tileMap.put('g', new Cell[]{new Cell(new int[]{0, 0, 1}), new Cell(new int[]{1, 0, 4}), new Cell(new int[]{1, 1, 4}), new Cell(new int[]{2, 1, 1})});
        tileMap.put('h', new Cell[]{new Cell(new int[]{0, 0, 3}), new Cell(new int[]{1, 0, 2}), new Cell(new int[]{2, 0, 2}), new Cell(new int[]{0, 1, 1}), new Cell(new int[]{0, 2, 1})});
        tileMap.put('i', new Cell[]{new Cell(new int[]{0, 0, 4}), new Cell(new int[]{1, 0, 4}), new Cell(new int[]{1, 1, 1})});
        tileMap.put('j', new Cell[]{new Cell(new int[]{0, 0, 2}), new Cell(new int[]{1, 0, 2}), new Cell(new int[]{2, 0, 1}), new Cell(new int[]{3, 0, 3}), new Cell(new int[]{0, 1, 2})});
    }

    // Ra u6650903
    // tile properties
    public Character tileType;
    public int orientation;
    // coordinates of top left position
    int x; // min x
    int y; // min y
    Cell[] cells;
    // property of the tile - picture of the tile
    public static HashSet<Rectangle> tilePicture = new HashSet<>();
    //public static Group tileGroup = new Group();


    // Ra u6650903
    // substitutes mirror tiles for the lower orientation option (required for task 9)
    public void reduceMirrors() {
        if ((tileType == 'f') || (tileType == 'g')) {
            if (orientation == 2)
                orientation = 0;
            if (orientation == 3)
                orientation = 1;
        }
    }

    // Ra u6650903
    // methods
    // constructor
    public Tile (String placement) {
        Character tileType = placement.charAt(0);
        this.x = Integer.parseInt("" + placement.charAt(1));
        this.y = Integer.parseInt("" + placement.charAt(2));
        this.orientation = Integer.parseInt("" + placement.charAt(3));
        initializeTileMap();
        int minX = 0;
        int minY = 0;
        this.cells = tileMap.get(tileType);


        // Ra u6650903
        // rotating each cell as many times as we need (according to orientation)
        //  0 for no rotation, 1 for 90 deg, 2 for 180 deg, 3 for 270 deg
        if (orientation != 0) {
            for (int r = 1; r <= orientation; r++) {
                for (Cell c : cells) {
                    c.turn90deg();
                }
            }
        }


        // Ra u6650903
        // finding new cell location after orientation to move it back to the origin
        for (Cell c : cells) {
            if (c.x < minX) minX = c.x;
            if (c.y < minY) minY = c.y;
        }


        // shifting cells back to origin
        int vecX = 0 - minX;
        int vecY = 0 - minY;
        for (Cell c : cells) {
            c.shift(vecX, vecY);
        }


        // shifting cells to the required position (must be combined with the above after commission)
        for (Cell c : cells) {
            c.shift(x, y);
        }
    }

    // Ra u6650903
    // Check if the tiles in string placements overlap between each other
    static boolean doTilesOverlap(String placements) {
        Set<String> cellCoordinates = new HashSet<>();
        for (int i = 0; i < placements.length(); i += 4) {
            String temp = placements.substring(i, i + 4);
            Tile tile = new Tile(temp);
            for (Cell cell : tile.cells) {
                int cx = cell.x;
                int cy = cell.y;


                // Ra u6650903
                // check if cell is on the board
                if ((cx < 0) || (cx > 8) || (cy < 0) || (cy > 4) || ((cx == 0) && (cy == 4)) || ((cx == 8) && (cy == 4)))
                    return true;
                String tempCell = cell.coordsToString();
                if (cellCoordinates.contains(tempCell)) {
                    return true;
                } else {
                    cellCoordinates.add(tempCell);
                }
            }
        }
        return false;
    }


    // Ra u6650903
    // Checks if tile covers certain cell
    public boolean coversCell(Cell cell){
        int x = cell.x;
        int y = cell.y;
        Cell[] thisCell = this.cells;
        for (Cell c : thisCell){
            if ((c.x == x)&&(c.y==y)) return true;
        }
        return false;
    }


    // Ra u6650903
    // Helps printing data, not ID
    @Override
    public String toString(){
        String StringtileType = "Tile type = " + this.tileType;
        String topLeftCorner = "Top Left Corner x = " + x + ", y = " + y;
        String orientation = "Orientation = " + this.orientation;
        String cells = "Cells: ";
        for(Cell cell : this.cells){
            cells += cell.toString() + " | ";
        }
        return "TILE:" + '\n' + StringtileType + '\n' + topLeftCorner + '\n' + orientation + '\n' + cells;
    }


    //Ra u6650903
    // makes tile a picture:
    // takes X Y coordinates for reference origin
    // the unit size of the coordinates plane (size of mesh of the board)
    // writes a property Arraylist of colored rectangles on dark gray backplates.
    public HashSet<Rectangle> draw(int originX, int originY, int unitCell){
        int rad = 0;
        int rim = 0;
        final HashSet<Rectangle> colorCells = new HashSet<>();
        final HashSet<Rectangle> backplates = new HashSet<>();

        for (Cell c : this.cells){
            final Rectangle backplate = new Rectangle();
            backplate.setX(c.x*unitCell+originX-rim/2);
            backplate.setY(c.y*unitCell+originY-rim/2);
            backplate.setHeight(unitCell+rim);
            backplate.setWidth(unitCell+rim);
            backplate.setArcWidth(rad);
            backplate.setArcHeight(rad);
            backplate.setFill(Color.rgb(153,153,153));
            backplates.add(backplate);
            colorCells.add(c.draw(unitCell,originX,originY)); // call cell method to draw
        }
        backplates.addAll(colorCells);
        // Ra u6650903
        // creating a field picture of the tile represented by Arraylist of rectangles
        this.tilePicture = backplates;
        //tileGroup.getChildren().addAll(backplates);
        return  backplates;
    }


    //Ra u6650903
    // Same as above but returns a group
    // makes tile a picture:
    // takes X Y coordinates for reference origin
    // the unit size of the coordinates plane (size of mesh of the board)
    // returns Group of colored rectangles on dark gray backplates.
    public Group drawGroup(int originX, int originY, int unitCell){
        int rad = 0;
        int rim = 0;
        final Group colorCells = new Group();
        final Group backplates = new Group();

        for (Cell c : this.cells){
            final Rectangle backplate = new Rectangle();
            backplate.setX(c.x*unitCell+originX-rim/2);
            backplate.setY(c.y*unitCell+originY-rim/2);
            backplate.setHeight(unitCell+rim);
            backplate.setWidth(unitCell+rim);
            backplate.setArcWidth(rad);
            backplate.setArcHeight(rad);
            backplate.setFill(Color.rgb(153,153,153));
            backplates.getChildren().add(backplate);
            colorCells.getChildren().add(c.draw(unitCell,originX,originY)); // call cell method to draw
        }
        backplates.getChildren().addAll(colorCells);
        return  backplates;
    }




    //Ra u6650903
    // makes hint tile a picture:
    // takes X Y coordinates for reference origin
    // the unit size of the coordinates plane (size of mesh of the board)
    // returns Group of yellow rectangles with no backplates.
    public  Group drawGroupHint(int originX, int originY, int unitCell){
        final Group colorCells = new Group();
        final Group backplates = new Group();
        for (Cell c : this.cells){

            final Rectangle backplate = new Rectangle();
            backplate.setX(c.x*unitCell+originX);
            backplate.setY(c.y*unitCell+originY);
            backplate.setHeight(unitCell);
            backplate.setWidth(unitCell);
            backplate.setFill(Color.rgb(255,198,39, 1));
            backplates.getChildren().add(backplate);


            Rectangle cell = c.draw(unitCell,originX,originY);
            cell.setFill(Color.rgb(231,231,231));
            colorCells.getChildren().add(cell); // call cell method to draw
        }
        backplates.getChildren().addAll(colorCells);
        return  backplates;
    }


    // Ra 6650903
    // converts string placement into arraylist of tiles
    public static ArrayList<Tile> StringToTiles(String placements){
        ArrayList<Tile> result = new ArrayList<Tile>();
        for (int i = 0; i < placements.length(); i += 4) {
            result.add(new Tile(placements.substring(i, i + 4)));
        }
        return  result;
    }


    // Ra 6650903
    // canonises solution (reduces symmetrical orientations and sorts placements alphabetically)
    public static String canonise(String placements){
        ArrayList<String> resultArr = new ArrayList<>();
        StringBuilder resultSt = new StringBuilder();
        char tiletype;
        int orientation;
        String coords = "";
        for (int i = 0; i < placements.length(); i += 4) {
            tiletype = placements.charAt(i);
            orientation = Integer.parseInt(""+placements.charAt(i+3));
            coords = placements.substring(i+1,i+3);
            if ((tiletype == 'f')||(tiletype == 'g')) {
                if (orientation == 2) orientation = 0;
                if (orientation == 3) orientation = 1;
            }
            resultArr.add(""+tiletype+coords+orientation);
        }
        Collections.sort(resultArr);
        for (String s : resultArr)
            resultSt.append(s);
        return resultSt.toString();
    }


    // Ra u6650903
    // main method for testing
    public static void main(String[] args) {
// test
        System.out.println(canonise("f003a001g002b003"));

    }
}
