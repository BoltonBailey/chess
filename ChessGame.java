import java.io.*;


public class ChessGame {

    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    // The players
    ChessPlayer whitePlayer;
    ChessPlayer blackPlayer;
    
    // The board
    ChessBoard board;
    
    // Initializer
    public ChessGame(ChessPlayer whitePlayer, ChessPlayer blackPlayer) {
    
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        
        board = new ChessBoard();
    }
    
    
    public void makeMove() {
        ChessMove move;
        if (board.onTurn == WHITE) {
            move = whitePlayer.getMove(board);
        }
        else {
            move = blackPlayer.getMove(board);
        }
        if (move == null) {
            throw new NullPointerException("Game given a null move.");
        }
        board.makeMove(move);
    }
    
    public void runGame() {
        
        board.printBoard();
        
        while (true) {
            
            makeMove();
            
            board.printBoard();
            
            String sidename;
            if (board.onTurn == WHITE) {
                sidename = "White";
            }
            else {
                sidename = "Black";
            }
            
            if (board.isCheckmated()) {
                System.out.println(sidename + " is checkmated.");
                break;
            }
            
            if (board.isStalemated()) {
                System.out.println(sidename + " is stalemated.");
                break;
            }
            if (board.isFiftyMove()) {
                System.out.println("Game is drawn by 50-move rule.");
                break;
            }
            if (board.isInsufficient()) {
                System.out.println("Game is drawn by insufficient material.");
                break;
            }

        }

    }
    
    
    public static void main(String[] args) {
        
        for (int i = 0; i < 1; i++){
            ChessPlayer whitePlayer = new GraphicsPlayer();
            ChessPlayer blackPlayer = new TreePlayer(new PieceHeuristic(), 250);
                    
            ChessGame myGame = new ChessGame(whitePlayer, blackPlayer);
        
            myGame.runGame();
        }
    }
}