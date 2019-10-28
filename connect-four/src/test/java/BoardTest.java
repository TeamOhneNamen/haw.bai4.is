import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.logic.Player;
import app.ui.Controller;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class BoardTest {
    final String black = "BLACK";
    final String white = "WHITE";

    @Test
    public void generateNextConstellationsSixPossibleTest(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(5,0, black);
        board.set(5,1, black);
        board.set(5,2, black);

        ArrayList<Board> constellations = board.generateNextConstellations();
        assertEquals(6, constellations.size());
    }

    @Test
    public void generateNextConstellationsFivePossibleTest(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,1, black);
        board.set(1,1, black);
        board.set(2,1, black);
        board.set(3,1, black);
        board.set(4,1, black);
        board.set(5,1, black);

        ArrayList<Board> constellations = board.generateNextConstellations();
        assertEquals(5, constellations.size());
    }

    @Test
    public void duplicateTestNegative(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,1, black);
        board.set(1,1, black);
        board.set(2,1, black);
        board.set(3,1, black);
        board.set(4,1, black);
        board.set(5,1, black);

        Board duplicate = board.duplicate();
        int row = 0;
        int col = 3;
        duplicate.set(row,col, white);
        assertNotEquals(white, board.get(row,col));
    }

    @Test
    public void duplicateTestPositive(){
        Board board = new Board(new Player(new Heuristic(), Controller.playerFerdi.getColor(), Controller.playerFerdi.getName()),new Player(new Heuristic(), Controller.playerThorben.getColor(), Controller.playerThorben.getName()));
        board.set(0,1, black);
        board.set(1,1, black);
        board.set(2,1, black);
        board.set(3,1, black);
        board.set(4,1, black);
        board.set(5,1, black);

        Board duplicate = board.duplicate();
        int row = 0;
        int col = 3;
        assertNotEquals(white, board.get(row,col));
    }
}
