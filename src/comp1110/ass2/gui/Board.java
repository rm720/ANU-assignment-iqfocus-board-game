package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;
    private static final String URI_BASE = "assets/";
    private static final int REFERENCE_Y = 200;
    private static final int REFERENCE_X = 50;
    private static final int WINDOW_WIDTH = 933;
    private static final int WINDOW_HEIGHT = 700;


    // Ra u6650903
    // Image management groups
    private final Group root = new Group();
    public final Group controls = new Group();
    public final Group board = new Group();
    private final ArrayList<Rectangle> grid = new ArrayList<>();
    public final Group tilesOnBoard = new Group();
    private final Group lidWithWindow = new Group();
    private final Group tileShop = new Group();
    private final Group tilesOnDisplay = new Group();
    private final Group placedTile = new Group();
    private final Group placedTiles = new Group();
    private final Group challenge = new Group();
    private final Group hintTile = new Group();
    private final Group hintTiles = new Group();
    private final Group helpGroup = new Group();

    // Ra u6650903
    // public vars
    public TextField textField;
    private Rectangle highlighted =  null;
    private int playOriginX;
    private int playOriginY;
    private int playCellSize;
    private boolean lidOn = false;
    private int orientation = 0;
    private String hintPlacement = "";
    private boolean hintIsOn = false;
    //private HashSet<String> hints = new HashSet<>();

    // Ra u6650903
    // variables for mouse dragging
    private double  mousePressX, mousePressY, mouseOffsetX, mouseOffsetY, offsetXLarge, offsetYLarge, offsetXSmall, offsetYSmall;
    private double tileMinX, tileMinY, tileMaxX, tileMaxY, offsetX, offsetY, tileCentreX, tileCentreY;
    private int locationTileOnField;
    private String currentChallenge = "";

    // Ra u6650903
    // variable for slash holding (to get several hints)
    private int timeCounter = 0;
    private int timeIterator = 0;
    private int secondsHeld = 0;

    // Ra u6650903
    // Tile Movement registration
    private HashSet<String> globalPlacementSet = new HashSet<>();

    // Ra u6650903
    // Set of tile placement need te become a string for validity checking.
    // set structure is required for changing the order of placed tiles on the board
    public static String setToString(HashSet<String> set){
        String result = "";
        for (String s: set){
            result = result+s;
        }
        return result;
    }

    // Ra u6650903
    // diemsions of the playfield to indicate where a tile was dropped
    private int x1min, x1max, x2min, x2max, y1min, y1max, y2min, y2max;

    // Ra u6650903
    // Keeps record what tiles are left in shop and which have been taken
    private static HashSet<Character> tilesInShop = new HashSet<>();
    private static void initializeTileStockTracker() {
        for(char c : new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'}){
            tilesInShop.add(c);
        }
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     //* @param placement A valid placement string
     */

    // Ra u6650903
    // manages tile display on the board
    // method called from refresh button (input placement string)
    private void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        // if there are some tile on the board already remove them
        if (tilesOnBoard.getChildren().size() != 0) {
            root.getChildren().remove(tilesOnBoard);
            // remove previous tiles from the board
            tilesOnBoard.getChildren().clear();
        }

        // if tiles are valid then associate them with the group (add tiles to the group)
        if (FocusGame.isPlacementStringValid(placement)) {
            ArrayList<Tile> tiles = Tile.StringToTiles(placement);
            for (Tile t : tiles){
                //System.out.println("x="+playOriginX+" y="+playOriginY);
                tilesOnBoard.getChildren().addAll(t.drawGroup(playOriginX, playOriginY, playCellSize));
            }
        }

        // add group of tiles to the board for display
        if (tilesOnBoard.getChildren().size() != 0) {
            root.getChildren().add(tilesOnBoard);
        }
    }

    // Ra u6650903
    // draws empty board in the window and clear lid, all elements' dimensions here are dependent on constant SQUARE_SIZE
    private void initialiseBoard(){
        // FIXME Task 4: implement the simple placement viewer
        // Arrange board in the centre of the window
        // Background
        double mySquareSize = SQUARE_SIZE;
        double border = 20; // side and bottom
        double topBorder = 1.3*mySquareSize; // top border
        double bgWidth = 9*mySquareSize + 2 * border;
        double bgHeight = 5*mySquareSize + topBorder + border;
        double topleftX = REFERENCE_X;
        double topleftY = REFERENCE_Y;
        double bgX = topleftX;
        double bgY = topleftY;

        // IQFocusBackground
        ImageView blueDotsBack = new ImageView(new Image(getClass().getResourceAsStream(URI_BASE+"IQFocusBackground.png"))); // Ra u6650903 corrected here a bit
        //ImageView scull = new ImageView(new Image(getClass().getResourceAsStream(URI_BASE+"scull2.png"))); // Ra u6650903 corrected here a bit

        // beautiful blue background
        blueDotsBack.setFitHeight(WINDOW_HEIGHT);  // scaling
        blueDotsBack.preserveRatioProperty();
        blueDotsBack.setPreserveRatio(true);
        root.getChildren().add(blueDotsBack);

//        // test scull
//        scull.setFitHeight(100);  // scaling
//        scull.preserveRatioProperty();
//        scull.setPreserveRatio(true);
//        root.getChildren().add(scull);



        Rectangle background = new Rectangle(bgX,bgY,bgWidth,bgHeight);
        background.setFill(Color.rgb(218,218,218));
        background.setArcWidth(100);
        background.setArcHeight(100);
        board.getChildren().add(background);

        // top bar
        double tbHeight = 1.5*mySquareSize;
        Rectangle topbar = new Rectangle(bgX,bgY,bgWidth,tbHeight);
        topbar.setFill(Color.rgb(218,218,218));
        board.getChildren().add(topbar);

        // Cell size (is set up here because playfield depends on their size)
        double cellSize = 3*(mySquareSize/4);
        double meshSize = mySquareSize/4; // meshsize refers only to the gap between cells,
        // not to the grid size, grid size would be cellsize + meshsize;

        // playfields (big and small) lower level area where to place tiles
        // Big
        double overlap = mySquareSize/4; // allowance for overlap playfields for round corner look beautiful
        double pfbWidth = 9*cellSize + 10*meshSize; //(meshsize is the width of the gap between cells)
        double pfbHeight = 4*cellSize + 5*meshSize;
        double pfbX =(bgX+(bgWidth - pfbWidth)/2);
        double pfbY = REFERENCE_Y+(topBorder-border/3);
        Rectangle playfieldBig = new Rectangle(pfbX, pfbY, pfbWidth, pfbHeight);
        playfieldBig.setArcWidth(overlap);
        playfieldBig.setArcHeight(overlap);
        playfieldBig.setFill(Color.rgb(189,189,189));
        board.getChildren().add(playfieldBig);

        // playfields (big and small) bottom part of the lower level area where to place tiles
        // Small
        double pfsWidth = 7*cellSize+8*meshSize; //(meshsize is the width of the gap between cells)
        double pfsHeight = cellSize+meshSize+overlap;
        double pfsX = (pfbX + mySquareSize);
        double pfsY = (pfbY + pfbHeight-overlap);

        // limits for indicating that a tile is on the playfield
        x1min = (int)pfbX;
        x1max = (int)(pfbX+pfbWidth);
        y1min = (int)pfbY;
        y1max = (int)(pfbY+pfbHeight);
        x2min = (int)pfsX;
        x2max = (int)(pfsX+pfsWidth);
        y2min = (int)pfsY;
        y2max = (int)(pfsY+pfsHeight);

        Rectangle playfieldSmall = new Rectangle(pfsX, pfsY, pfsWidth, pfsHeight);
        playfieldSmall.setArcWidth(overlap);
        playfieldSmall.setArcHeight(overlap);
        playfieldSmall.setFill(Color.rgb(189,189,189));
        board.getChildren().add(playfieldSmall);

        // Cell (grid on the empty board)
        int startX = (int) (pfbX+meshSize);
        int finishX = (int) (pfbX + pfbWidth);
        int startY = (int) (pfbY+meshSize);
        int finishY = (int) (pfbY + pfbHeight);
        int increment = (int) (cellSize + meshSize); // (meshsize is the width of the gap between cells)
        playOriginX = startX - (int)meshSize/2;
        playOriginY = startY - (int)meshSize/2;
        playCellSize = increment;
        for (int cellX = startX; cellX < finishX; cellX += increment ){
            for (int cellY = startY; cellY < finishY; cellY += increment ){
                Rectangle cell = new Rectangle(cellX, cellY, cellSize, cellSize);
                cell.setArcWidth(meshSize);
                cell.setArcHeight(meshSize);
                cell.setFill(Color.rgb(231,231,231));
                grid.add(cell);
            }
        }

        // Bottom row cells of the grid
        int startBX = (int) (pfbX+2*meshSize + cellSize); // (meshsize is the width of the gap between cells)
        int finishBX = (int) (pfbX + pfbWidth - (2*meshSize+ cellSize));
        int botomRowY = (int) (pfbY + 4* cellSize + 5*meshSize);


        // draw cells grid
        for (int cellX = startBX; cellX < finishBX; cellX += increment ){
            Rectangle cell = new Rectangle(cellX, botomRowY, cellSize, cellSize);
            cell.setArcWidth(meshSize);
            cell.setArcHeight(meshSize);
            cell.setFill(Color.rgb(231,231,231));
            grid.add(cell);
        }
        // add grid to the board
        board.getChildren().addAll(grid);

        // Creating a lid with a window (4 parts) // (meshsize is the width of the gap between cells)
        Color transparent = Color.rgb(255,255,255,0.7);
        Rectangle lidTop = new Rectangle(bgX,bgY,bgWidth,startY+cellSize+meshSize/2-REFERENCE_Y);
        lidTop.setFill(transparent);
        lidWithWindow.getChildren().add(lidTop);
        Rectangle lidLeft = new Rectangle(bgX,startY+cellSize+meshSize/2,
                startX+3*cellSize+2*meshSize+meshSize/2-bgX, 3*(cellSize+meshSize));
        lidLeft.setFill(transparent);
        lidWithWindow.getChildren().add(lidLeft);
        Rectangle lidRight= new Rectangle(startX+6*cellSize+5*meshSize+meshSize/2, startY+cellSize+meshSize/2,
                bgX+bgWidth-(startX+6*cellSize+5*meshSize+meshSize/2), 3*(cellSize+meshSize)  );
        lidRight.setFill(transparent);
        lidWithWindow.getChildren().add(lidRight);//
        Rectangle lidBottom = new Rectangle(bgX,(double)((startY+cellSize+meshSize/2)+(3*(cellSize+meshSize))), bgWidth,
                (bgY+bgHeight)-(startY+cellSize+meshSize/2+3*(cellSize+meshSize)) );
        lidBottom.setFill(transparent);
        lidWithWindow.getChildren().add(lidBottom);
    }

    private boolean tileOnField(int x, int y){
        boolean onBigPart = (((x>x1min)&&(x<x1max))&&((y>y1min)&&(y<y1max)));
        boolean onSmallPart = (((x>x2min)&&(x<x2max))&&((y>y2min)&&(y<y2max)));
        if (onBigPart || onSmallPart) return true;
        else return false;
    }

    // Ra u6650903
    // finds the nearest cell on the board to snap tile into it, returns a Rectangle
    private Rectangle findNearestCell(double x, double y){
        Rectangle nearest = grid.get(0);
        double shortest = Cell.distance(x,y,nearest);
        double distance;
        for(Rectangle rec : grid){
            distance = Cell.distance(x,y,rec);
            if (distance<shortest) {
                shortest = distance;
                nearest = rec;
            }
        }
        return nearest;
    }

    // Ra u6650903
    // finds the nearest cell coordinates on the board to snap tile into it, returns coordinates of top left corner.
    public double[] findNearestCellCoords(double x, double y){
        Bounds bounds = findNearestCell(x,y).getLayoutBounds();
        double resultX = bounds.getMinX();
        double resultY = bounds.getMinY();
        double[] result = {resultX,resultY};
        return result;
    }

    // Ra u6650903
    // highlites the intended cell to place a tile
    private void highlightNearestCell(double x, double y){
        if (highlighted != null)
            highlighted.setFill(Color.rgb(231,231,231));
        highlighted = findNearestCell(x,y);
        highlighted.setFill(Color.YELLOW);
    }

    // Ra u6650903
    // dehighlites the intended cell to place a tile
    private void dehighlightNearestCell(double x, double y){
        if (highlighted != null)
            highlighted.setFill(Color.rgb(231,231,231));
        highlighted = findNearestCell(x,y);
        highlighted.setFill(Color.rgb(231,231,231));
    }


    // Ra u6650903
    // display tileshop
    private void initialTiles(int orientation) {
        // background surface
        // constants

        int topLeftX = REFERENCE_X+600;
        int topLeftY = REFERENCE_Y;
        int shopWidth = 260;
        int shopHeight = VIEWER_HEIGHT-80;
        Rectangle backWall = new Rectangle(topLeftX,topLeftY,shopWidth,shopHeight);
        backWall.setFill(Color.rgb(231,231,231,0.35));
        backWall.setArcWidth(100);
        backWall.setArcHeight(100);
        tileShop.getChildren().add(backWall);

        int tileSize = SQUARE_SIZE/4;
        int stepY = 4*(shopHeight/21);
        int stepX = 4*(shopWidth/9);
        int startX = topLeftX+2*(shopWidth/9);
        int startY = topLeftY+10*(stepY/21);

        Character tileType = 'a';
        String tempPlacement = "";
        int x;
        int y;
        for (int tileX = 0; tileX < 2; tileX ++ ){
            for (int tileY = 0; tileY < 5; tileY ++ ) {
                if (tilesInShop.contains(tileType)) { // if this tiletype has not been taken from the shop
                    x = (tileX * stepX) + startX;
                    y = (tileY * stepY) + startY;
                    tempPlacement = "" + tileType + "00" + orientation;
                    String placedTilePlacement = tempPlacement;
                    final double initTileX = x;
                    final double initTileY = y;
                    final Character tt = tileType;
                    final String ttPlacement = tempPlacement;
                    Tile t = new Tile(tempPlacement);
                    Node oneTile = t.drawGroup(x, y, tileSize);
                    oneTile.setCursor(Cursor.HAND);
                    tilesOnDisplay.getChildren().add(oneTile);

                    // setting mouse events
                    oneTile.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            mousePressX  = t.getX();
                            mousePressY = t.getY();
                            Bounds bounds = oneTile.getLayoutBounds();
                            tileMinX = bounds.getMinX();
                            tileMinY = bounds.getMinY();
                            tileMaxX = bounds.getMaxX();
                            tileMaxY = bounds.getMaxY();
                            tileCentreX = (tileMaxX - tileMinY)/2;
                            tileCentreY = (tileMaxY - tileMinY)/2;

                            mouseOffsetX = (mousePressX - tileMinX);
                            mouseOffsetY = (mousePressY - tileMinY);
                            if (tileOnField((int)mousePressX,(int)mousePressY)){
                                offsetXLarge = mouseOffsetX;
                                offsetYLarge = mouseOffsetY;
                                offsetXSmall = mouseOffsetX/4;
                                offsetYSmall = mouseOffsetY/4;
                                int unitX = (int) (Math.round((t.getSceneX()- offsetXLarge - 77) / 60));
                                int unitY = (int) (Math.round((t.getSceneY() - offsetYLarge - 286) / 60));
                                locationTileOnField = 10*unitX+unitY;
                            }
                            else {
                                offsetXSmall = mouseOffsetX;
                                offsetYSmall = mouseOffsetY;
                                offsetXLarge = mouseOffsetX*4;
                                offsetYLarge = mouseOffsetY*4;
                                locationTileOnField = -1;
                            }
                        }
                    });

                    // Ra u6650903
                    // moving tile with mouse handle
                    oneTile.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            // see where the mouse currently is to deside on tile size (scale)
                            double tileX = t.getSceneX();
                            double tileY = t.getSceneY();

                            // if a tile is on the playfield then redraw it bigger
                            if ((tileOnField((int)tileX,(int)tileY))){
                                ((Group)t.getSource()).getChildren().clear();
                                Tile oneTile = new Tile(placedTilePlacement);
                                ((Group)t.getSource()).getChildren().addAll(oneTile.drawGroup((int)tileX,(int)tileY, 60));
                                ((Node) (t.getSource())).setLayoutX((0-offsetXLarge));
                                ((Node) (t.getSource())).setLayoutY((0-offsetYLarge));
                                highlightNearestCell(tileX-offsetXLarge, tileY-offsetYLarge);
                                highlighted.toFront();

                                // tile residency registration
                                int locationY = locationTileOnField % 10;
                                int locationX = (locationTileOnField - locationY)/10;
                                String returnTile = ttPlacement.charAt(0)+""+locationX+""+locationY+""+ttPlacement.charAt(3);
                                globalPlacementSet.remove(returnTile);


                            }
                            // if tile is not on the playfield then draw small tile
                            if ((!tileOnField((int)tileX,(int)tileY))){
                                ((Group)t.getSource()).getChildren().clear();
                                Tile oneTile = new Tile(placedTilePlacement);
                                ((Group)t.getSource()).getChildren().addAll(oneTile.drawGroup((int)tileX,(int)tileY, tileSize));
                                ((Node) (t.getSource())).setLayoutX((0-offsetXSmall));
                                ((Node) (t.getSource())).setLayoutY((0-offsetYSmall));
                                dehighlightNearestCell(tileX-offsetXLarge, tileY-offsetYLarge);
                            }
                            oneTile.toFront();
                            ((Group)t.getSource()).toFront();
                        }
                    });
                    oneTile.setOnMouseReleased(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            // first get the coordinates of the  mouse
                            double tileX = t.getSceneX();
                            double tileY = t.getSceneY();
                            // check if it is consistent, for that:
                            // build snap placement string
                            int unitX = (int) (Math.round((tileX - offsetXLarge - 77) / 60));
                            int unitY = (int) (Math.round((tileY - offsetYLarge - 286) / 60));
                            String snapPlacement = ttPlacement.charAt(0) + "" + unitX + "" + unitY +""+ ttPlacement.charAt(3);

                            // in case the tile is placed on the field:
                            if (tileOnField((int)tileX,(int)tileY)) {
                                // hide hint
                                unInitialiseHint();

                                // see if it complies with existing tiles on the board (overlap)
                                boolean moveIsLegal = FocusGame.isPlacementStringValid(setToString(globalPlacementSet)+snapPlacement);

                                // so if we do not overlap
                                if (moveIsLegal) {
                                    // get new tile on snap position (in grid), for that:
                                    // clear mouse node
                                    ((Group)t.getSource()).getChildren().clear();
                                    // build new tile in grid
                                    Tile snapTile = new Tile(snapPlacement);
                                    Group snappedTile = snapTile.drawGroup(playOriginX, playOriginY, playCellSize);
                                    // put new tile to the mouse node
                                    ((Group)t.getSource()).getChildren().addAll(snappedTile);
                                    // snap tile in the position
                                    ((Node) (t.getSource())).setLayoutX((0));
                                    ((Node) (t.getSource())).setLayoutY((0));

                                    //remove hint from the board
                                    if (snapPlacement.equals(hintPlacement)){
                                        placedTiles.getChildren().removeAll(hintTile);
                                    }

                                    // delete tile from where it came from:
                                    // if it came from shop
                                    if (locationTileOnField < 0){
                                        // remove tile form shop group
                                        tilesOnDisplay.getChildren().remove(((Node) (t.getSource())));
                                        // remove tiletype from shop stock set
                                        tilesInShop.remove(tt);
                                    }
                                    // if tile came from field
                                    if (locationTileOnField >= 0){
                                        // remove tile form shop group
                                        placedTiles.getChildren().remove(((Node) (t.getSource())));
                                        // remove tile from the placement set
                                        globalPlacementSet.remove(snapPlacement);
                                    }


                                    // add tile to the new showing group
                                    placedTiles.getChildren().add((Node) (t.getSource()));
                                    // register new placement in set
                                    globalPlacementSet.add(snapPlacement);
                                    // register new tile location
                                    locationTileOnField = 10*unitX+unitY;
                                    // turn off highlighting
                                    dehighlightNearestCell(tileX-offsetXLarge, tileY-offsetYLarge);
                                    highlighted.setFill(Color.rgb(231,231,231));
                                }


                                // in case we make overlap
                                if (!moveIsLegal) {
                                    // get the new tile on bounce position, for that
                                    // remove the tile from the mouse node
                                    ((Group)t.getSource()).getChildren().clear();
                                    // build new tile in bounce location
                                    Tile bounceTile = new Tile(placedTilePlacement);
                                    Group bouncedTile = bounceTile.drawGroup((int)initTileX,(int)initTileY, tileSize);
                                    // put new tile to the mouse node
                                    ((Group)t.getSource()).getChildren().add(bouncedTile);
                                    // bounce tile back to the position
                                    ((Node) (t.getSource())).setLayoutX((0));
                                    ((Node) (t.getSource())).setLayoutY((0));


                                    // remove tile from where it came from
                                    // if tile came from shop
                                    if (locationTileOnField < 0){
                                        // remove tile form shop group
                                        tilesOnDisplay.getChildren().remove(((Node) (t.getSource())));
                                        // remove tiletype from shop stock set
                                        tilesInShop.remove(tt);
                                    }
                                    // if tile came from field
                                    if (locationTileOnField >= 0){
                                        // remove tile form shop group
                                        placedTiles.getChildren().remove(((Node) (t.getSource())));
                                        // remove tile from the placement set
                                        globalPlacementSet.remove(snapPlacement);
                                    }

                                    // add tile picture to the shop (bounce back)
                                    tilesOnDisplay.getChildren().add((Node) (t.getSource()));
                                    // register tiletype back to the store set
                                    tilesInShop.add(tt);
                                    // register new tile location (shop)
                                    locationTileOnField = -1;
                                    // turn off highlighting
                                    dehighlightNearestCell(tileX-offsetXLarge, tileY-offsetYLarge);
                                    highlighted.setFill(Color.rgb(231,231,231));
                                }
                            }


                            // if tile is placed not in the field
                            if((!tileOnField((int)tileX,(int)tileY))) {
                                // get the new tile on bounce position, for that
                                // remove the tile from the mouse node
                                ((Group)t.getSource()).getChildren().clear();
                                // build new tile in bounce location
                                Tile bounceTile = new Tile(placedTilePlacement);
                                Group bouncedTile = bounceTile.drawGroup((int)initTileX,(int)initTileY, tileSize);
                                // put new tile to the mouse node
                                ((Group)t.getSource()).getChildren().add(bouncedTile);
                                // bounce tile back to the position
                                ((Node) (t.getSource())).setLayoutX((0));
                                ((Node) (t.getSource())).setLayoutY((0));


                                // remove tile from where it came from
                                // if tile came from shop
                                if (locationTileOnField < 0){
                                    // remove tile form shop group
                                    tilesOnDisplay.getChildren().remove(((Node) (t.getSource())));
                                    // remove tiletype from shop stock set
                                    tilesInShop.remove(tt);
                                }
                                // if tile came from field
                                if (locationTileOnField >= 0){
                                    // remove tile form shop group
                                    placedTiles.getChildren().remove(((Node) (t.getSource())));
                                    // remove tile from the placement set
                                    globalPlacementSet.remove(snapPlacement);
                                }

                                // add tile picture to the shop (bounce back)
                                tilesOnDisplay.getChildren().add((Node) (t.getSource()));
                                // register tiletype back to the store set
                                tilesInShop.add(tt);
                                // register new tile location (shop)
                                locationTileOnField = -1;
                                // turn off highlighting
                                dehighlightNearestCell(tileX-offsetXLarge, tileY-offsetYLarge);
                                highlighted.setFill(Color.rgb(231,231,231));
                            }
                            highlighted.setFill(Color.rgb(231,231,231));
                            ((Group)t.getSource()).toFront();
                        }
                    });
                }
                tileType++;
            }
        }
    }

    /**
     * Create a basic text field for input and a refresh button.
     */

    // Ra u6650903
    // creating control buttons
    // Not used in Board application. Used in Viewer application
    public void makeControlsPlacement() {
        Label label1 = new Label("Placement:");
        label1.setTextFill(Color.WHITE);
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!lidOn) {
                    makePlacement(textField.getText());
                    textField.clear();
                }
            }
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(REFERENCE_X + 40); // was 130 by Steve
        hb.setLayoutY(REFERENCE_Y + (VIEWER_HEIGHT - 50));
    }

    // Ra u6650903
    // tile rotate and close lid buttons
    public void makeControlsGame() {

        // Ra u6650903
        // Lid closing and opening button
        Button myButton = new Button("Close Lid"); // was close lid
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (lidOn) {
                    root.getChildren().remove(lidWithWindow);
                    lidOn = false;
                    myButton.setText("Close Lid"); // was close lid
                }
                else {
                    root.getChildren().add(lidWithWindow);
                    lidOn = true;
                    myButton.setText("Open Lid"); // was open lid
                }
            }
        });


        // Ra u6650903
        // Win button
        Button winButton = new Button("Finish Game"); // was close lid
        winButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                winCheck();
            }
        });

        // Ra u6650903
        // Help button

        Button helpButton = new Button("??"); // was close lid
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                showHelp();
            }
        });

        helpButton.setLayoutX(10);
        helpButton.setLayoutY(10);
        controls.getChildren().add(helpButton);


        // Tile rotation button setup
        // HB box for button
        // Button action
        Button rotationButton = new Button("Rotate");
        rotationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tilesOnDisplay.getChildren().clear();
                root.getChildren().remove(tilesOnDisplay);
                tileShop.getChildren().clear();
                orientation = (orientation + 1) % 4;
                initialTiles(orientation);
                root.getChildren().add(tilesOnDisplay);
            }
        });


        // Buttons display within H box: location of the buttons (lid, finish game, rotate) in horizontal box
        int tileShopHboxX = (WINDOW_WIDTH-370);
        int tileShopHboxY = REFERENCE_Y+(VIEWER_HEIGHT-50);
        controls.getChildren().add(rotationButton);
        HBox tileShopHBox = new HBox();
        tileShopHBox.setSpacing(50);
        tileShopHBox.setLayoutX(tileShopHboxX-20);
        tileShopHBox.setLayoutY(tileShopHboxY);
        tileShopHBox.getChildren().addAll(myButton,winButton,rotationButton);
        tileShopHBox.toFront();
        controls.getChildren().add(tileShopHBox);

        // Ra u6650903
        // complexity selector menu
        ChoiceBox LevelChoiceBox = new ChoiceBox();
        LevelChoiceBox.getItems().add("Easy");
        LevelChoiceBox.getItems().add("Medium");
        LevelChoiceBox.getItems().add("Hard");
        LevelChoiceBox.getItems().add("Very Hard");

        Label label2 = new Label("Select Difficulty Level");
        label2.setTextFill(Color.WHITE);
        label2.setScaleX(1.8);
        label2.setScaleY(1.8);
        //label2.setFont(Font.font(null,FontWeight.BOLD,20));

        VBox LevelVbox = new VBox();
        LevelVbox.setLayoutX(730);
        LevelVbox.setLayoutY(22);
        LevelVbox.setSpacing(17);

        // Ra u6650903
        // Listen what was selected in the selector box
        final String[] selection = {""};
        // string array
        String st[] = { "Easy","Medium", "Hard", "Very Hard"};
        LevelChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                // set the text for the label to the selected item
                selection[0] = (st[new_value.intValue()]);
            }
        });

        // Ra u6650903
        // Game start button. initialises new challenge
        Button StartButton = new Button("Start New Game");
        StartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int compLevel = 0; // no complexity selected
                if (selection[0].equals("Easy"))
                    compLevel = 4; // easiest level selected (5-8 solutions challenges)
                if (selection[0].equals("Medium"))
                    compLevel = 3; // easiest level selected (5-8 solutions challenges)
                if (selection[0].equals("Hard"))
                    compLevel = 2; // medium level selected (2-4 solutions challenges)
                if (selection[0].equals("Very Hard"))
                    compLevel = 1; // hard level selected (1 solutions challenges)

                // if the button is pressed clear all groups except controls
                // clear tile residency tracking variables
                zeroMyGame();
                initialiseChallenge(compLevel);
            }
        });


        // Ra u6650903
        // arrangements for "Hint" button
        Button HintButton = new Button("Hint");
        HintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (hintIsOn) {
                    unInitialiseHint();
                }
                else {
                    if (!currentChallenge.isEmpty()) {
                        initialiseHint(globalPlacementSet);
                    }
                }
            }
        });
        LevelVbox.getChildren().addAll(label2, LevelChoiceBox,StartButton, HintButton);
        LevelVbox.toFront();
        controls.getChildren().add(LevelVbox);
    }


    // Ra u6650903
    // reset the game anew
    // zeroing initial conditions method
    public void zeroMyGame(){
        // used with button (Play More, and Start New Game) is pressed clear all groups except controls
        // clear tile residency tracking variables
        root.getChildren().clear();
        board.getChildren().clear();
        tilesOnBoard.getChildren().clear();
        lidWithWindow.getChildren().clear();
        tileShop.getChildren().clear();
        tilesOnDisplay.getChildren().clear();
        placedTile.getChildren().clear();
        placedTiles.getChildren().clear();
        initializeTileStockTracker();
        initialiseBoard();
        initialTiles(0);
        root.getChildren().add(board);
        root.getChildren().add(controls);
        root.getChildren().add(tileShop);
        root.getChildren().add(tilesOnDisplay);
        root.getChildren().add(placedTiles);
        root.getChildren().add(challenge);
        locationTileOnField = 0;
        currentChallenge = "";
        globalPlacementSet.clear();
    }

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // Ra u650903
    // set up a challenge, displays the challenge goal (called from button of choosing complexity)
    public void initialiseChallenge(int complexityLevel){
        if (complexityLevel != 0) {
            final Random r = new Random();
            AllChallenge chall = new AllChallenge();
            chall.initialiseSolutionMap();
            List<String> games = new ArrayList<String>(chall.challengeFinder.get(complexityLevel));
            int numberOfGames = games.size();
            int randomGameNum = r.nextInt(numberOfGames);
            String challengeString = games.get(randomGameNum);
            currentChallenge = challengeString;
            Group challengePicture = AllChallenge.drawChallenge(challengeString, 260, 20, 1);
            challenge.getChildren().add(challengePicture);
        }
        else{
            Group challengePicture = AllChallenge.drawChallenge("", 260, 20, 1);
            challenge.getChildren().add(challengePicture);
        }
    }



    //Ra u6650903
    // Check if the user completed the task and won
    public void showHelp(){

        // Ra u6650903
        // White help screen
        // Shows  manual and help

        Rectangle curtain = new Rectangle(0,0,WINDOW_WIDTH*2,WINDOW_HEIGHT);
        curtain.setFill(Color.rgb(253,253,255,0.85));
        Label manual = new Label("Manual");
        manual.setLayoutX(WINDOW_WIDTH/2-100);
        manual.setLayoutY(40);
        manual.setFont(Font.font(null, FontWeight.BOLD, 50));
        //manual.setTextFill(Color.rgb(1,105,200));
        //manual.setTextFill(Color.rgb(1,105,160));
        manual.setTextFill(Color.rgb(1,105,160));
        curtain.toFront();
        manual.toFront();
        
        // Yubeng u6192835
        Text text = new Text();
        text.setLayoutX(70);
        text.setLayoutY(150);
        text.setWrappingWidth(800);
//        text.setText("To start the game select the level of difficulty in the selector box on the top right\n"+
//                "and click 'Start New Game' button.\n"+
//                "A coloured square will appear at the top, this is your challenge.\n"+
//                "\n"+
//                "The object of the game is to fit all the pieces on the game board so the colour pattern\n"+
//                "of the centre 3x3 square matches the colours of the challenge (square at the top).\n"+
//                "You need to fit all the pieces from the right on the board to the left.\n"+
//                "\n"+
//                "To move pieces use mouse.\n"+
//                "To rotate pieces click 'Rotate' button at the bottom right.\n"+
//                "To return a piece back single click on the piece.\n"+
//                "To close the lid and see if the pattern matches click 'Close Lid' button.\n"+
//                "When done click 'Finish Game' button to check whether you succeed.\n"+
//                "If you get stuck click 'Hint' button to know the next piece,\n"+
//                "Or press and hold '/' on keyboard for several hints.\n");

// C:\Users\ratmi\IdeaProjects\comp1110-pushingAss2\game.jar


        text.setText("1. Select a level challenge in the selector box and click 'Start New Game' button.\n" +
                "\n" +
                "2. The object of the game is to fit all the pieces on the game board so that the colors of the 9 central" +
                " squares match what is shown in the challenge.\n" +
                "You are free to press 'Hint' button or hold '/' on keyboard to get what's the next pieces going to be placed.\n" +
                "\n" +
                "3. When all puzzle pieces fit on the game board, close the lid to check if the color placements in" +
                " the central 3x3 area match the challenge.\n" +
                "Now, you can press 'Finish Game' to check whether you succeed.");
        text.setFont(Font.font(null, FontWeight.BOLD, 25));
        text.setFill(Color.rgb(1,105,160));
        curtain.toFront();
        manual.toFront();

        // Ra u6650903
        Button playMore = new Button("<-");
        playMore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                hideHelp();
            }
        });

        HBox playAgain = new HBox();
        playAgain.setSpacing(70);
        playAgain.setLayoutX(10);
        playAgain.setLayoutY(10);
        playAgain.getChildren().addAll(playMore);
        playAgain.toFront();
        helpGroup.getChildren().addAll(curtain,manual,text,playAgain);
        root.getChildren().addAll(helpGroup);
    }


    public void hideHelp(){
        helpGroup.getChildren().clear();;
        root.getChildren().remove(helpGroup);
    }



    //Ra u6650903
    // Check if the user completed the task and won
    public void winCheck(){
        String tryWinsolution = setToString(globalPlacementSet);
        String attemptChallenge = currentChallenge;
        BoardState testBoard = new BoardState();
        testBoard.zeroBoard();
        testBoard.updateBoard(tryWinsolution);
        String middleSquareColours = testBoard.middleSquare();
        boolean allTilesUsed = ((tryWinsolution.length() == 40)&&(tilesInShop.size() == 0));
        boolean challengeCompleted = (attemptChallenge.equals(middleSquareColours));

        // Ra u6650903
        // Black win screen
        // Shows that user won and a button to play more
        if (allTilesUsed && challengeCompleted) {
            Rectangle curtain = new Rectangle(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
            curtain.setFill(Color.rgb(0,0,0,0.8));
            Label congrats = new Label("WIN!");
            congrats.setLayoutX(20);
            congrats.setLayoutY(110);
            congrats.setFont(Font.font("Cambria", FontWeight.BOLD, 400));
            congrats.setTextFill(Color.GOLD);
            curtain.toFront();
            congrats.toFront();

            // Ra u6650903
            // After win : show button to return back
            // return to paly after win
            Button playMore = new Button("Play More");
            playMore.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    zeroMyGame();
                    initialiseChallenge(0);
                }
            });

            HBox playAgain = new HBox();
            playAgain.setSpacing(70);
            playAgain.setLayoutX(WINDOW_WIDTH/2);
            playAgain.setLayoutY(WINDOW_HEIGHT-WINDOW_HEIGHT/6);
            playAgain.getChildren().addAll(playMore);
            playAgain.toFront();
            root.getChildren().addAll(curtain,congrats,playAgain);
        }
    }


    // FIXME Task 10: Implement hints
    // Ra u6650903
    // draws orange hint tile
    public void initialiseHint(HashSet<String> existingPlacement){
        hintPlacement = AllChallenge.getHint(currentChallenge, existingPlacement);
        if (!hintPlacement.isEmpty()) {
            Tile newHintTile = new Tile(hintPlacement);
            hintIsOn = true;
            hintTile.getChildren().addAll(newHintTile.drawGroupHint(playOriginX, playOriginY, SQUARE_SIZE));
            board.getChildren().add(hintTile);
        }
    }
    // Ra u6650903
    // hides a single hint (orange tile): undoes the above method
    public void unInitialiseHint(){
        hintIsOn = false;
        hintTile.getChildren().clear();
        board.getChildren().removeAll(hintTile);
    }

    // Ra u6650903
    // finds and displays multiple hints depending on the current challenge abd state of the board
    public void hintsDisplay(int howMany, String challenge, HashSet<String> currentState){
        hintTiles.getChildren().clear();
        board.getChildren().remove(hintTiles);
        HashSet<String> currentHints = new HashSet(currentState);
        //currentHints.addAll(hints);
        if (howMany > 0) {
            HashSet<String> hints = AllChallenge.getHints(howMany, challenge, currentHints);
            for (String hint : hints) {
                Tile h = new Tile(hint);
                hintTiles.getChildren().addAll(h.drawGroupHint(playOriginX, playOriginY, SQUARE_SIZE));
            }
            board.getChildren().addAll(hintTiles);
        }
    }

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    // Ra u6650903, Yubeng,
    // solutions are generated using AllChallenges class and they are stored in file challengeLibrary.csv. Please find it.
    // we generated all possible challenges and found all solutions, and store them in the file.

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ Focus");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        // Ra u6650903
        // slash key press event handler
        // holding slash constatntly gives an event (approx 70 per second)
        // timecounter counts those events
        // secondsHeld counts seconds starting from 1, by dividing timecounter by 100;
        // every second hintDisplay is called with higher number of requested hints
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SLASH) {
                if (hintIsOn) {
                    unInitialiseHint();
                }
                else {
                    if (!currentChallenge.isEmpty()) {
                        timeCounter++;
                        secondsHeld = 1+Math.round(timeCounter/40);
                        if (secondsHeld > timeIterator) {
                            hintsDisplay(secondsHeld, currentChallenge, globalPlacementSet);
                            timeIterator = secondsHeld;
                        }
                    }
                }
            }
        });


        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SLASH) {
                // zero all related to multiple hints algo variables
                secondsHeld = 0;
                timeIterator = 0;
                timeCounter = 0;
                hintTiles.getChildren().clear();
            }
        });

        initializeTileStockTracker();
        initialiseBoard();
        makeControlsPlacement();
        initialTiles(0);
        root.getChildren().add(board);
        root.getChildren().add(controls);
        root.getChildren().add(tileShop);
        root.getChildren().add(tilesOnDisplay);
        root.getChildren().add(placedTiles);
        root.getChildren().add(challenge);
        makeControlsGame();
        initialiseChallenge(0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


