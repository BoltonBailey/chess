import java.util.*;

public class Rook extends ChessPiece {

    public Rook(ChessBoard b, int s) {
        super(b, s);
    }
    
    
    public HashSet<ChessSquare> attacking() {
    
        HashSet<ChessSquare> rookAttacks = new HashSet<ChessSquare>();
        
        lineMoves(rookAttacks, 1, 0);
        lineMoves(rookAttacks, 0, 1);
        lineMoves(rookAttacks, -1, 0);
        lineMoves(rookAttacks, 0, -1);
        
        return rookAttacks;
    }
    
    
    public HashSet<ChessMove> legalMoves() {
        return standardMoves();
    }
    
    public String letter() {
        if (side == WHITE) {
            return "R";
        }
        else {
            return "r";
        }
    }
    
}