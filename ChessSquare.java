

public class ChessSquare {

    public final ChessBoard board;
    public final int rank;
    public final int file;
    
    public ChessSquare(ChessBoard b, int r, int f) {
        board = b;
        rank = r;
        file = f;
    }

    public ChessPiece getPiece() {
        return board.getPiece(this);
    }
    
    public boolean isOccupied() {
        return getPiece() != null;
    }
    
    public boolean isEmpty() {
        return !isOccupied();
    }
    
    public String toString() {
        return "(" + rank + ", " + file + ")";
    }
    
}