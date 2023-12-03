package comp1110.ass2;

import java.util.ArrayList;

public class BoardState {
    // Ra u6650903
    // current game board state, contains only colurs (int 1-4), if 0 cell is empty

    //Ra u6650903
    // field
    int[][] board = new int[9][5];
    int vacancies = 43;
    int instanceNumber = 0;
    private static int[] xSpiralIncrements = new int[]{0, 1, 0,-1,-1, 0, 0, 1, 1, 1, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0,-6, 0, 0, 0, 0, 7, 0, 0, 0,-8, 0, 0, 0};
    private static int[] ySpiralIncrements = new int[]{0, 0, 1, 0, 0,-1,-1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0,-1,-1,-1,-1, 0, 1, 1, 1, 0,-1,-1,-1};


    // First Constructor (makes default board)
    public BoardState(){
        int[][] board = new int[9][5];
        int vacancies = 43;
        int instanceNumber = 0;
    }

    // Second Constructor (makes new instance of predefined board)
    public BoardState(int[][] boardArray, int vacancies, int instanceNumber){
        this.board = boardArray;
        this.vacancies = vacancies;
        this.instanceNumber = instanceNumber;
    }

    // Third Constructor (makes new instance of predefined board)
    public BoardState(BoardState b){
        this.board = b.board;
        this.vacancies = b.vacancies;
        this.instanceNumber = b.instanceNumber;
    }

    public boolean isMoveValid(Tile tile) {
        for (Cell c : tile.cells) {
            if (this.board[c.x][c.y] != 0) return false;
        }
        return true;
    }
    // Ra u6650903
    // update the board method, places tile on the board
    //    0 empty
    //    1 white
    //    2 green
    //    3 red
    //    4 blue
    public boolean makeMove(Tile tile) {
        // x coordinate on the boardstate (column of the board but usually understood as a "row" in the matrix)
        // y coordinate on the boardstate (row, read the above, same here)
        for (Cell cell : tile.cells) {
            if (this.board[cell.x][cell.y] == 0) {
                this.board[cell.x][cell.y] = cell.colour;
                this.vacancies--;
            }
            else return false;
        }
        return true;
    }
    // Ra u6650903
    // places tiles on the board (one or many)
    // puts color code in the 8x4 matrix
    public boolean updateBoard(String placements){
        int[][] temp = this.board; // save bord before updating;
        int tempVac = vacancies;
        boolean goodMove = true; // true if all good
        for( int i = 0; i < placements.length(); i+=4){
            Tile tile = new Tile(placements.substring(i, i + 4));
            goodMove = makeMove(tile);
            if (!goodMove) {
                // if any of the cells of the tile tries to get onto occupied position, reverce all the updating and return
                this.board = temp;
                vacancies = tempVac;
                return false;
            }
        }
        return true;
    }

    // Ra u6650903
    // sets all cells to zero
    public boolean zeroBoard(){
        for (int i = 0; i<9; i++){
            for (int j = 0; j< 5; j++){
                this.board[i][j] = 0;
            }
        }
        return true;
    }

    

    // Ra u6650903
    // returns colours of the middle square
    public String middleSquare(){
        String result = "";
        for (int i = 1; i<4; i++){
            for (int j = 3; j< 6; j++){
                result = result + Cell.decodeColour(board[j][i]);
            }
        }
        return result;

    }


    // Ra u6650903
    // return coordinates of the cells which are empty, alternatively empty array if all cells are occupied
    public ArrayList<Integer[]> emptyCells() {
        ArrayList<Integer[]> result = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                if (!((x == 0) && ((y == 4)) || ((x == 8) && ((y == 4))))) {
                    if (this.board[x][y] == 0) {
                        result.add(new Integer[]{x,y});
                    }

                }
            }
        }
        return result;
    }

    // Ra u6650903
    // returns coordinates of the next available unoccupied cell
    public int[] nextEmptyCell() {
        int[] resultnull = new int[0];
        //Baoyu Ma  change the way to find all the solutions, from the center to find the cells instead of one by one
        for (int x = 3; x < 6; x++) {
            for (int y = 1; y < 4; y++) {
                if (this.board[x][y] == 0) {
                    return new int[] {x,y};
                }
            }
        }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                if (!((x == 0) && ((y == 4)) || ((x == 8) && ((y == 4))))) {
                    if (this.board[x][y] == 0) {
                        return new int[] {x,y};
                    }
                }
            }
        }
        return resultnull;
    }




    // Ra 6650903
    // prints the state of game board
    public void print(){
        String line = "";
        System.out.println("-----------------------------------");
        for (int j = 0; j < 5; j++){
            for (int i = 0; i < 9; i++){
                line = line + "["+Cell.decodeColour(this.board[i][j])+"] ";
            }
            System.out.println(line);
            line = "";
        }
        System.out.println("-----------------------------------");
    }

    public static void main(String[] args) {
        int index = 0;
        int x = 4;
        int y = 2;
        //System.out.println(xSpiralIncrements.length);
        //System.out.println(ySpiralIncrements.length);
//private static int[] xSpiralIncrements = new int[]{0, 1, 0,-1,-1, 0, 0, 1, 1, 1, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0,-6, 0, 0, 0, 0, 7, 0, 0, 0,-8, 0, 0, 0};
//private static int[] ySpiralIncrements = new int[]{0, 0, 1, 0, 0,-1,-1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0,-1,-1,-1,-1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0,-1,-1,-1,-1, 0, 1, 1, 1, 0,-1,-1,-1};

        for (int i = 0; i < 43; i++) {
            x = x + xSpiralIncrements[i];
            y = y + ySpiralIncrements[i];
            System.out.println("x="+x+" y="+y);
        }
    }


}

