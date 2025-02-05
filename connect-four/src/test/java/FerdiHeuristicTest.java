import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.ui.Controller;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FerdiHeuristicTest {

    final String black = "BLACK";
    final String white = "WHITE";

    @Test
    public void horizontalScoreFirstFieldsFilled(){
        Board board = new Board(black,white);
        board.set(0,0, black);
        board.set(0,1, black);
        board.set(0,2, black);
        System.out.println();
        assertEquals(3.5, Controller.heuristic.determineScore(board), 0.0);
    }

    @Test
    public void horizontalScoreNotFirstFieldsFilled(){
        Board board = new Board(black,white);
        board.set(0,1, black);
        board.set(0,2, black);
        board.set(0,3, black);
        assertEquals(7.5,Controller.heuristic.determineScore(board), 0.0);
    }

    @Test
    public void horizontalScoreSingleDisk(){
        Board board = new Board(black,white);
        board.set(2,2, black);
        assertEquals(4.0,Controller.heuristic.determineScore(board), 0.0);
    }

    @Test
    public void horizontalScoreNoDisk(){
        Board board = new Board(black,white);
        assertEquals(0,Controller.heuristic.determineScore(board), 0.0);
    }

    @Test
    public void horizontalScoreNoChanceToConnectFour(){
        Board board = new Board(black,white);
        board.set(0,1, black);
        board.set(0,2, black);
        board.set(0,3, white);
        assertEquals(5.5,Controller.heuristic.determineScore(board), 0.0);
    }
}
