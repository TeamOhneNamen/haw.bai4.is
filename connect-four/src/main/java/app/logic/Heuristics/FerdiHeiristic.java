package app.logic.Heuristics;

import app.logic.Board;
import app.ui.Controller;

public class FerdiHeiristic implements IHeuristic {


    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 4;

    @Override
    public double determineScore(Board board) {
        double score = 0.0;
        score = score + ecalUp(board, Controller.discColor1);
        score = score + ecalSide(board, Controller.discColor1);
        score = score + ecalDia1(board, Controller.discColor1);
        score = score + ecalDia2(board, Controller.discColor1);
        return score;
    }


    private double ecalUp(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i, j+k)){
                        score = score+2;
                    }else if (isFree(board, i, j+k)){
                        score = score+1;
                    }
                }
            }
        }
        return score;
    }

    private double ecalSide(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j)){
                        score = score+2;
                    }else if (isFree(board, i+k, j)){
                        score = score+1;
                    }
                }
            }
        }
        return score;
    }

    private double ecalDia1(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j+k)){
                        score = score+2;
                    }else if (isFree(board, i+k, j+k)){
                        score = score+1;
                    }
                }
            }
        }
        return score;
    }

    private double ecalDia2(Board board, String playerColor){
        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerColor, i+k, j-k)){
                        score = score+2;
                    }else if (isFree(board, i+k, j-k)){
                        score = score+1;
                    }
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
