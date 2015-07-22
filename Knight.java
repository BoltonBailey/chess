import java.util.*;

public class Knight extends ChessPiece {


    public Knight(ChessBoard b, int s) {
        super(b, s);
    }
    
    
    public HashSet<ChessSquare> attacking() {
        
        HashSet<ChessSquare> knightAttacks = new HashSet<ChessSquare>();

        jumpMoves(knightAttacks, 1, 2);
        jumpMoves(knightAttacks, 2, 1);
        jumpMoves(knightAttacks, -1, 2);
        jumpMoves(knightAttacks, -2, 1);
        jumpMoves(knightAttacks, 1, -2);
        jumpMoves(knightAttacks, 2, -1);
        jumpMoves(knightAttacks, -1, -2);
        jumpMoves(knightAttacks, -2, -1);
        
        return knightAttacks;
    }
    
    
    
    public HashSet<ChessMove> legalMoves() {
        
        return standardMoves();
    }
    
    public String letter() {
        if (side == WHITE) {
            return "N";
        }
        else {
            return "n";
        }
    }
    
}