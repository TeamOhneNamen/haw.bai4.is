package app.logic.minimax;

import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.logic.Heuristics.IHeuristic;
import app.logic.Player;
import app.ui.Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MiniMaxFerdi {

    private static int searchDeph = 6;
    private static int gespeicherterZug = Integer.MIN_VALUE;

    Player player_max;
    Player player_min;

    public MiniMaxFerdi(Player max, int deph,  Player min) {
        this.searchDeph = deph;
        this.player_max = max;
        this.player_min = min;
    }

    public int minmax(Board currentBoard){
        LocalDateTime start = LocalDateTime.now();
        double bewertung = max(searchDeph,Integer.MIN_VALUE, Integer.MAX_VALUE, currentBoard);

        double milis = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
        double sec = ChronoUnit.SECONDS.between(start, LocalDateTime.now());
        double min = ChronoUnit.MINUTES.between(start, LocalDateTime.now());
        sec = sec-(min*60);
        milis = milis-(sec*1000);
        System.out.println((int)min+":"+(int)sec+":"+(int)milis);

        return gespeicherterZug;

    }

    double max(int tiefe, double alpha, double beta, Board board) {
        List<Board> moeglicheZuege = generiereMoeglicheZuege(board, player_max);
        if (tiefe == 0 || moeglicheZuege.size()==0 || player_max.getHeuristic().gameEnded(board, player_max, player_min)!=null){
            if(player_max.getHeuristic().gameEnded(board, player_max, player_min)==player_max){
                return 1000000;
            }else if (player_max.getHeuristic().gameEnded(board, player_max, player_min)==player_min){
                return -1000000;
            }else{
                return player_max.getHeuristic().determineScore(board, player_max, player_min);

            }

        }
        double maxWert = alpha;
        for(Board tempBoard: moeglicheZuege) {
            double wert = min(tiefe-1, maxWert, beta, tempBoard);
            if (wert > maxWert) {
                maxWert = wert;
                if (tiefe == searchDeph)
                    gespeicherterZug = tempBoard.getLastMove().column;
                if (maxWert >= beta)
                    break;
            }
        }
        return maxWert;
    }

    double min(int tiefe, double alpha, double beta, Board board) {
        List<Board> nextMoves = generiereMoeglicheZuege(board, player_min);
        if (tiefe == 0 || nextMoves.size()==0 || player_max.getHeuristic().gameEnded(board, player_max, player_min)!=null){
            if(player_max.getHeuristic().gameEnded(board, player_max, player_min)==player_max){
                return 1000000;
            }else if (player_max.getHeuristic().gameEnded(board, player_max, player_min)==player_min){
                return -1000000;
            }else{
                return player_max.getHeuristic().determineScore(board, player_min, player_max);
            }
        }
        double minWert = beta;
        for(Board tempBoard: nextMoves) {
            double wert = max(tiefe-1, alpha, minWert, tempBoard);
            if (wert < minWert) {
                minWert = wert;
                if (minWert <= alpha)
                    break;
            }
        }
        return minWert;
    }

    private List<Board> generiereMoeglicheZuege(Board board, Player player){
        List<Board> steps = new ArrayList<>();
        for (int i = 0; i < Controller.COLUMNS; i++) {
            Board tempBoard = board.duplicate();
            if(tempBoard.insertInColumn(i, player.getColor())){
                steps.add(tempBoard);
            }
        }
        return steps;
    }

}
