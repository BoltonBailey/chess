import java.util.*;
import java.io.*;

public class ChessPosition {

    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    public ChessPosition parentPosition;
    public double advantage;
    public boolean isHeuristic;
    public ChessBoard board;
    
    public ChessPosition(ChessPosition parent, ChessBoard b, double adv) {
        parentPosition = parent;
        board = b;
        isHeuristic = true;
        advantage = adv;
    }
    
    
    /**
     * Will correct the advantages of this and all predecessors
     */
    public void updateAllAdvantages(double newAdvantage) {

        // Update
        if (isHeuristic) {
            isHeuristic = false;
            advantage = newAdvantage;
        } else {
            if (board.onTurn == WHITE) {
                advantage = Math.max(advantage, newAdvantage);
            } else {
                advantage = Math.min(advantage, newAdvantage);
            }
        }

        // If parent
        if (parentPosition != null) {
            // Parent update if this was updated
            if (advantage == newAdvantage) {
                parentPosition.updateAllAdvantages(advantage);
            }
        }
    }
}