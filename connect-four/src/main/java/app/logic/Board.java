package app.logic;

import app.logic.minimax.Direction;
import app.ui.Controller;
import javafx.util.Pair;

import java.util.ArrayList;

// a more simple form of the disc matrix in Controller
// stores just the color of the disc
// for using it in the heuristic
public class Board {
    private String[][] board;
    public static final String NO_COLOR = "NONE";
    public static final double LOWEST_NUMBER = -1000.0;
    public static final double HIGHEST_NUMBER = 1000.0;
    public static final String MAXIMIZER = Controller.playerThorben.getColor();
    private boolean pruned = false;
    public Pair<Double,Double> borders = new Pair<>(LOWEST_NUMBER, LOWEST_NUMBER);
    //the disc color of the player who is on it
    //used for generating next possible constellations
    public Player nextPlayer;
    //the disc color of the player who made the last move
    //used for generating next possible constellations
    public Player lastPlayer;
    public double score;

    private BoardElement lastMove;

    public Board(String[][] board, Player lastPlayer, Player nextPlayer) {
        this.board = board;
        this.lastPlayer = lastPlayer;
        this.nextPlayer = nextPlayer;
    }

    public Board(Player lastPlayer, Player nextPlayer) {
        this.board = new String[Controller.ROWS][Controller.COLUMNS];
        this.lastPlayer = lastPlayer;
        this.nextPlayer = nextPlayer;
    }

    public Board duplicate() {
        //https://stackoverflow.com/a/9106176
        String[][] input = this.board;
        String[][] result = new String[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        };
        return new Board(result, this.lastPlayer, this.nextPlayer);
    }

    public void set(int row, int column, String value) {
        this.board[row][column] = value;
        this.switchCurrentLastPlayer();
        this.lastMove = new BoardElement(row,column,value);
    }

    public BoardElement getLastMove(){
        return this.lastMove;
    }

    public String get(int row, int column) {
        String color;

        try {
            color = this.board[row][column];
            if (null == color) {
                color = NO_COLOR;
            }
        } catch (IndexOutOfBoundsException e) {
            color = null;
        }
        return color;
    }

    //returns if the value could be inserted in the given column
    public boolean insertInColumn(int column, String value) {
        boolean inserted = false;
        int row = rowLength() - 1;
        while (!inserted && row > 0) {
            if (null == this.board[row][column]) {
                this.set(row, column, value);
                inserted = true;
            }
            row--;
        }
        return inserted;
    }

    private void switchCurrentLastPlayer() {
        Player temp = this.lastPlayer;
        this.lastPlayer = this.nextPlayer;
        this.nextPlayer = temp;
    }

    //returns copy board with value inserted in given line
    //null if insert not possible
    private Board insertToDuplicateInColumn(int column, Player value) {
        Board boardDuplicate = duplicate();
        if (boardDuplicate.insertInColumn(column, value.getColor())) {
            return boardDuplicate;
        } else {
            return null;
        }
    }

    //return all possible direct constellations after this board
    public ArrayList<Board> generateNextConstellations() {
        Board rootBoard = duplicate();
        ArrayList<Board> constellations = new ArrayList<>();
        for (int i = 0; i < columnLength() - 1; i++) {
            Board currentBoard = rootBoard.duplicate();
            currentBoard = currentBoard.insertToDuplicateInColumn(i, this.nextPlayer);
            if (null != currentBoard) {
                //currentBoard.print();
                constellations.add(currentBoard);
            }
        }
        return constellations;
    }

    public int rowLength() {
        return board.length;
    }

    public int columnLength() {
        return this.board[0].length;
    }

    public String rowToString(int row) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < columnLength(); j++) {
            stringBuffer.append(" "+ this.board[row][j]);
        }
        return stringBuffer.toString();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < rowLength(); i++) {
            stringBuffer.append("[");
            stringBuffer.append(rowToString(i));
            stringBuffer.append(" ]\n");
        }
        return stringBuffer.toString();
    }

    //prunes boards from minimax tree
    public void prune(){this.pruned=true;}

    public String toSimpleString() {
        String str = toString();
        str = str.replace(" ","");
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.replace(Controller.playerFerdi.getColor(),"O");
        str = str.replace(Controller.playerThorben.getColor(),"X");
        str = str.replace("null",".");
        return str;
    }

    public String toSimpleStringWithScore() {
        String str = toSimpleString();
        str = str + String.valueOf(this.score);
        return str;
    }

    public void setBorder(double leftBorder, double rightBorder){
        this.borders = new Pair<>(leftBorder, rightBorder);
    }

    public void clear() {
        this.board = new String[Controller.ROWS][Controller.COLUMNS];
    }

    public boolean isPruned() {
        return this.pruned;
    }

    public BoardElement getLeftNeighbor(int row, int column, Direction direction) {
        switch(direction){
            case HORIZONTAL:
                return new BoardElement(row, column-1, this.get(row, column-1));
            case DIAGONAL:
                return new BoardElement(row - 1, column - 1, this.get(row - 1, column - 1));
            case VERTICAL:
                return new BoardElement(row - 1, column, this.get(row - 1, column));
        }
        return null;
    }

    public BoardElement getLeftNeighbor(BoardElement boardElement, Direction directio) {
        return this.getLeftNeighbor(boardElement.row,boardElement.column,directio);
    }

    public BoardElement getRightNeighbor(int row, int column, Direction direction) {
        switch(direction){
            case HORIZONTAL:
                return new BoardElement(row, column+1, this.get(row, column+1));
            case DIAGONAL:
                return new BoardElement(row + 1, column + 1, this.get(row + 1, column + 1));
            case VERTICAL:
                return new BoardElement(row + 1, column, this.get(row + 1, column));
        }
        return null;
    }

    public BoardElement getRightNeighbor(BoardElement boardElement, Direction direction) {
        return this.getRightNeighbor(boardElement.row,boardElement.column,direction);
    }
}
