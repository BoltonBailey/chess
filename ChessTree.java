import java.util.*;
import java.io.*;

public class ChessTree {
    
    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    

    private ChessPosition rootPosition;
    private LinkedList<ChessPosition> leafPositions;
    private HashMap<ChessMove, ChessPosition> nextMoveMap;
    private ChessHeuristic heuristic;
    
    public ChessTree(ChessBoard root, ChessHeuristic h) {
        
        heuristic = h;
        
        leafPositions = new LinkedList<ChessPosition>();
        
        rootPosition = new ChessPosition(null, root, heuristic.heuristicEvaluate(root));
        
        nextMoveMap = new HashMap<ChessMove, ChessPosition>();

        // Loop through all legal moves on the board
        for (ChessMove move : root.legalMoves()) {
        
            // Make a new board, and effect that move on it
            ChessBoard childBoard = new ChessBoard(root);
            childBoard.makeMove(new ChessMove(move, childBoard));

            // Get new heuristic value
            double hvalue = heuristic.heuristicEvaluate(childBoard);
            
            // Make the position for this new board
            ChessPosition childPosition;
            childPosition = new ChessPosition(rootPosition, childBoard, hvalue);

            leafPositions.addLast(childPosition);
            rootPosition.updateAllAdvantages(hvalue);
            nextMoveMap.put(move, childPosition);
        }   
    }
    
    // Puts all children from next leaf
    // Consider terminal positions?
    // Leaf nodes have no childAdvantages until they are expanded
    public void expandLeaf() {
    
        // The position to be expanded.
        ChessPosition parentPosition = leafPositions.removeFirst();
    
        
        // Loop through all legal moves on the board
        for (ChessMove move : parentPosition.board.legalMoves()) {
        
            // Make a new board, and effect that move on it
            ChessBoard childBoard = new ChessBoard(parentPosition.board);
            childBoard.makeMove(new ChessMove(move, childBoard));

            // Get new heuristic value
            double hvalue = heuristic.heuristicEvaluate(childBoard);
            
            // Make the position for this new board
            ChessPosition childPosition;
            childPosition = new ChessPosition(parentPosition, childBoard, hvalue);
            leafPositions.addLast(childPosition);
            
            parentPosition.updateAllAdvantages(hvalue);
        }
        
    }
    
    public ChessMove getBestRootMove() {

        assert (!rootPosition.isHeuristic);
        
        System.out.println(rootPosition.advantage);
        System.out.println();
        
        for (ChessMove move : nextMoveMap.keySet()) {


            ChessPosition pos = nextMoveMap.get(move);

            System.out.println(move);
            System.out.println(pos.advantage);
            System.out.println();

            if (pos.advantage == rootPosition.advantage) {
                return move;
            }

        }

        System.out.println("ChessTree: Could not find move");
        return null;
    }
}








    