package app.logic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeuristicTest {

    @Test
    public void horizontalScoreFirstFieldsFilled(){
        Board.clear();
        final String color = "BLACK";
        Board.set(0,0, color);
        Board.set(0,1, color);
        Board.set(0,2, color);
        assertEquals(3.5,Heuristic.determineHorizontalScore(color), 0.0);
    }

    @Test
    public void horizontalScoreNotFirstFieldsFilled(){
        Board.clear();
        final String color = "BLACK";
        Board.set(0,1, color);
        Board.set(0,2, color);
        Board.set(0,3, color);
        assertEquals(4,Heuristic.determineHorizontalScore(color), 0.0);
    }
}
