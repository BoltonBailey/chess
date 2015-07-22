import java.util.*;

public class RandomPlayer extends ChessPlayer {

    Random generator;
    
    public RandomPlayer() {
        generator = new Random();
    }
    
    public ChessMove getMove(ChessBoard board) {
        HashSet<ChessMove> legal = board.legalMoves();
        
        int size = legal.size();
        
        int item = generator.nextInt(size);
        
        ChessMove randomMove = null;
        
        int i = 0;
        for (ChessMove move : legal) {
            if (i == item) {
                randomMove = move;
            }
            i++;
        }
        
        return randomMove;
    }
}