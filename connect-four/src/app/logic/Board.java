package app.logic;

import app.ui.Controller;

import java.util.Arrays;

// a more simple form of the disc matrix in Controller
// stores just the color of the disc
// for using it in the heuristic
public class Board {
    private String[][] board = new String[Controller.ROWS][Controller.COLUMNS];
    public final String NO_COLOR = "NONE";

    public void set(int row, int column, String value) {
        this.board[row][column] = value;
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
            }
        }
        return inserted;
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
