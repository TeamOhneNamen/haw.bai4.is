package app.logic;

import app.ui.Controller;

import java.util.Arrays;

// a more simple form of the disc matrix in Controller
// stores just the color of the disc
// for using it in the heuristic
public class Board {
    private static String[][] board = new String[Controller.ROWS][Controller.COLUMNS];
    public static final String NO_COLOR = "NONE";

    public static void set(int row, int column, String value) {
        board[row][column] = value;
    }

    public static String get(int row, int column) {
        String color;
        try {
            color = board[row][column];
            if(null == color){
                color = NO_COLOR;
            }
        } catch (IndexOutOfBoundsException e) {
            color = null;
        }
        return color;
    }

    public static int rowLength() {
        return board.length;
    }

    public static int columnLength() {
        return board[0].length;
    }

    public static void print() {
        for (int i = 0; i < rowLength(); i++) {
            System.out.print("[");
            printRow( i);
            System.out.println(" ]");
        }
        System.out.println();
    }

    public static void printRow(int row) {
        for (int j = 0; j < rowLength(); j++) {
            System.out.print(" " + board[row][j]);
        }
    }
    public static void clear() {
        board = new String[Controller.ROWS][Controller.COLUMNS];
    }
}
