package app.logic.Heuristics;

import app.logic.Board;
import app.logic.Player;

public class FerdiHeuristic implements IHeuristic {


    final static int AMOUNT_OF_NEIGHBORS_TO_CHECK = 4;
    static Player playerMax;
    static Player playerMin;

    @Override
    public double determineScore(Board board, Player playerMaxPara, Player playerMinPara) {
        this.playerMax = playerMaxPara;
        this.playerMin = playerMinPara;
        return determineScore(board);
    }

    public double determineScore(Board board) {

        double scoreMax = 0.0;
        scoreMax = scoreMax + ecalUp(board, this.playerMax);
        scoreMax = scoreMax + ecalSide(board, this.playerMax);
        scoreMax = scoreMax + ecalDia(board, this.playerMax);
        double scoreMin = 0.0;
        double ecalDown = ecalUp(board, this.playerMin);
        if(ecalDown==1000000){
            return -4000000;
        }
        scoreMin = scoreMin + ecalDown;
        double ecalSide = ecalSide(board, this.playerMin);
        if(ecalSide==1000000){
            return -4000000;
        }
        scoreMin = scoreMin + ecalSide;
        double ecalDia = ecalDia(board, this.playerMin);
        if(ecalDia==1000000){
                return -4000000;
        }
        scoreMin = scoreMin + ecalDia;

        return scoreMax - scoreMin;
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
    public Player gameEnded(Board board, Player player1, Player player2) {

        if(ecalSide(board, player1)>=1000000 || ecalDia(board, player1)>=1000000 || ecalUp(board, player1)>=1000000){
            return player1;
        }else if(ecalSide(board, player2)>=1000000 || ecalDia(board, player2)>=1000000 || ecalUp(board, player2)>=1000000){
            return player2;
        }else{
            return null;
        }
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
                        tempScore = tempScore*tempScore+1;
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
                                if(!isFree(board, i + (AMOUNT_OF_NEIGHBORS_TO_CHECK * xMultiplyer)-1, j + (AMOUNT_OF_NEIGHBORS_TO_CHECK * yMultiplyer))){
                                    score = score + 900000;
                                    //System.out.println("UNLOOSABLE");
                                    break;
                                }else{
                                    score = score + 1000;
                                }
                            }
                        }

                    }
                }


            }
        }
        return score;
    }

}
