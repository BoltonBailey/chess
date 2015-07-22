import java.io.*;
import java.util.*;

public class GoodPlayer extends ChessPlayer {

    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public static final int PLY = 2;
    
    public ChessHeuristic heuristic;
    
    public GoodPlayer(ChessHeuristic h) {
        heuristic = h;
    }

    public ChessMove getMove(ChessBoard board) {
        
        heuristic.moveDepth = board.moveCount + PLY;
        heuristic.memoMap.clear();
        
        double bestAdvantage = null;
        ChessMove bestMove = null;
        
        for (ChessMove move : board.legalMoves()) {

            // System.out.println("Examining move");
            
            ChessBoard tempBoard = new ChessBoard(board);
            tempBoard.makeMove(new ChessMove(move, tempBoard));
            double evaluation = heuristic.depthEvaluate(tempBoard);
        
            if (bestMove == null) {
                bestMove = move;
                bestAdvantage = evaluation;
                continue;
            }
            if (board.onTurn == ChessBoard.WHITE) {
                if (evaluation.compareTo(bestAdvantage) > 0) {
                    bestMove = move;
                    bestAdvantage = evaluation;
                }
            }
            else {
                 if (evaluation.compareTo(bestAdvantage) < 0) {
                    bestMove = move;
                    bestAdvantage = evaluation;
                }
            }
        }
        return bestMove;
    }
}