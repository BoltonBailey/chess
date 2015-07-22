import java.util.*;

public class King extends ChessPiece {

    public King(ChessBoard b, int s) {
        super(b, s);
    }
    
    public HashSet<ChessSquare> attacking() {
    
        HashSet<ChessSquare> kingAttacks = new HashSet<ChessSquare>();

        jumpMoves(kingAttacks, 1, 1);
        jumpMoves(kingAttacks, 1, 0);
        jumpMoves(kingAttacks, 1, -1);
        jumpMoves(kingAttacks, 0, -1);
        jumpMoves(kingAttacks, -1, -1);
        jumpMoves(kingAttacks, -1, 0);
        jumpMoves(kingAttacks, -1, 1);
        jumpMoves(kingAttacks, 0, 1);
        
        return kingAttacks;
    }
    
    public HashSet<ChessMove> legalMoves() {
        
        HashSet<ChessMove> kingMoves = standardMoves();
        // Add castling
        // White
        if (side == WHITE) {
            ChessSquare b = board.getSquare(0, 1);
            ChessSquare c = board.getSquare(0, 2);
            ChessSquare d = board.getSquare(0, 3);
            ChessSquare e = board.getSquare(0, 4);
            ChessSquare f = board.getSquare(0, 5);
            ChessSquare g = board.getSquare(0, 6);

            boolean fEmpty = !f.isOccupied();
            boolean gEmpty = !g.isOccupied();
            
            if (board.whiteKingsideCastle && fEmpty && gEmpty) {
                boolean eChecked = board.inCheck(WHITE);
                board.place(this, f);
                boolean fChecked = board.inCheck(WHITE);
                board.place(this, g);
                boolean gChecked = board.inCheck(WHITE);
                board.place(this, e);
                if (!(eChecked || fChecked || gChecked)) {
                    kingMoves.add(new ChessMove(this, g, "O-O"));
                }
            }
            
            boolean bEmpty = !b.isOccupied();
            boolean cEmpty = !c.isOccupied();
            boolean dEmpty = !d.isOccupied();
            
            if (board.whiteQueensideCastle && bEmpty && cEmpty && dEmpty) {
                boolean eChecked = board.inCheck(WHITE);
                board.place(this, d);
                boolean dChecked = board.inCheck(WHITE);
                board.place(this, c);
                boolean cChecked = board.inCheck(WHITE);
                board.place(this, e);
                if (!(eChecked || dChecked || cChecked)) {
                    kingMoves.add(new ChessMove(this, c, "O-O-O"));
                }
            }
        }
        else {
            ChessSquare b = board.getSquare(7, 1);
            ChessSquare c = board.getSquare(7, 2);
            ChessSquare d = board.getSquare(7, 3);
            ChessSquare e = board.getSquare(7, 4);
            ChessSquare f = board.getSquare(7, 5);
            ChessSquare g = board.getSquare(7, 6);

            boolean fEmpty = !f.isOccupied();
            boolean gEmpty = !g.isOccupied();
            
            if (board.blackKingsideCastle && fEmpty && gEmpty) {
                boolean eChecked = board.inCheck(BLACK);
                board.place(this, f);
                boolean fChecked = board.inCheck(BLACK);
                board.place(this, g);
                boolean gChecked = board.inCheck(BLACK);
                board.place(this, e);
                if (!(eChecked || fChecked || gChecked)) {
                    kingMoves.add(new ChessMove(this, g, "O-O"));
                }
            }
            
            boolean bEmpty = !b.isOccupied();
            boolean cEmpty = !c.isOccupied();
            boolean dEmpty = !d.isOccupied();
            
            if (board.blackQueensideCastle && bEmpty && cEmpty && dEmpty) {
                boolean eChecked = board.inCheck(BLACK);
                board.place(this, d);
                boolean dChecked = board.inCheck(BLACK);
                board.place(this, c);
                boolean cChecked = board.inCheck(BLACK);
                board.place(this, e);
                if (!(eChecked || dChecked || cChecked)) {
                    kingMoves.add(new ChessMove(this, c, "O-O-O"));
                }
            }
        }
        
        return kingMoves;
    }
    
    public String letter() {
        if (side == WHITE) {
            return "K";
        }
        else {
            return "k";
        }
    }
    
}