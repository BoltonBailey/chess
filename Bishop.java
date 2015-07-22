import java.util.*;

public class Bishop extends ChessPiece {

    public Bishop(ChessBoard b, int s) {
        super(b, s);
    }
    
    public HashSet<ChessSquare> attacking() {
        
        // Create list of squares that are attacked
        HashSet<ChessSquare> bishopAttacks = new HashSet<ChessSquare>();
        
        // Add line moves along diagonals.
        lineMoves(bishopAttacks, 1, 1);
        lineMoves(bishopAttacks, -1, 1);
        lineMoves(bishopAttacks, -1, -1);
        lineMoves(bishopAttacks, 1, -1);
        
        return bishopAttacks;
    }
    
    public HashSet<ChessMove> legalMoves() {
        return standardMoves();
    }

    public String letter() {
        if (side == WHITE) {
            return "B";
        }
        else {
            return "b";
        }
    }
    
}