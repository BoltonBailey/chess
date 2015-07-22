import java.util.*;

public class ChessAdvantage implements Comparable<ChessAdvantage> {

    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    // True if position is calculated to be a draw
    public final boolean isDraw;
    // True if position is calculated to be a win for white
    public final boolean whiteMate;
    // True if position is calculated to be a win for black
    public final boolean blackMate;
    // Estimated White advantage.
    public final double whiteAdv;
    // Move on which the board was analyzed
    public final int moveCount;
    
    // Returns a draw
    public ChessAdvantage(int count) {
        isDraw = true;
        whiteMate = false;
        blackMate = false;
        whiteAdv = 0;
        moveCount = count;
    }
    
    // Returns a win for the indicated side
    public ChessAdvantage(int side, int count) {
        isDraw = false;
        whiteMate = (side == WHITE);
        blackMate = (side == BLACK);
        if (side == WHITE) {
            whiteAdv = Double.POSITIVE_INFINITY;
        }
        else {
            whiteAdv = Double.NEGATIVE_INFINITY;
        }
        moveCount = count;
    }
    
    // Returns an advantage for a side
    public ChessAdvantage(double adv, int count) {
        isDraw = false;
        whiteMate = false;
        blackMate = false;
        whiteAdv = adv;
        moveCount = count;
    }
    
    public int compare(ChessAdvantage first, ChessAdvantage second) {
        return first.compareTo(second);
    }
    
    
    // Overriding the compareTo method
    public int compareTo(ChessAdvantage other){
        if (whiteAdv > other.whiteAdv) {
            return 1;
        }
        else if (whiteMate) {
            return other.moveCount - moveCount;
        }
        else if (other.blackMate) {
            return moveCount - other.moveCount;
        }
        return -1;
    }

    public String toString() {
        return Double.toString(whiteAdv);
    }

}
    
    
    
    
    
    
    /*
    // Returns a chess advantage of a position, where white has choice between
    // set of advantages
    // Must be nonempty - returns one of the set
    public static ChessAdvantage whiteMove(HashSet<ChessAdvantage> set) {
        if (set.isEmpty()) {
            throw new IllegalArgumentException("Empty move set");
        }
        ChessAdvantage chosen = null;
        for (ChessAdvantage adv : set) {
            // deal with first iteration
            if (chosen == null) {
                chosen = adv;
                continue;
            }
            if (chosen.whiteAdv < adv.whiteAdv) {
                chosen = adv;
            }
            else if (chosen.whiteMate && adv.whiteMate) {
                if (adv.moveCount < chosen.moveCount) {
                    chosen = adv;
                }
            }
        }
        return chosen;
        
    }
    
    //
    public static ChessAdvantage blackMove(HashSet<ChessAdvantage> set) {
        if (set.isEmpty()) {
            throw new IllegalArgumentException("Empty move set");
        }
        ChessAdvantage chosen = null;
        for (ChessAdvantage adv : set) {
            // deal with first iteration
            if (chosen == null) {
                chosen = adv;
                continue;
            }
            if (chosen.whiteAdv > adv.whiteAdv) {
                chosen = adv;
            }
            else if (chosen.blackMate && adv.blackMate) {
                if (adv.moveCount < chosen.moveCount) {
                    chosen = adv;
                }
            }
        }
        return chosen;
    }
    
    public static ChessAdvantage getAdv(int side, HashSet<ChessPosition> set) {
        HashSet<ChessAdvantage> advSet = new HashSet<ChessAdvantage>();
        for (ChessPosition pos : set) {
            advSet.add(pos.advantage);
        }
        if (side == WHITE) {
            return whiteMove(advSet);
        }
        else {
            return blackMove(advSet);
        }
    }
    */

