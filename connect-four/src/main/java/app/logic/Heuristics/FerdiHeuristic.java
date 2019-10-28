package app.logic.Heuristics;

import app.logic.Board;
import app.logic.Player;

public class FerdiHeuristic implements IHeuristic {


    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 4;
    static Player player;

    @Override
    public double determineScore(Board board) {
        if(this.player==null){
            throw new NullPointerException();
        }
        return determineScore(board, this.player);
    }

    @Override
    public double determineScore(Board board, Player player) {
        this.player = player;
        double score = 0.0;
        score = score + ecalUp(board, this.player);
        score = score + ecalSide(board, this.player);
        score = score + ecalDia(board, this.player);
        return score;
    }

    private double ecalDia(Board board, Player playerPara){
        double erg = ecalDia1(board, playerPara);
        erg += ecalDia2(board, playerPara);
        return erg;
    }

    private double ecalUp(Board board, Player playerPara){
        return checkDir(board, playerPara, 1, 0);
    }

    private double ecalSide(Board board, Player playerPara){
        return checkDir(board, playerPara, 0, +1);
    }

    private double ecalDia1(Board board, Player playerPara){
        return checkDir(board, playerPara, 1, 1);
    }

    private double ecalDia2(Board board, Player playerPara){
        return checkDir(board, playerPara, 1, -1);
    }

    private boolean isSameColor(Board board, Player playerPara, int i, int j){
        if(board.get(i, j)!=null) {
            if (!board.get(i, j).equals("NONE")) {
                if (board.get(i, j).equals(playerPara.getColor())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFree(Board board, int i, int j){
        if(board.get(i, j)!=null) {
            if (board.get(i, j).equals(Board.NO_COLOR)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean gameEnded(Board board, Player playerPara) {
        return ecalSide(board, playerPara)>=1000000 || ecalDia(board, playerPara)>=1000000 || ecalUp(board, playerPara)>=1000000;
    }

    public double checkDir(Board board, Player playerPara, int xMultiplyer, int yMultiplyer){

        double score = 0.0;
        for (int i = 0; i < board.rowLength(); i++) {
            for (int j = 0; j < board.columnLength(); j++) {
                int inARow = 0;
                double tempScore = 0.0;
                for (int k = 0; k < AMOUNT_OF_NEIGHBORS_TO_CHECK; k++) {
                    if(isSameColor(board, playerPara, i+(k*xMultiplyer), j+(k*yMultiplyer))){
                        inARow++;
                        tempScore = tempScore+2;
                    }else if (isFree(board, i+(k*xMultiplyer), j+(k*yMultiplyer))){
                        tempScore = tempScore+1;
                    }else {
                        tempScore = 0.0;
                        break;
                    }
                }
                score = score + tempScore;
                if(inARow==AMOUNT_OF_NEIGHBORS_TO_CHECK){
                    //System.out.println("WON");
                    return 1000000;
                }
                if(isFree(board, i, j)){
                    if(!isFree(board, i+1, j)) {

                        boolean isThreeInThrRow = true;
                        for (int l = 0; l < AMOUNT_OF_NEIGHBORS_TO_CHECK - 1; l++) {
                            if (!isSameColor(board, playerPara, i + (l * xMultiplyer) + xMultiplyer, j + (l * yMultiplyer)+ yMultiplyer)) {
                                isThreeInThrRow = false;
                                break;
                            }
                        }
                        if(isThreeInThrRow){
                            //System.out.println("ist " + (i + (AMOUNT_OF_NEIGHBORS_TO_CHECK * xMultiplyer)) + " : " + (j + (AMOUNT_OF_NEIGHBORS_TO_CHECK * yMultiplyer)) + " frei?");
                            if (isFree(board, i + (AMOUNT_OF_NEIGHBORS_TO_CHECK * xMultiplyer), j + (AMOUNT_OF_NEIGHBORS_TO_CHECK * yMultiplyer))) {
                                score = score + 1000;
                                //System.out.println("UNLOOSABLE");
                                break;
                            }
                        }

                    }
                }


            }
        }
        return score;
    }

}
