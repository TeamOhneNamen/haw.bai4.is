package app.logic;

import app.ui.Controller;

import java.util.concurrent.ThreadLocalRandom;

//Based on this idea: https://cs.stackexchange.com/a/13455
public class Heuristic {

    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 3;

    public static double determineScore(Board board, String playerColor) {
        //TODO: add functionality
        double score = ThreadLocalRandom.current().nextInt(1, 7);
        return score;
    }

    public static double determineScore(String playerColor) {
        //TODO: add functionality
        double score = ThreadLocalRandom.current().nextInt(1, 7);
        return score;
    }

    public static double determineScore(Board board) {
        String wayToMove = "HORIZONTAL";
        determineHorizontalScore(board, board.nextPlayerColor);
        return 0.0;
    }

    public static double determineHorizontalScore(Board board, String playerColor) {
        int emptyLeftNeighbors = 0;
        int emptyRightNeighbors = 0;
        int inARow = 0;
        String wayToMove = "HORIZONTAL";
        for (int i = 0; i < board.rowLength(); i++) {
            int j = 0;
            int column;
            while (j < board.columnLength()) {
                column = j;
                // 1 in a row
                if (playerColor.equals(board.get(i, column))) {
                    inARow++;
                    // space to the left
                    emptyLeftNeighbors = checkLeftNeighbors(board, i, column, wayToMove);
                    j++;
                    BoardElement rightNeighbor = board.getRightNeighbor(i, column, wayToMove);

                    for (int h = 0; h < (AMOUNT_OF_NEIGHBORS_TO_CHECK+2); h++) {

                        if (playerColor.equals(rightNeighbor.data)) {
                            inARow++;
                            j++;
                            rightNeighbor = board.getRightNeighbor(rightNeighbor, wayToMove);
                        } else if (board.NO_COLOR.equals(rightNeighbor.data)) {
                            emptyRightNeighbors++;
                            j++;
                            rightNeighbor = board.getRightNeighbor(rightNeighbor, wayToMove);
                            //field reserved by the enemy
                        }else {
                            j++;
                            break;
                        }
                    }
                }else {
                    j++;
                }
            }
        }
        return evaluate(emptyLeftNeighbors, emptyRightNeighbors, inARow);
    }

    private static int checkLeftNeighbors(Board board, int row, int column, String wayToMove) {
        int emptyLeftNeighbors = 0;
        BoardElement leftNeighbor = board.getLeftNeighbor(row, column, wayToMove);
        for (int i = 0; i < AMOUNT_OF_NEIGHBORS_TO_CHECK; i++) {
            if (Board.NO_COLOR.equals(leftNeighbor.data)) {
                emptyLeftNeighbors++;
                leftNeighbor = board.getLeftNeighbor(row, column, wayToMove);
            } else {
                break;
            }
        }
        return emptyLeftNeighbors;
    }

    private static double evaluate(int emptyLeftNeighbors, int emptyRightNeighbors, int inARow) {
        System.out.println("Left: " + emptyLeftNeighbors + " Right: " + emptyRightNeighbors + " In A Row: " + inARow);
        double score = 0.0;
        if (emptyLeftNeighbors + emptyRightNeighbors + inARow > 3) {
            score = inARow;
            if (emptyLeftNeighbors > 0) {
                score += 0.5;
            }
            if (emptyRightNeighbors > 0) {
                score += 0.5;
            }
        }
        return score;
    }
}
