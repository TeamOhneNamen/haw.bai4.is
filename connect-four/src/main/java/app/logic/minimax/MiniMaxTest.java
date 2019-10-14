package app.logic.minimax;

import app.logic.Board;

import java.util.ArrayList;

public class MiniMaxTest {


    public static void main(String [ ] args)
    {
        generateNextConstellationsSixPossibleTest();
    }

    public static void generateNextConstellationsSixPossibleTest(){
        final String black = "BLACK";
        final String white = "WHITE";
        Board board = new Board(black,white);
        board.set(5,0, black);
        board.set(5,1, black);
        board.set(5,2, black);

        TreeNode<Board> tree = MiniMax.contructTreeNode(board,2);
        TreeNode.printTree(tree, ";");
    }
}
