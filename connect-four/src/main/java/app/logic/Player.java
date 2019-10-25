package app.logic;

import app.logic.Heuristics.IHeuristic;

public class Player {

    IHeuristic heuristic;
    String color;
    String name;

    public Player(IHeuristic heuristicPar, String colorPar, String namePar) {
        this.color = colorPar;
        this.heuristic = heuristicPar;
        this.name = namePar;
    }

    public IHeuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
