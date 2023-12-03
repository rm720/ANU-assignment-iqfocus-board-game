package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import comp1110.ass2.Tile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;
    private final Group tilesOnBoard = new Group();
    private final int playOriginX = 36;
    private final int playOriginY = 74;



    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */

// Yubeng u6192835, Ra u6650903 repaired, debugged
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer

        // Ra u6650903
        // if there are some tile on the board already remove them
        if (tilesOnBoard.getChildren().size() != 0) {
            root.getChildren().remove(tilesOnBoard);
            // remove previous tiles from the board
            tilesOnBoard.getChildren().clear();
        }
        // Ra u6650903
        // if tiles are valid then associate them with the group (add tiles to the group)
        if (FocusGame.isPlacementStringValid(placement)) {
            ArrayList<Tile> tiles = Tile.StringToTiles(placement);
            for (Tile t : tiles){
                tilesOnBoard.getChildren().addAll(t.drawGroup(playOriginX, playOriginY, SQUARE_SIZE));
            }
        }
        // Ra u6650903
        // add group of tiles to the board for display
        if (tilesOnBoard.getChildren().size() != 0) {
            root.getChildren().add(tilesOnBoard);
        }
    }


    // FIXME Task 4: implement the simple placement viewer
    // Ra u6650903
    // place photo of the board in right place and right scale for tiles to appear where wanted
    private void initialisePlayBoard() {
        ImageView board = new ImageView(new Image(getClass().getResourceAsStream(URI_BASE + "board.png")));
        board.setFitWidth(615);
        board.setPreserveRatio(true);
        board.setLayoutY(0);
        board.setLayoutX(0);
        root.getChildren().add(board);
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        controls.toFront();
        root.getChildren().add(controls);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ Focus");
        Scene scene = new Scene(root, 933, 700);
        initialisePlayBoard();
        makeControls();



        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

//  --module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml,javafx.media