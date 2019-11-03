import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.logic.Player;
import app.ui.Controller;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FerdiHeuristicTest {

    final String black = "BLACK";
    final String white = "WHITE";

    @Test
    public void horizontalScoreFirstFieldsFilled(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,0, black);
        board.set(0,1, black);
        board.set(0,2, black);
        System.out.println();
        assertEquals(3.5, Controller.playerFerdi.getHeuristic().determineScore(board, Controller.playerFerdi, Controller.playerThorben), 0.0);
    }

    @Test
    public void horizontalScoreNotFirstFieldsFilled(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,1, black);
        board.set(0,2, black);
        board.set(0,3, black);
        assertEquals(7.5,Controller.playerFerdi.getHeuristic().determineScore(board, Controller.playerFerdi, Controller.playerThorben), 0.0);
    }

    @Test
    public void horizontalScoreSingleDisk(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(2,2, black);
        assertEquals(4.0,Controller.playerFerdi.getHeuristic().determineScore(board, Controller.playerFerdi, Controller.playerThorben), 0.0);
    }

    @Test
    public void horizontalScoreNoDisk(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        assertEquals(0,Controller.playerFerdi.getHeuristic().determineScore(board, Controller.playerFerdi, Controller.playerThorben), 0.0);
    }

    @Test
    public void horizontalScoreNoChanceToConnectFour(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,1, black);
        board.set(0,2, black);
        board.set(0,3, white);
        assertEquals(5.5,Controller.playerFerdi.getHeuristic().determineScore(board, Controller.playerFerdi, Controller.playerThorben), 0.0);
    }
}
