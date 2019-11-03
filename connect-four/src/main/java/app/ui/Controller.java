package app.ui;

import app.logic.Board;
import app.logic.Gamemode;
import app.logic.Heuristics.FerdiHeuristic;
import app.logic.Heuristics.Heuristic;
import app.logic.Heuristics.IHeuristic;
import app.logic.Player;
import app.logic.minimax.MiniMax;
import app.logic.minimax.MiniMaxFerdi;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public static final boolean PRUNE = true;
    public static final boolean PRINT_MINIMAX_TREE = true;
    public static final int MINIMAX_DEPTH = 8;
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    private static final int CIRCLE_DIAMETER = 80;

    private static final boolean animation = true;
    private static final boolean aiStarts = true;

    private static Gamemode gamemode = Gamemode.AIVSPLAYER;

    public static boolean isGameOver = false;

    private Disc[][] insertedDiscArray = new Disc[ROWS][COLUMNS];

    public static Board board;

    public static MiniMaxFerdi miniMaxFerdi;
    public static boolean TAKE_FERDIS_MINIMAX = true;

    public static Player playerFerdi = new Player(new FerdiHeuristic(), "0x000000ff", "Ferdinand");
    public static Player playerThorben = new Player(new FerdiHeuristic(), "0xffffffff", "Thorben");
    public static final Player startPlayer = playerFerdi;
    public static Player currentPlayer = startPlayer;



    private boolean isAllowed = true;

    @FXML
    public GridPane rootGridPane;
    @FXML
    public Pane insertDiscsPane;

    @FXML
    public Label playerNameLabel;

    @FXML
    public TextField pl1, pl2;

    @FXML
    public Label score1, score2;

    @FXML
    public Button setBtn;


    public static Player getPlayerByName(String name){
        System.out.println(name);
        if(playerFerdi.getName().equals(name)){
            return playerFerdi;
        }else if(playerThorben.getName().equals(name)){
            return playerThorben;
        }else{
            return null;
        }
    }

    public static Player getPlayerByColor(String color){
        if(playerFerdi.getColor().equals(color)){
            return playerFerdi;
        }else if(playerThorben.getColor().equals(color)){
            return playerThorben;
        }else{
            return null;
        }
    }

    public void createPlayground() {

        playerNameLabel.setText(startPlayer.getName());

        pl1.setFocusTraversable(false);
        pl2.setFocusTraversable(false);


        setBtn.setOnAction(event -> {

            playerFerdi.setName(pl1.getText());
            playerThorben.setName(pl2.getText());

            if (playerFerdi.getName().isEmpty() || playerThorben.getName().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Enter all Details");
                alert.setContentText("Enter details for both Player One and Player Two");
                alert.show();
                resetGame();
                pl1.clear();
                pl2.clear();


            }
            resetGame();


        });


        Shape rectangleWithHoles = createGameStructuralGrid();


        rootGridPane.add(rectangleWithHoles, 0, 1);

        List<Rectangle> rectangleList = createClickableColumns();

        for (Rectangle rectangle : rectangleList
        ) {
            rootGridPane.add(rectangle, 0, 1);

        }

    }


    private Shape createGameStructuralGrid() {

        Shape rectangleWithHoles = new Rectangle((COLUMNS + 1) * CIRCLE_DIAMETER, (ROWS + 1) * CIRCLE_DIAMETER);


        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                Circle circle = new Circle();
                circle.setRadius(CIRCLE_DIAMETER / 2);
                circle.setCenterX(CIRCLE_DIAMETER / 2);
                circle.setCenterY(CIRCLE_DIAMETER / 2);
                circle.setSmooth(true);

                circle.setTranslateX(col * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER / 4);
                circle.setTranslateY(row * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER / 4);
                rectangleWithHoles = Shape.subtract(rectangleWithHoles, circle);

            }


        }


        rectangleWithHoles.setFill(Color.DARKSALMON);

        return rectangleWithHoles;


    }

    private void swapPlayer(){
        if(currentPlayer.equals(playerFerdi)){
            currentPlayer = playerThorben;
        }else{
            currentPlayer = playerFerdi;
        }
    }

    private Player getOtherPlayer(Player player){
        if(player == playerFerdi){
            return playerThorben;
        }else{
            return playerFerdi;
        }
    }

    private List<Rectangle> createClickableColumns() {

        board = new Board(currentPlayer, getOtherPlayer(currentPlayer));

        List<Rectangle> rectangleList = new ArrayList<>();


        for (int col = 0; col < COLUMNS; col++) {
            Rectangle rectangle = new Rectangle(CIRCLE_DIAMETER, (ROWS + 1) * CIRCLE_DIAMETER);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(col * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER / 4);


            rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.valueOf("#eeeeee26")));
            rectangle.setOnMouseExited(event -> rectangle.setFill(Color.TRANSPARENT));

            final int column = col;
            rectangle.setOnMouseClicked(event -> {

                if(aiStarts && board.bordIsEmpty()){
                    System.out.println("isEmpty");
                    makeAIMove(true);
                }else{
                    if (isAllowed) {
                        System.out.println("Klick");
                        isAllowed = false;
                        if(gamemode.equals(Gamemode.AIVSAI)){
                            makeAIMove(true);
                        }else if(gamemode.equals(Gamemode.AIVSAISTART)) {
                            insertDisc(new Disc(currentPlayer), column, true);
                        }else{
                            insertDisc(new Disc(currentPlayer), column, true);
                        }
                    }
                }
            });

            rectangleList.add(rectangle);

        }

        return rectangleList;

    }

    private void insertDisc(Disc disc, int column, boolean aiWeiter) {

        int row = ROWS - 1;

        while (row >= 0) {

            if (getDiscIfPresent(row, column) == null)
                break;
            row--;
        }
        if (row < 0) {
            return;
        }



        insertDiscsPane.getChildren().add(disc);

        int currentRow = row;


        disc.setTranslateX(column * (CIRCLE_DIAMETER + 5) + 20);

        if(animation){
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), disc);
            translateTransition.setToY(row * (CIRCLE_DIAMETER + 5) + 20);
            translateTransition.setOnFinished(event -> {
                makeMove(currentRow, column, disc, aiWeiter);
            });

            translateTransition.play();
        }else{
            disc.setTranslateY(row * (CIRCLE_DIAMETER + 5) + 20);

            makeMove(currentRow, column, disc, aiWeiter);
        }
    }

    private void makeMove(int currentRow, int column, Disc disc, boolean aiWeiter) {
        isAllowed = true;

        insertedDiscArray[currentRow][column] = disc;
        board.set(currentRow, column, disc.getFill().toString());
        System.out.println(board.toSimpleString());

        Player tempPlayer = currentPlayer.getHeuristic().gameEnded(board, playerThorben, playerFerdi);
        if(tempPlayer!=null){
            currentPlayer = tempPlayer;
            isGameOver = true;
            gameOver();
        }
        playerNameLabel.setText(getOtherPlayer(currentPlayer).getName());

        displayScore();

        swapPlayer();

        if (!isGameOver && aiWeiter) {
            makeAIMove(aiWeiter);
        }
    }

    private void makeAIMove(boolean aiWeiter){
        Disc disc = new Disc(currentPlayer);
        //int column = MiniMax.determineBestMove(board,Controller.MINIMAX_DEPTH,Controller.PRUNE,Controller.PRINT_MINIMAX_TREE);

        int column;
        if(TAKE_FERDIS_MINIMAX){
            miniMaxFerdi = new MiniMaxFerdi(currentPlayer, MINIMAX_DEPTH, getOtherPlayer(currentPlayer));
            column = miniMaxFerdi.minmax(board);
        }else{
            column = MiniMax.determineBestMove(board,MINIMAX_DEPTH,PRUNE,PRINT_MINIMAX_TREE, currentPlayer);
        }

        if(column!=Integer.MIN_VALUE){
            //System.out.println("Make AI Move in col: "+column);
            if(gamemode.equals(Gamemode.AIVSAI) || gamemode.equals(Gamemode.AIVSAISTART)){
                insertDisc(disc, column, true);
            }else{
                insertDisc(disc, column, false);
            }
        }


    }

    private void displayScore() {
        double score1 = Controller.playerFerdi.getHeuristic().determineScore(board, playerFerdi, playerThorben);
        double score2 = Controller.playerThorben.getHeuristic().determineScore(board, playerThorben, playerFerdi);

        this.score1.setText(score1 + ": " +  playerFerdi.getName().substring(0, 1));
        this.score2.setText(score2 + ": " +  playerThorben.getName().substring(0, 1));
    }

    private Disc getDiscIfPresent(int row, int column) {

        if (row >= ROWS || row < 0 || column >= COLUMNS || column < 0)
            return null;
        else
            return insertedDiscArray[row][column];
    }

    private void gameOver() {

        String winner = currentPlayer.getName();
        System.out.println("Winner is " + winner);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect Four");
        alert.setContentText("Want to play again ?");

        alert.setHeaderText("Winner is " + winner);

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No,Exit");


        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Platform.runLater(() -> {


            Optional<ButtonType> btnCLicked = alert.showAndWait();

            if (btnCLicked.isPresent() && btnCLicked.get() == yesBtn) {
                resetGame();
            } else {

                Platform.exit();
                System.exit(0);


            }


        });


    }

    public void resetGame() {
        insertDiscsPane.getChildren().clear();

        for (int row = 0; row < insertedDiscArray.length; row++) {
            for (int col = 0; col < insertedDiscArray[row].length; col++) {

                insertedDiscArray[row][col] = null;
            }

        }

        currentPlayer = Controller.startPlayer;
        playerNameLabel.setText(startPlayer.getName());
        board = new Board(startPlayer, getOtherPlayer(startPlayer));
        createPlayground();
        isGameOver = false;
    }


    private static class Disc extends Circle {

        private final Player player;

        public Disc(Player player) {

            this.player = player;
            setRadius(CIRCLE_DIAMETER / 2);
            setCenterX(CIRCLE_DIAMETER / 2);
            setCenterY(CIRCLE_DIAMETER / 2);

            setFill(Color.valueOf(player.getColor()));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
