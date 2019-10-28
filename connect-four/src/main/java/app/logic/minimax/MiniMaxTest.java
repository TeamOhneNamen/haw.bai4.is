package app.logic.minimax;

import app.logic.Board;
import app.ui.Controller;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.IOException;

import static app.logic.minimax.MiniMax.contructTree;

public class MiniMaxTest {


    public static void main(String [ ] args)
    {
        miniMaxTest();
    }

    public static void miniMaxTest(){
        final String fileName = "src/main/resources/graph/miniMaxTest.";
        Board board = new Board(Controller.playerThorben,Controller.playerFerdi);
        board.insertInColumn(5, Controller.playerFerdi.getColor());
        board.insertInColumn(1, Controller.playerThorben.getColor());
        board.insertInColumn(5, Controller.playerFerdi.getColor());

        TreeNode<Board> tree = contructTree(board,2);
        MiniMax.miniMax(tree,true);


        String treeString = TreeNode.treeToString(tree,true);

        try {
            MutableGraph g = Parser.read(treeString);
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(fileName+"png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
