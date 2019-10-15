package app.logic.minimax;

import app.logic.Board;
import app.logic.Heuristic;

import java.util.ArrayList;

public class MiniMax {
    //contructs a MiniMax Tree with board as the root node
    //with a specific depth
    //leaves score blank
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

    public static void miniMax(TreeNode<Board> tree, boolean pruned){
        if (pruned) {
            miniMax(tree, Board.LOWEST_NUMBER, Board.HIGHEST_NUMBER);
        } else {
            miniMax(tree);
        }
    };

    //the miniMax algorithm based on: https://www.youtube.com/watch?v=l-hh51ncgDI
    private static double miniMax(TreeNode<Board> tree){
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
                maxEval = max(maxEval,eval);
            }
            tree.data.score = maxEval;
            return maxEval;
            //minimizer is to move
        }else {
            double minEval = Board.HIGHEST_NUMBER;
            for(int i = 0; i < tree.children.size()-1; i++){
                double eval = miniMax(tree.children.get(i));
                minEval = ((minEval < eval) ? minEval : eval);
            }
            tree.data.score = minEval;
            return minEval;
        }
    }

    private static double max(double value1, double value2){
        return ((value1 > value2) ? value1 : value2);
    }

    private static double min(double value1, double value2){
        return ((value1 < value2) ? value1 : value2);
    }

    //the miniMax with alphaBeta Pruning algorithm based on: https://www.youtube.com/watch?v=l-hh51ncgDI
    private static double miniMax(TreeNode<Board> tree, double alpha, double beta){
        if(tree.isLeaf()){
            Board board = tree.data;
            board.score = Heuristic.determineScore(board,board.nextPlayerColor);
            return board.score;
        }
        //maximizer is next to move
        if(Board.MAXIMIZER.equals(tree.data.nextPlayerColor)){
            double maxEval = Board.LOWEST_NUMBER;
            for(int i = 0; i < tree.children.size()-1; i++){
                double eval = miniMax(tree.children.get(i),alpha,beta);
                maxEval = max(maxEval,eval);
                alpha = max(alpha, eval);
                if(beta <= alpha){
                    tree.data.prune();
                    break;
                }
            }
            tree.data.score = maxEval;
            return maxEval;
            //minimizer is to move
        }else {
            double minEval = Board.HIGHEST_NUMBER;
            for(int i = 0; i < tree.children.size()-1; i++){
                double eval = miniMax(tree.children.get(i),alpha,beta);
                minEval = ((minEval < eval) ? minEval : eval);
                beta = min(beta, eval);
                if(beta <= alpha){
                    tree.data.prune();
                    break;
                }
            }
            tree.data.score = minEval;
            return minEval;
        }
    }

}
