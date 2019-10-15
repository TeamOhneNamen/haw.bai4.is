package app.logic.minimax;

import app.logic.Board;
import app.logic.Heuristic;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=l-hh51ncgDI
public class MiniMax {
    //contructs a MiniMax Tree with board as the root node
    //with a specific depth
    public static TreeNode<Board> contructTree(Board board, int depth){
        if(depth == 0){
            return new TreeNode<Board>(board);
        }
        TreeNode<Board> tree = new TreeNode<Board>(board);
        ArrayList<Board> nextConstellations = board.generateNextConstellations();
        nextConstellations.forEach(constellation ->
                tree.addChildNode(contructTree(constellation, depth-1)));
        return tree;
    }

    public static double miniMax(TreeNode<Board> tree){
        if(tree.isLeaf()){
            Board board = tree.data;
            board.score = Heuristic.determineScore(board,board.nextPlayerColor);
            return board.score;
        }
        //maximizer is next to move
        if(Board.MAXIMIZER.equals(tree.data.nextPlayerColor)){
            double maxEval = Board.LOWEST_NUMBER;
            for(int i = 0; i < tree.children.size()-1; i++){
                double eval = miniMax(tree.children.get(i));
                maxEval = ((maxEval > eval) ? maxEval : eval);
            }
            tree.data.score = maxEval;
            return maxEval;
            //minimizer is to move
        }else {
            double minEval = Board.LOWEST_NUMBER;
            for(int i = 0; i < tree.children.size()-1; i++){
                double eval = miniMax(tree.children.get(i));
                minEval = ((minEval < eval) ? minEval : eval);
            }
            tree.data.score = minEval;
            return minEval;
        }
    }

    //add a score based on the heuristic and the minimax algorithm to
    //each board in the tree
//    public static TreeNode<Board> addScoreToTree(TreeNode<Board> tree){
//        List<TreeNode<Board>> children = tree.children;
//        //children nodes dont have a score
//        if(children.get(1).data.score == Board.NO_SCORE){
//            //check if next level if leaf
//            if(children.get(1).isLeaf()){
//                children.forEach(child -> {
//                    double score = Heuristic.determineScore(child.data, child.data.lastPlayerColor);
//                    child.data.score = score;
//                });
//            }else {
//                children.forEach(child -> addScoreToTree(child));
//            }
//
//        }else {
//            AlphaBetaPruning.addScoreAndSetBordersToParentNode(tree, children);
//        }
//        return tree;
//    }


}
