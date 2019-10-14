package app.logic.minimax;

import app.logic.Board;

public class MiniMax {
    //contructs a MiniMax Tree with board as the root node
    //with a specific depth
    public static TreeNode<Board> contructTreeNode(Board board, int depth){
        return new TreeNode<>(board);
    }
}
