package app.logic.minimax;

import app.logic.Board;
import app.logic.Heuristics.Heuristic;
import app.ui.Controller;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MiniMax {


    //return the column of the best move
    public static int determineBestMove(Board board, int depth, boolean pruned, boolean printTree){
        TreeNode<Board> tree = MiniMax.contructTree(board, depth);
        MiniMax.miniMax(tree, pruned);
        Board bestMove = findBestMove(tree);
        if(printTree){
            final String fileName = "src/main/resources/graph/determineBestMove.";
            String treeString = TreeNode.treeToString(tree,true);
            try {
                MutableGraph g = Parser.read(treeString);
                Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(fileName+"png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bestMove.getLastMove().column;
    }

    private static Board findBestMove(TreeNode<Board> tree){
        Board bestMove = null;
        for(int i = 0; i < tree.children.size();i++){
            Board currentMove = tree.children.get(i).data;
            if(null == bestMove){
                bestMove = currentMove;
            }else if(bestMove.score < currentMove.score){
                bestMove = currentMove;
            }
        }
        return bestMove;
    }


    //contructs a MiniMax Tree with board as the root node
    //with a specific depth
    //leaves score blank
    protected static TreeNode<Board> contructTree(Board board, int depth){
        if(depth == 0){
            return new TreeNode<Board>(board);
        }
        TreeNode<Board> tree = new TreeNode<>(board);
        ArrayList<Board> nextConstellations = board.generateNextConstellations();
        nextConstellations.forEach(constellation ->
                tree.addChildNode(contructTree(constellation, depth-1)));
        return tree;
    }



    protected static void miniMax(TreeNode<Board> tree, boolean pruned){
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
            board.score = Controller.heuristicThorben.determineScore(board);
            return board.score;
        }
        //maximizer is next to move
        if(Board.MAXIMIZER.equals(tree.data.nextPlayer)){
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
            board.score = Controller.heuristicThorben.determineScore(board);
            return board.score;
        }
        //maximizer is next to move
        if(Board.MAXIMIZER.equals(tree.data.nextPlayer)){
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
