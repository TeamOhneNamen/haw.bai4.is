package app.logic.minimax;

import app.logic.Board;

import java.util.ArrayList;

public class MiniMax {
    //contructs a MiniMax Tree with board as the root node
    //with a specific depth
    public static TreeNode<Board> contructTreeNode(Board board, int depth){
        if(depth == 0){
            return new TreeNode<Board>(board);
        }
        TreeNode<Board> tree = new TreeNode<Board>(board);
        ArrayList<Board> nextConstellations = board.generateNextConstellations();
        nextConstellations.forEach(constellation ->
                tree.addChildNode(contructTreeNode(constellation, depth-1)));
        return tree;
    }
}
