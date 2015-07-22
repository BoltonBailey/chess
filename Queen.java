import java.util.*;

public class Queen extends ChessPiece {


    public Queen(ChessBoard b, int s) {
        super(b, s);
    }
    
    
    public HashSet<ChessSquare> attacking() {
    
        HashSet<ChessSquare> queenAttacks = new HashSet<ChessSquare>();
        
        lineMoves(queenAttacks, 1, 1);
        lineMoves(queenAttacks, -1, 1);
        lineMoves(queenAttacks, -1, -1);
        lineMoves(queenAttacks, 1, -1);
        lineMoves(queenAttacks, 0, 1);
        lineMoves(queenAttacks, -1, 0);
        lineMoves(queenAttacks, 0, -1);
        lineMoves(queenAttacks, 1, 0);
        
        return queenAttacks;
    }
    
    public HashSet<ChessMove> legalMoves() {
        return standardMoves();
    }
    
    public String letter() {
        if (side == WHITE) {
            return "Q";
        }
        else {
            return "q";
        }
    }
    
}