

public class ReducedChessBoard {

    public int[][] rBoard;
    public int onTurn;
    public int halfMoves;
    public int moveCount;
    public int hash;
    
    public ReducedChessBoard(ChessBoard copyBoard) {
        // Array of squares
        rBoard = new int[8][8];
        
        
        // Make the new squares and fill squareArray
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                rBoard[rank][file] = 0;
            }
        }
        
        // Make and place pieces
        for (ChessPiece piece : copyBoard.pieceMap.keySet()) {
            
            int side = piece.getSide();
            int rank = copyBoard.getSquare(piece).rank;
            int file = copyBoard.getSquare(piece).file;
            int pieceType;
            
            if (piece instanceof King) {
                pieceType = 6;
            }
            else if (piece instanceof Queen) {
                pieceType = 5;
            }
            else if (piece instanceof Bishop) {
                pieceType = 4;
            }
            else if (piece instanceof Knight) {
                pieceType = 3;
            }
            else if (piece instanceof Rook) {
                pieceType = 2;
            }
            else {
                pieceType = 1;
            }
            
            rBoard[rank][file] = pieceType;
            if (side == ChessBoard.BLACK) {
                rBoard[rank][file] = -pieceType;
            }
        }
        
        if (copyBoard.enPassantSquare != null) {
            int epRank = copyBoard.enPassantSquare.rank;
            int epFile = copyBoard.enPassantSquare.file;
            rBoard[epRank][epFile] = 7;
        }

        onTurn = copyBoard.onTurn;

        halfMoves = copyBoard.halfMoves;
        moveCount = copyBoard.moveCount;
        
        if (copyBoard.blackQueensideCastle) {
            rBoard[7][0] = 8;
        }
        if (copyBoard.blackKingsideCastle) {
            rBoard[7][7] = 8;
        }
        if (copyBoard.whiteQueensideCastle) {
            rBoard[0][0] = 8;
        }
        if (copyBoard.whiteKingsideCastle) {
            rBoard[0][7] = 8;
        }
        
        hash = copyBoard.hashCode();
    }
    
    public int hashCode() {
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof ReducedChessBoard)) {
            return false;
        }
        ReducedChessBoard otherBoard = (ReducedChessBoard) obj;
        
        // Loop through squares
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                // Ask if the square is empty
                if (rBoard[rank][file] != otherBoard.rBoard[rank][file]) {
                    return false;
                }
            }
        }
        //Done looping through squares
        
        if (onTurn != otherBoard.onTurn) {
            return false;
        }
        if (halfMoves != otherBoard.halfMoves) {
            return false;
        }
        if (moveCount != otherBoard.moveCount) {
            return false;
        }
        return true;
    }
}


