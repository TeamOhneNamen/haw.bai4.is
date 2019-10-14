package app.logic;

import app.ui.Controller;

import java.util.ArrayList;

// a more simple form of the disc matrix in Controller
// stores just the color of the disc
// for using it in the heuristic
public class Board {
    private String[][] board;
    public final String NO_COLOR = "NONE";
    //the disc color of the player who is on it
    //used for generating next possible constellations
    public String nextPlayerColor;
    //the disc color of the player who made the last move
    //used for generating next possible constellations
    public String lastPlayerColor;

    public Board (String[][] board, String lastPlayerColor, String nextPlayerColor){
        this.board = board;
        this.nextPlayerColor = nextPlayerColor;
    }

    public Board (String lastPlayerColor, String nextPlayerColor){
        this.board = new String[Controller.ROWS][Controller.COLUMNS];
        this.nextPlayerColor = nextPlayerColor;
    }

    public Board duplicate(){
        return new Board(this.board.clone(), this.lastPlayerColor,  this.nextPlayerColor);
    }

    public void setNextPlayerColor(String nextPlayerColor){
        this.nextPlayerColor = nextPlayerColor;
    }

    public void setLastPlayerColor(String lastPlayerColor){
        this.lastPlayerColor = lastPlayerColor;
    }

    public void set(int row, int column, String value) {
        this.board[row][column] = value;
        this.switchCurrentLastPlayer();
    }

    public String get(int row, int column) {
        String color;
        try {
            color = this.board[row][column];
            if(null == color){
                color = NO_COLOR;
            }
        } catch (IndexOutOfBoundsException e) {
            color = null;
        }
        return color;
    }
    //returns if the value could be inserted in the given line
    public boolean insertInLine(int column, String value) {
        boolean inserted = false;
        int row = 0;
        while(!inserted || row < columnLength()){
            if(null == this.board[row][column]){
                this.board[row][column] = value;
                inserted = true;
                this.switchCurrentLastPlayer();
            }
        }
        return inserted;
    }

    public void switchCurrentLastPlayer(){
        String temp = this.lastPlayerColor;
        this.lastPlayerColor = this.nextPlayerColor;
        this.nextPlayerColor = temp;
    }

    //returns copy board with value inserted in given line
    //null if insert not possible
    public Board insertToDuplicateInLine(int column, String value) {
        Board boardDuplicate = duplicate();
        if(boardDuplicate.insertInLine(column,value)){
            return boardDuplicate;
        }else {
            return null;
        }
    }

    //return all possible direct constellations after this board
    public ArrayList<Board> generateNextConstelations(){
        ArrayList<Board> constellations = new ArrayList<Board>();
        for(int i=0; i<rowLength(); i++){
            Board board = insertToDuplicateInLine(i, this.nextPlayerColor);
            if(null != board){
                constellations.add(board);
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

    public void print() {
        for (int i = 0; i < rowLength(); i++) {
            System.out.print("[");
            printRow( i);
            System.out.println(" ]");
        }
        System.out.println();
    }

    public void printRow(int row) {
        for (int j = 0; j < rowLength(); j++) {
            System.out.print(" " + this.board[row][j]);
        }
    }
    public void clear() {
        this.board = new String[Controller.ROWS][Controller.COLUMNS];
    }
}
