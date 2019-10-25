package app.logic.minimax;

import app.logic.Board;
import app.logic.Heuristics.IHeuristic;
import app.logic.Player;
import app.ui.Controller;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxFerdi {

    private static int searchDeph = 4;
    private static IHeuristic heuristic;
    private static int gespeicherterZug = Integer.MIN_VALUE;

    Player player_max;
    Player player_min;

    public MiniMaxFerdi(IHeuristic heuristic, Player max, Player min) {
        this.heuristic = heuristic;
        this.player_max = max;
        this.player_min = min;
    }

    public int minmax(Board currentBoard){
        double bewertung = max(searchDeph,Integer.MIN_VALUE, Integer.MAX_VALUE, currentBoard);
        return gespeicherterZug;

    }

    double max(int tiefe, double alpha, double beta, Board board) {
        if (tiefe == 0 || generiereMoeglicheZuege(board, player_max).size()==0){
            return heuristic.determineScore(board);
        }
        double maxWert = alpha;
        List<Board> nextMoves = generiereMoeglicheZuege(board, player_max);
        for(Board tempBoard: nextMoves) {
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
        if (tiefe == 0 || generiereMoeglicheZuege(board, player_min).size()==0){
            return heuristic.determineScore(board);
        }
        double minWert = beta;
        List<Board> nextMoves = generiereMoeglicheZuege(board, player_min);
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
