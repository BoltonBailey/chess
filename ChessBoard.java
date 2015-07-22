import java.util.*;
import java.lang.*;

public class ChessBoard {
    
    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    // Array of squares, the chessboard spaces
    private final ChessSquare[][] squareArray;
    // Maps containing all the pieces and their squares
    public HashMap<ChessPiece, ChessSquare> pieceMap;
    public HashMap<ChessSquare, ChessPiece> squareMap;
    // Holders for the kings
    public ChessPiece whiteKing;
    public ChessPiece blackKing;
    // Holds which player is about to move
    public int onTurn;
    // Holds file upon which a pawn made a double move, or else -1
    public ChessSquare enPassantSquare;
    // Holds castlings - have the king and rook never moved?
    public boolean whiteKingsideCastle;
    public boolean whiteQueensideCastle;
    public boolean blackKingsideCastle;
    public boolean blackQueensideCastle;
    // Holds half-move clock
    public int halfMoves;
    // Holds Whole move count
    public int moveCount;
    
    
    public ChessBoard(ChessBoard copyBoard) {
        // Array of squares
        this.squareArray = new ChessSquare[8][8];
        
        // Make the new squares and fill squareArray
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                this.squareArray[rank][file] = new ChessSquare(this, rank, file);
            }
        }
        
        // The square-piece association maps
        this.pieceMap = new HashMap<ChessPiece, ChessSquare>();
        this.squareMap = new HashMap<ChessSquare, ChessPiece>();
        
        // Make and place pieces
        for (ChessPiece piece : copyBoard.pieceMap.keySet()) {
            ChessPiece newPiece;
            
            int side = piece.getSide();
            int rank = copyBoard.getSquare(piece).rank;
            int file = copyBoard.getSquare(piece).file;
            ChessSquare newPieceSquare = squareArray[rank][file];
            
            
            if (piece instanceof King) {
                newPiece = new King(this, side);
                if (side == WHITE) {
                    whiteKing = newPiece;
                }
                else {
                    blackKing = newPiece;
                }
            }
            else if (piece instanceof Queen) {
                newPiece = new Queen(this, side);
            }
            else if (piece instanceof Bishop) {
                newPiece = new Bishop(this, side);
            }
            else if (piece instanceof Knight) {
                newPiece = new Knight(this, side);
            }
            else if (piece instanceof Rook) {
                newPiece = new Rook(this, side);
            }
            else {
                newPiece = new Pawn(this, side);
            }
            place(newPiece, newPieceSquare);
        }
        
        if (copyBoard.enPassantSquare != null) {
            int epRank = copyBoard.enPassantSquare.rank;
            int epFile = copyBoard.enPassantSquare.file;
            this.enPassantSquare = getSquare(epRank, epFile);
        }
        else {
            this.enPassantSquare = null;
        }
        
        onTurn = copyBoard.onTurn;
        whiteKingsideCastle = copyBoard.whiteKingsideCastle;
        whiteQueensideCastle = copyBoard.whiteQueensideCastle;
        blackKingsideCastle = copyBoard.blackKingsideCastle;
        blackQueensideCastle = copyBoard.blackQueensideCastle;
        halfMoves = copyBoard.halfMoves;
        moveCount = copyBoard.moveCount;
    }
    
    /**
     * Creates ChessBoard in the state of the beginning of the game.
     */
    public ChessBoard() {
    
        // Array of squares
        squareArray = new ChessSquare[8][8];
        
        // Make the new squares and fill squareArray
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                squareArray[rank][file] = new ChessSquare(this, rank, file);
            }
        }
        
        // The square-piece association maps
        pieceMap = new HashMap<ChessPiece, ChessSquare>();
        squareMap = new HashMap<ChessSquare, ChessPiece>();
        
        // Make and place Pieces
        // White
        place(new Rook(this, WHITE), getSquare(0, 0));
        place(new Knight(this, WHITE), getSquare(0, 1));
        place(new Bishop(this, WHITE), getSquare(0, 2));
        place(new Queen(this, WHITE), getSquare(0, 3));
        place(new King(this, WHITE), getSquare(0, 4));
        place(new Bishop(this, WHITE), getSquare(0, 5));
        place(new Knight(this, WHITE), getSquare(0, 6));
        place(new Rook(this, WHITE), getSquare(0, 7));
        for (int i = 0; i < 8; i++) {
            place(new Pawn(this, WHITE), getSquare(1, i));
        }
        
        // Black
        place(new Rook(this, BLACK), getSquare(7, 0));
        place(new Knight(this, BLACK), getSquare(7, 1));
        place(new Bishop(this, BLACK), getSquare(7, 2));
        place(new Queen(this, BLACK), getSquare(7, 3));
        place(new King(this, BLACK), getSquare(7, 4));
        place(new Bishop(this, BLACK), getSquare(7, 5));
        place(new Knight(this, BLACK), getSquare(7, 6));
        place(new Rook(this, BLACK), getSquare(7, 7));
        for (int i = 0; i < 8; i++) {
            place(new Pawn(this, BLACK), getSquare(6, i));
        }
        
        // Find the kings
        whiteKing = getPiece(getSquare(0, 4));
        blackKing = getPiece(getSquare(7, 4));
        
        // White goes first
        onTurn = WHITE;
        // No enPassant file
        enPassantSquare = null;
        // Castling possible
        whiteKingsideCastle = true;
        whiteQueensideCastle = true;
        blackKingsideCastle = true;
        blackQueensideCastle = true;
        // Zero half-move clock
        halfMoves = 0;
        // Whole move clock to 1
        moveCount = 1;
        
    }
    
    /**
     * Returns reference to the square at rank, file on the chessboard
     */
    public ChessSquare getSquare(int rank, int file) {
        return squareArray[rank][file];
    }
    
    
    
    // Returns square that a piece is on
    public ChessSquare getSquare(ChessPiece piece) {
        return pieceMap.get(piece);
    }
    
    
    // Returns piece on square
    public ChessPiece getPiece(ChessSquare square) {
    
        if (squareMap.containsKey(square)) {
            return squareMap.get(square);
        }
        else {
            return null;
        }
    }
    
    
    // Returns piece on square
    public ChessPiece getPiece(int rank, int file) {
    
        return getPiece(getSquare(rank, file));
    }
    
    
    // Returns king
    public ChessPiece getKing(int side) {
        if (side == WHITE) {
            return whiteKing;
        }
        else {
            return blackKing;
        }
    }


    // Disassociates square and piece if there is association
    public void clearSquare(ChessSquare square) {
        if (squareMap.containsKey(square)) {
            pieceMap.remove(squareMap.get(square));
            squareMap.remove(square);
        }
    }
    
    
    // Disassociates square and piece if there is association
    public void removePiece(ChessPiece piece) {
        if (pieceMap.containsKey(piece)) {
            clearSquare(pieceMap.get(piece));
        }
    }
    
    
    // Associates a square and a piece, removing any other associations
    public void place(ChessSquare square, ChessPiece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("null piece placed");
        }
        if (square == null) {
            throw new IllegalArgumentException("null square placed on");
        }
        clearSquare(square);
        removePiece(piece);
        pieceMap.put(piece, square);
        squareMap.put(square, piece);
    }


    // Associates a square and a piece, removing any other associations
    public void place(ChessPiece piece, ChessSquare square) {
        place(square, piece);
    }
    
    
    // Changes the board state according to a ChessMove
    public void makeMove(ChessMove move) {
        // Unpack the move.
        ChessSquare destSquare = move.square;
        ChessPiece piece = move.piece;
        String special = move.special;
        
        // Increase move clocks
        halfMoves++;
        moveCount++;
        // Zero clock for pawn move
        if (piece instanceof Pawn) {
            halfMoves = 0;
        }
        // Zero clock for capture
        if (destSquare.isOccupied()) {
            halfMoves = 0;
        }
        
        //Move piece
        place(piece, destSquare);
        
        int rank = destSquare.rank;
        int file = destSquare.file;
        
        // Specify enPassant square
        if (special == "d") {
            if (rank == 3) {
                enPassantSquare = getSquare(2, file);
            }
            else { // rank == 4
                enPassantSquare = getSquare(5, file);
            }
        }
        else {
            enPassantSquare = null;
        }
        
        // Remove pawn for en passant capture
        if (special == "ep") {
            if (rank == 2) {
                clearSquare(getSquare(3, file));
            }
            else { // rank == 5
                clearSquare(getSquare(4, file));
            }
        }
        
        
        // Move rook for castle
        if (special == "O-O") {
            place(getPiece(rank, 7), getSquare(rank, 5));
        }

        if (special == "O-O-O") {
            place(getPiece(rank, 0), getSquare(rank, 3));
        }
        
        // Place new piece for promotion
        
        if (special == "R") {
            place(destSquare, new Rook(this, onTurn));
        }
        else if (special == "N") {
            place(destSquare, new Knight(this, onTurn));
        }
        else if (special == "B") {
            place(destSquare, new Bishop(this, onTurn));
        }
        else if (special == "Q") {
            place(destSquare, new Queen(this, onTurn));
        }
        
        // Castling still available?
        if (!getSquare(0,4).isOccupied()) {
            whiteKingsideCastle = false;
            whiteQueensideCastle = false;
        }
        if (!getSquare(7,4).isOccupied()) {
            blackKingsideCastle = false;
            blackQueensideCastle = false;
        }
        if (!getSquare(0,0).isOccupied() || getPiece(0,0).side == BLACK) {
            whiteQueensideCastle = false;
        }
        if (!getSquare(0,7).isOccupied() || getPiece(0,7).side == BLACK) {
            whiteKingsideCastle = false;
        }
        if (!getSquare(7,0).isOccupied() || getPiece(7,0).side == WHITE) {
            blackQueensideCastle = false;
        }
        if (!getSquare(7,7).isOccupied() || getPiece(7,7).side == WHITE) {
            blackKingsideCastle = false;
        }
        // Change turn
        if (onTurn == WHITE) {
            onTurn = BLACK;
        }
        else {
            onTurn = WHITE;
        }
        
    }
    
    
    // Returns list of legal moves
    public HashSet<ChessMove> legalMoves() {
        
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        int numPieces = pieceMap.keySet().size();
        ChessPiece[] pieces;
        pieces = pieceMap.keySet().toArray(new ChessPiece[numPieces]);
        
        for (ChessPiece piece : pieces) {
            if (piece.getSide() == onTurn) {
                for (ChessMove move : piece.legalMoves()) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }
    
    
    //Asks if person on move is checkmated
    public boolean isCheckmated() {
        return legalMoves().isEmpty() && inCheck(onTurn);
    }
    
    //Asks is person on move is stalemated
    public boolean isStalemated() {
        return legalMoves().isEmpty() && !inCheck(onTurn);
    }
    
    // Asks if at least one side has mating material.
    public boolean isInsufficient() {
        if (pieceMap.size() == 2) {
            return true;
        }
        if (pieceMap.size() == 3) {
            Set<ChessPiece> pieces = pieceMap.keySet();
            for (ChessPiece piece : pieces) {
                if (piece instanceof Bishop) {
                    return true;
                }
                if (piece instanceof Knight) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isFiftyMove() {
        return halfMoves > 100;
    }
    
    public boolean isDraw() {
        return isStalemated() || isInsufficient() || isFiftyMove();
    }
    
    /**
     * returns whether side is in check.
     */
    public boolean inCheck(int side) {
        
        ChessSquare kingSquare = pieceMap.get(getKing(side));
        Set<ChessPiece> pieces = pieceMap.keySet();
        for (ChessPiece piece : pieces) {
            if (piece.getSide() != side) {
                if (piece.attacking().contains(kingSquare)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    // Prints out the chess board
    public void printBoard() {
        
        if (onTurn == WHITE) {
            System.out.println("White to move: Move number " + moveCount);
        }
        else {
            System.out.println("Black to move: Move number " + moveCount);
        }
        
        if (whiteKingsideCastle) {
            System.out.println("White can kingside castle.");
        }
        if (whiteQueensideCastle) {
            System.out.println("White can queenside castle.");
        }
        if (blackKingsideCastle) {
            System.out.println("Black can kingside castle.");
        }
        if (blackQueensideCastle) {
            System.out.println("Black can queenside castle.");
        }
        System.out.println("Half-moves since capture/pawn: " + halfMoves);
        
        
        for (int rank = 7; rank >= 0; rank--) {
            System.out.println("  +---+---+---+---+---+---+---+---+");
            String rankString = rank + " ";
            for (int file = 0; file < 8; file++) {
                rankString += "|";
                rankString += " ";
                if (getSquare(rank, file).isOccupied()) {
                    rankString += getPiece(getSquare(rank, file)).letter();
                }
                else if (getSquare(rank, file) == enPassantSquare) {
                    rankString += "*";
                }
                else {
                    rankString += " ";
                }
                rankString += " ";
            }
            rankString += "|";
            System.out.println(rankString);
        }
        System.out.println("  +---+---+---+---+---+---+---+---+");
        System.out.println("    0   1   2   3   4   5   6   7 ");
        System.out.println();
    }
    
    
    
    // Not exactly FEN
    // Equality in strings depends on  object identity, not contents of string
    public String toFEN() {
        String FENString = "";
        
        for (int rank = 7; rank >= 0; rank--) {
            int emptySquareCount = 0;
            for (int file = 0; file < 8; file++) {
                if (getSquare(rank, file).isOccupied()) {
                    if (emptySquareCount > 0) {
                        FENString += emptySquareCount;
                    }
                    emptySquareCount = 0;
                    FENString += getPiece(rank, file).letter();
                }
                else {
                    emptySquareCount++;
                }
            }
            if (rank > 0) {
                FENString += "/";
            }
        }
        
        FENString += " ";
        
        if (onTurn == WHITE) {
            FENString += "w ";
        }
        else {
            FENString += "b ";
        }
        if (whiteKingsideCastle) {
            FENString += "K";
        }
        if (whiteQueensideCastle) {
            FENString += "Q";
        }
        if (blackKingsideCastle) {
            FENString += "k";
        }
        if (blackQueensideCastle) {
            FENString += "q";
        }
        FENString += " ";
        if (enPassantSquare != null) {
            FENString += enPassantSquare;
        }
        FENString += " " + halfMoves + " " + moveCount;
        
        return FENString;
        
            
    }
    
    
    //
    public boolean equals(Object obj) {
        if (!(obj instanceof ChessBoard)) {
            return false;
        }
        ChessBoard otherBoard = (ChessBoard) obj;
        
        // Loop through squares
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                // Ask if the square is empty
                if (getSquare(rank, file).isEmpty()) {
                    // If it is and the corresponding isn't, not equal
                    if (!otherBoard.getSquare(rank, file).isEmpty()) {
                        return false;
                    }
                    // If both are, then no contradiction - continue,
                    else {
                        continue;
                    }
                }
                // Now we have situations where square is nonempty
                // If corresponding square empty, not equal
                if (otherBoard.getSquare(rank, file).isEmpty()) {
                    return false;
                }
                // Now both squares are full, so get the pieces
                ChessPiece piece = getPiece(rank, file);
                ChessPiece otherPiece = otherBoard.getPiece(rank, file);
                // If they are different sides, not equal
                if (piece.side != otherPiece.side) {
                    return false;
                }
                //Compare piece type
                if (piece instanceof Pawn) {
                    if (!(otherPiece instanceof Pawn)) {
                        return false;
                    }
                }
                if (piece instanceof Rook) {
                    if (!(otherPiece instanceof Rook)) {
                        return false;
                    }
                }
                if (piece instanceof Knight) {
                    if (!(otherPiece instanceof Knight)) {
                        return false;
                    }
                }
                if (piece instanceof Bishop) {
                    if (!(otherPiece instanceof Bishop)) {
                        return false;
                    }
                }
                if (piece instanceof Queen) {
                    if (!(otherPiece instanceof Queen)) {
                        return false;
                    }
                }
                if (piece instanceof King) {
                    if (!(otherPiece instanceof King)) {
                        return false;
                    }
                }
            }
        }
        //Done looping through squares
        if (onTurn != otherBoard.onTurn) {
            return false;
        }
        if (whiteKingsideCastle != otherBoard.whiteKingsideCastle) {
            return false;
        }
        if (whiteQueensideCastle != otherBoard.whiteQueensideCastle) {
            return false;
        }
        if (blackKingsideCastle != otherBoard.blackKingsideCastle) {
            return false;
        }
        if (blackQueensideCastle != otherBoard.blackQueensideCastle) {
            return false;
        }
        if (halfMoves != otherBoard.halfMoves) {
            return false;
        }
        if (moveCount != otherBoard.moveCount) {
            return false;
        }
        // Examine enpassant
        if (enPassantSquare == null && otherBoard.enPassantSquare == null) {
            return true;
        }
        if (enPassantSquare != null && otherBoard.enPassantSquare == null) {
            return false;
        }
        if (enPassantSquare == null && otherBoard.enPassantSquare != null) {
            return false;
        }
        //Both have enpassant
        if (enPassantSquare.file != otherBoard.enPassantSquare.file) {
            return false;
        }
        if (enPassantSquare.rank != otherBoard.enPassantSquare.rank) {
            return false;
        }
        return true;
    }
    
    public int hashCode() {
    
        int result = 233;
        
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file ++) {
                ChessSquare square = getSquare(rank, file);
                
                if (square.isEmpty()) {
                    result += 0;
                    result *= 37;
                    continue;
                }
                ChessPiece piece = getPiece(square);
                if (piece instanceof Pawn) {
                    result += 1;
                }
                else if (piece instanceof Rook) {
                    result += 2;
                }
                else if (piece instanceof Knight) {
                    result += 3;
                }
                else if (piece instanceof Bishop) {
                    result += 4;
                }
                else if (piece instanceof Queen) {
                    result += 5;
                }
                else {
                    result += 6;
                }
                if (piece.side == WHITE) {
                    result += 7;
                }
                result *= 37;
            }
        }
        
        if (onTurn == WHITE) {
            result += 1;
        }
        if (whiteKingsideCastle) {
            result += 2;
        }
        if (whiteQueensideCastle) {
            result += 4;
        }
        if (blackKingsideCastle) {
            result += 8;
        }
        if (blackQueensideCastle) {
            result += 16;
        }
        result *= 37;
        
        if (enPassantSquare != null) {
            result += enPassantSquare.file;
        }
        
        result *= 37;
        
        result += moveCount;
        
        result *= 37;
        
        result += halfMoves;
        
        return result;
    }


}
    
    