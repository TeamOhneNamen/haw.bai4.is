package app.logic.Heuristics;

import app.logic.Board;
import app.logic.Player;

public interface IHeuristic {

    double determineScore(Board board);
    double determineScore(Board board, Player color);

}
