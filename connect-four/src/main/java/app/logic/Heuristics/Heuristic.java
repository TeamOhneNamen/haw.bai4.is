package app.logic.Heuristics;

import app.logic.Board;
import app.logic.BoardElement;
import app.logic.minimax.Direction;
import app.ui.Controller;

import java.util.concurrent.ThreadLocalRandom;

//Based on this idea: https://cs.stackexchange.com/a/13455
public class Heuristic implements IHeuristic{

    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 4;

    public double determineScore(Board board) {
        String playerColor = board.nextPlayerColor;
        int emptyLeftNeighbors = 0;
        int emptyRightNeighbors = 0;
        int inARow = 0;
        int outerCountBorder = 0;
        int innerCountBorder = 0;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            switch (direction) {
                case HORIZONTAL:
                case DIAGONAL:
                    outerCountBorder = board.rowLength();
                    innerCountBorder = board.columnLength();
                    break;
                case VERTICAL:
                    outerCountBorder = board.columnLength();
                    innerCountBorder = board.rowLength();
                    break;
                default:
                    System.err.println("Keine gueltige Richtung!");
            }
            for (int i = 0; i < outerCountBorder; i++) {
                int j = 0;
                while (j < innerCountBorder) {
                    int column = 0;
                    int row = 0;
                    switch (direction) {
                        case HORIZONTAL:
                        case DIAGONAL:
                            column = j;
                            row = i;
                            break;
                        case VERTICAL:
                            column = i;
                            row = j;
                            break;
                    }
                            // 1 in a row
                            if (playerColor.equals(board.get(row, column))) {
                                inARow++;
                                // space to the left
                                emptyLeftNeighbors = checkNeighbors(board, row, column, direction);
                                j++;
                                BoardElement rightNeighbor = board.getRightNeighbor(row, column, direction);

                                for (int h = 0; h < (AMOUNT_OF_NEIGHBORS_TO_CHECK + 2); h++) {

                                    if (playerColor.equals(rightNeighbor.data)) {
                                        inARow++;
                                        j++;
                                        rightNeighbor = board.getRightNeighbor(rightNeighbor, direction);
                                    } else if (board.NO_COLOR.equals(rightNeighbor.data)) {
                                        emptyRightNeighbors++;
                                        j++;
                                        rightNeighbor = board.getRightNeighbor(rightNeighbor, direction);
                                        //field reserved by the enemy
                                    } else {
                                        j++;
                                        break;
                                    }
                                }
                            } else {
                                j++;
                            }
                    }
                }
            }
        return evaluate(emptyLeftNeighbors, emptyRightNeighbors, inARow);
    }

    double evaluate(int emptyLeftNeighbors, int emptyRightNeighbors, int inARow) {
        //System.out.println("Left: " + emptyLeftNeighbors + " Right: " + emptyRightNeighbors + " In A Row: " + inARow);
        double score = 0.0;
        if (emptyLeftNeighbors + emptyRightNeighbors + inARow >= 4) {
            score = inARow;
            if (emptyLeftNeighbors >= 1) {
                score += 0.5;
            }
            if (emptyRightNeighbors >= 1) {
                score += 0.5;
            }
        }
        return score;
    }

    private int checkNeighbors(Board board, int row, int column, Direction direction) {
        int emptyLeftNeighbors = 0;
        BoardElement leftNeighbor = board.getLeftNeighbor(row, column, direction);
        for (int i = 0; i < AMOUNT_OF_NEIGHBORS_TO_CHECK; i++) {
            if (Board.NO_COLOR.equals(leftNeighbor.data)) {
                emptyLeftNeighbors++;
                leftNeighbor = board.getLeftNeighbor(leftNeighbor,direction);
            } else {
                break;
            }
        }
        return emptyLeftNeighbors;
    }
    }
