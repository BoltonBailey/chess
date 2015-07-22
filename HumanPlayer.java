import java.io.*;
import java.util.*;

public class HumanPlayer extends ChessPlayer {

    public ChessMove getMove(ChessBoard board) {
        HashSet<ChessMove> legal = board.legalMoves();
        
        board.printBoard();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type in string of your move: ");
        String choice = scanner.nextLine();
        
        
        for (ChessMove move : legal) {
            System.out.println(move.toString());
            if (move.toString().startsWith(choice)) {
                return move;
            }
        }
        
        return null;
    }

}