import java.util.*;

public class Pawn extends ChessPiece {

    public Pawn(ChessBoard b, int s) {
        super(b, s);
    }
    
    public HashSet<ChessSquare> attacking() {
    
        HashSet<ChessSquare> pawnAttacks = new HashSet<ChessSquare>();
        
        if (side == WHITE) {
            jumpMoves(pawnAttacks, 1, 1);
            jumpMoves(pawnAttacks, 1, -1);
        }
        else {
            jumpMoves(pawnAttacks, -1, 1);
            jumpMoves(pawnAttacks, -1, -1);
        }
        
        return pawnAttacks;
    }
    
    public HashSet<ChessMove> legalMoves() {
        
        int dir;
        
        if (side == WHITE) {
            dir = 1;
        }
        else {
            dir = -1;
        }
        
        HashSet<ChessMove> pawnMoves = new HashSet<ChessMove>();
        
        ChessSquare square = getSquare();
        int rank = square.rank;
        int file = square.file;
        
        

        // Single and double moves
        ChessSquare sSquare = board.getSquare(rank + dir, file);
        
        if (!sSquare.isOccupied()) {
            board.place(this, sSquare);
            if (!board.inCheck(side)) {
                pawnMoves.add(new ChessMove(this, sSquare));
            }
            board.place(this, square);
            if ((rank == 1 && side == WHITE) || (rank == 6 && side == BLACK)) {
                ChessSquare dSquare = board.getSquare(rank + 2 * dir, file);
                if (!dSquare.isOccupied()) {
                    board.place(this, dSquare);
                    if (!board.inCheck(side)) {
                        pawnMoves.add(new ChessMove(this, dSquare, "d"));
                    }
                    board.place(this, square);
                }
            }
        }
        // Left Captures
        if (file > 0) {
            ChessSquare lSquare = board.getSquare(rank + dir, file - 1);
            if (lSquare.isOccupied()) {
                ChessPiece capturedPiece = board.getPiece(lSquare);
                if (capturedPiece.getSide() != side) {
                    board.place(this, lSquare);
                    if (!board.inCheck(side)) {
                        pawnMoves.add(new ChessMove(this, lSquare));
                    }
                    board.place(this, square);
                    board.place(capturedPiece, lSquare);
                }
            }
            if (lSquare == board.enPassantSquare) {
                ChessSquare capturedSquare = board.getSquare(rank, file - 1);
                ChessPiece capturedPawn = board.getPiece(capturedSquare);
                board.place(this, board.enPassantSquare);
                board.clearSquare(capturedSquare);
                if (!board.inCheck(side)) {
                    pawnMoves.add(new ChessMove(this, lSquare, "ep"));
                }
                board.place(this, square);
                board.place(capturedSquare, capturedPawn);
            }
        }
        // Right Captures
        if (file < 7) {
            ChessSquare rSquare = board.getSquare(rank + dir, file + 1);
            if (rSquare.isOccupied()) {
                ChessPiece capturedPiece = board.getPiece(rSquare);
                if (capturedPiece.getSide() != side) {
                    board.place(this, rSquare);
                    if (!board.inCheck(side)) {
                        pawnMoves.add(new ChessMove(this, rSquare));
                    }
                    board.place(this, square);
                    board.place(capturedPiece, rSquare);
                }
            }
            if (rSquare == board.enPassantSquare) {
                ChessSquare capturedSquare = board.getSquare(rank, file + 1);
                ChessPiece capturedPawn = board.getPiece(capturedSquare);
                board.place(this, board.enPassantSquare);
                board.clearSquare(capturedSquare);
                if (!board.inCheck(side)) {
                    pawnMoves.add(new ChessMove(this, rSquare, "ep"));
                }
                board.place(this, square);
                board.place(capturedSquare, capturedPawn);
            }
        }
        // If pawn is on rank 0 or 7, it promotes
        if (rank + dir == 7 || rank + dir == 0) {
        
            HashSet<ChessMove> promotionMoves = new HashSet<ChessMove>();
            
            for (ChessMove move : pawnMoves) {
                promotionMoves.add(new ChessMove(this, move.square, "R"));
                promotionMoves.add(new ChessMove(this, move.square, "N"));
                promotionMoves.add(new ChessMove(this, move.square, "B"));
                promotionMoves.add(new ChessMove(this, move.square, "Q"));
            }
            
            return promotionMoves;
        }
        
        return pawnMoves;
    }
    
    public String letter() {
        if (side == WHITE) {
            return "P";
        }
        else {
            return "p";
        }
    }
    
    
}