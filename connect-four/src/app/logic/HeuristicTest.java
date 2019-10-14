package app.logic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeuristicTest {

    @Test
    public void horizontalScoreFirstFieldsFilled(){
        Board board = new Board();
        final String color = "BLACK";
        board.set(0,0, color);
        board.set(0,1, color);
        board.set(0,2, color);
        assertEquals(3.5,Heuristic.determineHorizontalScore(board, color), 0.0);
    }

    @Test
    public void horizontalScoreNotFirstFieldsFilled(){
        Board board = new Board();
        final String color = "BLACK";
        board.set(0,1, color);
        board.set(0,2, color);
        board.set(0,3, color);
        assertEquals(4,Heuristic.determineHorizontalScore(board, color), 0.0);
    }

    @Test
    public void horizontalScoreNoChanceToConnectFour(){
        Board board = new Board();
        final String color = "BLACK";
        final String color2 = "WHITE";
        board.set(0,1, color);
        board.set(0,2, color);
        board.set(0,3, color2);
        assertEquals(0,Heuristic.determineHorizontalScore(board, color), 0.0);
    }
}
