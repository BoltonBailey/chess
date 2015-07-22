

public class ChessMove {
    
    // Piece being moved (King for castling)
    public ChessPiece piece;
    // Square that piece moves to
    public ChessSquare square;
    // Notes ("R", "N", "B", "Q") for promotion,
    // "ep" for en passant
    // "O-O" or "O-O-O" for castling.
    // "d" for pawn double move
    public String special;
    
    public ChessMove(ChessPiece p, ChessSquare s, String sp) {
        piece = p;
        square = s;
        special = sp;
    }
    
    public ChessMove(ChessPiece p, ChessSquare s) {
        piece = p;
        square = s;
        special = "";
    }
    
    // finds same legal chess move on different, but identical board.
    public ChessMove(ChessMove move, ChessBoard newBoard) {
        int startRank = move.piece.getSquare().rank;
        int startFile = move.piece.getSquare().file;
        int destRank = move.square.rank;
        int destFile = move.square.file;
        
        piece = newBoard.getPiece(startRank, startFile);
        square = newBoard.getSquare(destRank, destFile);
        special = move.special;
    }
        
        
    public String toString() {
        return piece.letter() + " " + piece.getSquare() + " to " + square + " " + special;
    }
}