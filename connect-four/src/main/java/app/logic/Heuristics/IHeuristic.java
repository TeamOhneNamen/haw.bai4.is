package app.logic.Heuristics;

import app.logic.Board;

public interface IHeuristic {

    double determineScore(Board board);

}
