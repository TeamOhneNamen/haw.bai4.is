import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.logic.Player;
import app.ui.Controller;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeuristicTest {

    final String black = "BLACK";
    final String white = "WHITE";

    @Test
    public void gameEndedVerticalPositive(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,0, black);
        board.set(0,1, white);
        board.set(1,0, black);
        board.set(0,2, white);
        board.set(2,0, black);
        board.set(1,2, white);
        board.set(3,0, black);
        assertTrue(Controller.currentPlayer.getHeuristic().gameEnded(board, Controller.playerFerdi, Controller.playerThorben)!=null);


    }

    @Test
    public void gameEndedNegative(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,0, black);
        board.set(0,1, white);
        board.set(1,0, black);
        board.set(0,2, white);
        board.set(2,0, black);
        assertTrue(Controller.currentPlayer.getHeuristic().gameEnded(board, Controller.playerFerdi, Controller.playerThorben)!=null);

    }

    @Test
    public void gameEndedHorizontalPositive(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,0, black);
        board.set(1,1, white);
        board.set(0,1, black);
        board.set(2,0, white);
        board.set(0,2, black);
        board.set(0,4, white);
        board.set(0,3, black);
        assertTrue(Controller.currentPlayer.getHeuristic().gameEnded(board, Controller.playerFerdi, Controller.playerThorben)!=null);

    }

}
