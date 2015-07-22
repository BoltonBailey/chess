import java.util.*;

public class ChessBoard {
    
    // Board is a multidimensional array of ints
    // First index is rank, second is file
    /* 
    0 = empty 
    1 = pawn, 
    2 = rook, 
    3 = knight, 
    4 = bishop, 
    5 = queen, 
    6 = king
    negative = black
    */
    int[][] boardState;
    int onMove;               // +1 for white, -1 for black
    boolean whiteKingCastle;
    boolean whiteQueenCastle;
    boolean blackKingCastle;
    boolean blackQueenCastle;
    int enPassantFile;
    
    /**
     * Creates a chessboard, in the the position at the beginning of the game.
     */
    public ChessBoard() {
        // Create Board
        int[][] boardState = new int[8][8];
        // Place pieces on Board
    
        // Specify castling
        onMove = 1;
        whiteKingCastle = true;
        whiteQueenCastle = true;
        blackKingCastle = true;
        blackQueenCastle = true;
        // Specify en Passant
        enPassantFile = -1;
    }
    
    
    /**
     * Returns a list of all the legal moves for the player to move to make in 
     * the given position.
     */
    public List<ChessMove> legalMoves() {
        List<ChessMove> legalList = new List<ChessMove>();
        // Loop through squares to find pieces
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                int piece = boardState[rank][file];
                // If square empty or wrong color, continue
                if (piece * onMove <= 0) {
                    continue;
                }
                // We now deal with having a piece of correct color
                // piece = +-1 = pawn
                if (abs(piece) == 1) {
                    legalList.addAll(pawnMoves(rank, file));
                }
                // piece = +-2 = rook
                else if (abs(piece) == 2) {
                    legalList.addAll(lineMoves(rank, file, 1, 0));
                    legalList.addAll(lineMoves(rank, file, 0, 1));
                    legalList.addAll(lineMoves(rank, file, -1, 0));
                    legalList.addAll(lineMoves(rank, file, 0, -1));
                }
                // piece = +-3 = knight
                else if (abs(piece) == 3) {
                    legalList.addAll(knightMoves(rank, file));
                }
                // piece = +-4 = bishop
                else if (abs(piece) == 4) {
                    legalList.addAll(lineMoves(rank, file, 1, 1));
                    legalList.addAll(lineMoves(rank, file, -1, 1));
                    legalList.addAll(lineMoves(rank, file, 1, -1));
                    legalList.addAll(lineMoves(rank, file, -1, -1));
                }
                // piece = +-5 = queen
                else if (abs(piece) == 5) {
                    legalList.addAll(lineMoves(rank, file, 1, 0));
                    legalList.addAll(lineMoves(rank, file, 0, 1));
                    legalList.addAll(lineMoves(rank, file, -1, 0));
                    legalList.addAll(lineMoves(rank, file, 0, -1));
                    legalList.addAll(lineMoves(rank, file, 1, 1));
                    legalList.addAll(lineMoves(rank, file, -1, 1));
                    legalList.addAll(lineMoves(rank, file, 1, -1));
                    legalList.addAll(lineMoves(rank, file, -1, -1));
                }
                // piece = +-6 = king
                else if (abs(piece) == 6) {
                    // Add normal move to legalList
                    legalList.addAll(kingMoves(rank, file));
                    // Add castling to legalList
                    if (onMove == 1)
                        if (!inCheck(1) && )
                    
                }
            }
            // Remove checking moves
        }
    
    
    /**
     * Takes a rank and file where an on-move pawn is located and returns list of moves that pawn can make, not considering check.
     */
    private List<ChessMove> pawnMoves(int rank, int file) {
        List<ChessMove> pawnList; // Not accounting for promotion
        piece = onMove;
        // Add single move to pawnList
        // For white
        if (piece == 1 &&
            boardState[rank + 1][file] == 0) {
            // Normal single move
            pawnList.add(new ChessMove(rank, file, rank + 1, file, piece));
        }
        // For black
        if (piece == -1 &&
            boardState[rank - 1][file] == 0) {
            // Normal single move
            pawnList.add(new ChessMove(rank, file, rank - 1, file, piece));
        }
        
        // Add capture to pawnList
        // For white
        if (piece == 1 &&
            file > 0 &&
            boardState[rank + 1][file - 1] < 0) {
            // Normal capture
            pawnList.add(new ChessMove(rank, file, rank + 1, file - 1, piece));
        }
        if (piece == 1 &&
            file < 7 &&
            boardState[rank + 1][file + 1] < 0) {
            // Normal capture
            pawnList.add(new ChessMove(rank, file, rank + 1, file + 1, piece));

        }
        // For black
        if (piece == -1 &&
            file > 0 &&
            boardState[rank - 1][file - 1] < 0) {
            // Normal capture
            pawnList.add(new ChessMove(rank, file, rank - 1, file - 1, piece));
        }
        if (piece == 1 &&
            file < 7 &&
            boardState[rank - 1][file + 1] < 0) {
            // Normal capture
            pawnList.add(new ChessMove(rank, file, rank - 1, file + 1, piece));
        }
                    
        // Add double move to pawnList
        // For white
        if (piece == 1 &&
            rank == 1 &&
            boardState[rank + 1][file] == 0 &&
            boardState[rank + 2][file] == 0) {
            pawnList.add(new ChessMove(rank, file, rank + 2, file, piece));
        }
        // For black
        if (piece == -1 &&
            rank == 6 &&
            boardState[rank - 1][file] == 0 &&
            boardState[rank - 2][file] == 0) {
            pawnList.add(new ChessMove(rank, file, rank - 2, file, piece));
        }
        
        // Add en passant capture to pawnList
        // For white
        if (piece == 1 &&
            rank == 4 &&
            enPassantFile != -1 &&
            abs(file - enPassantFile) == 1 ) {
            pawnList.add(new ChessMove(rank, file, rank + 1, enPassantFile, piece));
        }
        // For black
        if (piece == -1 &&
            rank == 3 &&
            enPassantFile != -1 &&
            abs(file - enPassantFile) == 1 ) {
            pawnList.add(new ChessMove(rank, file, rank - 1, enPassantFile, piece));
        }
        
        List<ChessMove> completePawnList = new List<ChessMove>;
        for (ChessMove move : pawnList) {
            if (0 < move.finalRank < 7) {
                completePawnList.add(move);
            }
            else {
                for (int pieceType = 2; pieceType < 6; pieceType++) {
                    ChessMove promotionMove = new ChessMove(move.startRank, move.startFile, move.finalRank, move.finalFile, onMove * pieceType);
                    completePawnList.add(promotionMove);
                }
            }
        }
        return completePawnList;
    }
    
    
    /**
     * Takes a rank and file where an on-move piece is located and returns list of moves that piece can make in the given direction, not considering check.
     */
    private List<ChessMove> lineMoves(int rank, int file, int x, int y) {
        List<ChessMove> lineList = new List<ChessMove>;
        
        int piece = boardState[rank][file];
        
        int pRank = rank;
        int pFile = file;
        
        while (true) {
            pRank += x;
            pFile += y;
            if (pRank > 7 || pRank < 0 || pFile > 7 || pFile < 0) {
                break
            }
            else if (boardState[pRank][pFile] != 0) {
                if (boardState[pRank][pFile] * onMove < 0) {
                    lineList.add(new ChessMove(rank, file, pRank, pFile, piece));
                }
                break;
            }
            else {
                lineList.add(new ChessMove(rank, file, pRank, pFile, piece));
            }
        }
        return lineList;
    }
    
    
    /**
     * Takes a rank and file where an on-move knight is located and returns list of moves that knight can make, not considering check.
     */
    private List<ChessMove> knightMoves(int rank, int file) {
        List<ChessMove> knightList = new List<ChessMove>();

        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                if (x * x + y * y == 5) {
                    if (0 <= rank + x < 8 && 0 <= file + y < 8 &&
                        boardState[rank + x][rank + y] * onMove <= 0) {
                        knightList.add(new ChessMove(rank, file, rank + x, file + y, boardState[rank][file]));
                    }
                }
            }
        }
        
        return knightList;
    }
            
    /**
     * Takes a rank and file where an on-move king is located and returns list of moves that king can make, not considering check.
     */
    private List<ChessMove> kingMoves(int rank, int file) {
        List<ChessMove> kingList = new List<ChessMove>();

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (0 <= rank + x < 8 && 0 <= file + y < 8) {
                    kingList.add(new ChessMove(rank, file, rank + x, file + y, boardState[rank][file]));
                    
                }
            }
        }
        
        return kingList;
    }
        
    
    
    /**
     * Counts pieces of color indicated by side attacking the specified square.
     */
    public int numAttacking(int side, int rank, int file) {
        int count = 0;
        
        // Search nearby squares for knights and kings.
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                // Look for knights
                if (x * x + y * y == 5) {
                    if (0 <= rank + x < 8 && 0 <= file + y < 8) {
                        if( boardState[rank + x][file + y] * side == 3 ) {
                            count += 1;
                        }
                    }
                }
                // Look for kings
                if (-1 <= x <= 1 && -1 <= y <= 1 && (x != 0 || y != 0) ) {
                    if (0 <= rank + x < 8 && 0 <= file + y < 8) {
                        if( boardState[rank + x][file + y] * side == 6 ) {
                            count += 1;
                        }
                    }
                }
            }
        }
        // Search rows, columns, for pieces

        int[] orthogonalPieces = new int[4];
        
        x = 1;
        y = 0;
        while ((x + 1) < 8 && boardState[rank + x][file + y] == 0) {
            x++;
        }
        orthogonalPieces[0] = boardState[rank + x][file + y];
        
        x = -1;
        y = 0;
        while ((x - 1) >= 0 && boardState[rank + x][file + y] == 0) {
            x--;
        }
        orthogonalPieces[1] = boardState[rank + x][file + y];
        
        x = 0;
        y = 1;
        while ((7 + 1) < 8 && boardState[rank + x][file + y] == 0) {
            y++;
        }
        orthogonalPieces[2] = boardState[rank + x][file + y];
        
        x = 0;
        y = -1;
        while ((y - 1) >= 0 && boardState[rank + x][file + y] == 0) {
            y--;
        }
        orthogonalPieces[3] = boardState[rank + x][file + y];
        
        for (int piece : orthogonalPieces) {
            if (piece * side == 2 || piece * side == 5) {
                count += 1
            }
        }
        
        // Search diagonals for pieces
        
        int[] diagonalPieces = new int[4];
        
        x = 1;
        y = 1;
        while ((x + 1) < 8 && (y + 1) < 8 && boardState[rank + x][file + y] == 0) {
            x++;
            y++;
        }
        diagonalPieces[0] = boardState[rank + x][file + y];
        
        x = -1;
        y = 1;
        while ((x - 1) >= 0 && (y + 1) < 8 && boardState[rank + x][file + y] == 0) {
            x--;
            y++;
        }
        diagonalPieces[1] = boardState[rank + x][file + y];
        
        x = 0;
        y = 1;
        while ((7 + 1) < 8 && boardState[rank + x][file + y] == 0) {
            y++;
        }
        diagonalPieces[2] = boardState[rank + x][file + y];
        
        x = 0;
        y = -1;
        while ((y - 1) >= 0 && boardState[rank + x][file + y] == 0) {
            y--;
        }
        diagonalPieces[3] = boardState[rank + x][file + y];
        
        for (int piece : orthogonalPieces) {
            if (piece * side == 2 || piece * side == 5) {
                count += 1
            }
        }
        
    }
    
    
    public void makeMove(ChessMove move) {
        
    }
}
    
        