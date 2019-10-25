package app.logic.Heuristics;

import app.logic.Board;
import app.logic.Player;
import app.ui.Controller;

public class FerdiHeiristic implements IHeuristic {


    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 4;

    private Player player;

    @Override
    public double determineScore(Board board) {
        double score = 0.0;
        score = score + ecalUp(board, player.getColor());
        score = score + ecalSide(board, player.getColor());
        score = score + ecalDia(board, player.getColor());
        return score;
    }

    @Override
    public double determineScore(Board board, Player player) {
        this.player = player;
        return determineScore(board);
    }

    private double ecalDia(Board board, String playerColor){
        double erg = ecalDia1(board, playerColor);
        erg += ecalDia2(board, playerColor);
        return erg;
    }

    private double ecalUp(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i, j+k)){
                        score = score+2;
                    }else if (isFree(board, i, j+k)){
                        score = score+1;
                    }
                }
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    return 1000000;
                }
            }
        }
        return score;
    }

    private double ecalRight(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j)){
                        inARow++;
                        score = score+2;
                    }else if (isFree(board, i+k, j)){
                        score = score+1;
                    }
                }
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    return 1000000;
                }
                if(isFree(board, i, j)){
                    boolean isThreeInThrRow = true;
                    for (int l = 0; l < AMOUNT_OF_NEIGHBORS_TO_CHECK-1; l++) {
                        if(!isSameColor(board, playerColor, i+l, j)){
                            isThreeInThrRow = false;
                        }
                    }
                    if(isThreeInThrRow&&isFree(board, i+AMOUNT_OF_NEIGHBORS_TO_CHECK, j)){
                        score = score+1000;
                        break;
                    }
                }


            }
        }
        return score;
    }

    private double ecalLeft(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i-k, j)){
                        score = score+2;
                    }else if (isFree(board, i-k, j)){
                        score = score+1;
                    }
                }
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    return 1000000;
                }
                if(isFree(board, i, j)){
                    boolean isThreeInThrRow = true;
                    for (int l = 0; l < AMOUNT_OF_NEIGHBORS_TO_CHECK-1; l++) {
                        if(!isSameColor(board, playerColor, i-l, j)){
                            isThreeInThrRow = false;
                            break;
                        }
                    }
                    if(isThreeInThrRow&&isFree(board, i-AMOUNT_OF_NEIGHBORS_TO_CHECK, j)){
                        score = score+1000;
                    }
                }
            }
        }
        return score;
    }

    private double ecalSide(Board board, String playerColor){
        double score = ecalLeft(board, playerColor);
        score += ecalRight(board, playerColor);
        return score;
    }

    private double ecalDia1(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j+k)){
                        score = score+2;
                    }else if (isFree(board, i+k, j+k)){
                        score = score+1;
                    }
                }
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    return 1000000;
                }
            }
        }
        return score;
    }

    private double ecalDia2(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j-k)){
                        score = score+2;
                    }else if (isFree(board, i+k, j-k)){
                        score = score+1;
                    }
                }
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    return 1000000;
                }
            }
        }
        return score;
    }

    private boolean isSameColor(Board board, String playerColor, int i, int j){
        if(board.get(i, j)!=null){
            if(board.get(i, j).equals(playerColor)){
                return true;
            }
        }
        return false;
    }

    private boolean isFree(Board board, int i, int j){
        if(board.get(i, j)!=null){
            if(board.get(i, j).equals(Board.NO_COLOR)){
                return true;
            }
        }
        return false;
    }

}
