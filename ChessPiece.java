import java.util.*;

public abstract class ChessPiece {

    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    public final ChessBoard board;
    public final int side;
    
    public ChessPiece(ChessBoard b, int s) {
        board = b;
        side = s;
    }
    
    
    // Returns BLACK or WHITE
    public int getSide() {
        return side;
    }
    
    public ChessSquare getSquare() {
        return board.getSquare(this);
    }
    
    /**
     * Set of squares which a piece could capture on, if an opposing piece were placed there. Includes squares which might contain pieces already.
     */
    public abstract HashSet<ChessSquare> attacking();
    
    
    // Set of chess move
    public abstract HashSet<ChessMove> legalMoves();
    
    // letter form of piece
    public abstract String letter();
    
    /**
     * Adds squares that a line-type move would attack to the set
     */
    public void lineMoves(HashSet<ChessSquare> set, int x, int y) {
        
        int pRank = getSquare().rank;
        int pFile = getSquare().file;
        
        while (true) {
        
            pRank += x;
            pFile += y;
            
            if (pRank > 7 || pRank < 0 || pFile > 7 || pFile < 0) {
                return;
            }
            else if (board.getSquare(pRank, pFile).isOccupied()) {
                set.add(board.getSquare(pRank, pFile));
                return;
            }
            else {
                set.add(board.getSquare(pRank, pFile));
            }
        }
        
    }

    /**
     * Adds squares that a jump-type move would attack to the list
     */
    public void jumpMoves(HashSet<ChessSquare> set, int x, int y) {
        
        int pRank = getSquare().rank;
        int pFile = getSquare().file;
    
        pRank += x;
        pFile += y;
            
        if (pRank < 8 && pRank >= 0 && pFile < 8 && pFile >= 0) {
            set.add(board.getSquare(pRank, pFile));
        }
    }
    
    
    
    /**
     * Normal moves to squares attacked by the piece. For R B Q N
     * this is legalMoves
     */
    public HashSet<ChessMove> standardMoves() {
        
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        
        ChessSquare square = getSquare();
        
        // Loop through all attacked squares
        for (ChessSquare destSquare : attacking()) {
            
            ChessPiece pieceFound = destSquare.getPiece();
            
            if (pieceFound == null || pieceFound.getSide() != side) {
                // Move piece to the square, dissociating other pieces
                board.place(destSquare, this);
                // Check if this move leads to check
                if (!board.inCheck(side)) {
                    // If move is legal, add to list
                    moves.add(new ChessMove(this, destSquare, ""));
                }
                // Return board maps to original state
                board.place(square, this);
                if (pieceFound != null) {
                    board.place(destSquare, pieceFound);
                }
            }
        }
        
        return moves;
    }
}