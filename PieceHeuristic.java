import java.util.*;

public class PieceHeuristic extends ChessHeuristic {
   
    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    //todo memoize copying?
    public PieceHeuristic() {
        super();
    }
    
    public double heuristicEvaluate(ChessBoard board) {

        double wtotal = 0.0;
        double btotal = 0.0;

        for (ChessPiece piece : board.pieceMap.keySet()) {
            double val;
            if (piece instanceof Queen) {
                val = 9.0;
            }
            else if (piece instanceof Rook) {
                val = 5.0;
            }
            else if (piece instanceof Knight) {
                val = 3.0;
            }
            else if (piece instanceof Bishop) {
                val = 3.0;
            }
            else if (piece instanceof Pawn) {
                val = 1.0;
            }
            else {
                val = 1.0;
            }
            if (piece.side == WHITE) {
                wtotal += val;
            }
            else {
                btotal += val;
            }
        }
        return (wtotal - btotal);
    }
}