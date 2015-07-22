import java.util.*;

public abstract class ChessHeuristic {
    
    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    //public HashMap<ReducedChessBoard, double> memoMap;
    public int moveDepth;
    
    //todo memoize copying?
    /*
    public ChessHeuristic() {
        memoMap = new HashMap<ReducedChessBoard, double>(10000);
    }
    */
    
    
    // New chess advantage
    public abstract double heuristicEvaluate(ChessBoard board);
    
    
    /**
     * Evaluates the ChessBoard by recursion up to ply depth. Returns the advantage of the current position with correct play up to the given depth.
     * ab represents an advantage that is an alpha-beta. If there is a single child board that is better than this value, we can return ab.
     */
    /*
    public double depthEvaluate(ChessBoard board, double ab) {
    
        if (board == null) {
            throw new IllegalArgumentException("Null board given");
        }
        
        ReducedChessBoard redBoard = new ReducedChessBoard(board);
    
        if (memoMap.containsKey(redBoard)) {
            return memoMap.get(redBoard);
        }
    
        double adv = null;
        
        // Check game ended
        if (board.isDraw()) {
            adv = new double(board.moveCount);
            memoMap.put(redBoard, adv);
            return adv;
        }
        
        // Check game won
        else if (board.isCheckmated()) {
            // Black is on turn means black is mated
            if (board.onTurn == BLACK) {
                adv = new double(WHITE, board.moveCount);
            }
            else {
                adv = new double(BLACK, board.moveCount);
            }
            memoMap.put(redBoard, adv);
        }
        
        // Game not over, but at depth, We use the heuristic in this case.
        else if (board.moveCount == moveDepth) {
            adv = heuristicEvaluate(board);
            memoMap.put(redBoard, adv);
        }
        
        // Not at depth and game not over - there exist legal moves.
        else {

            // Order the moves to improve ab pruning
            Vector<ChessMove> orderedMoves = new Vector<ChessMove>();
            Vector<double> ordAdv = new Vector<double>();

            for (ChessMove move : board.legalMoves()) {
                
                ChessBoard tempBoard = new ChessBoard(board);
                tempBoard.makeMove(new ChessMove(move, tempBoard));
                double tempAdv = heuristicEvaluate(tempBoard);

                int index = 0;
                for (double advScan : ordAdv) {
                    if (board.onTurn == WHITE) {
                        if (tempAdv.compareTo(advScan) < 0) {
                            index++;
                        }
                    }
                    else {
                        if (tempAdv.compareTo(advScan) > 0) {
                            index++;
                        }
                    }
                }

                ordAdv.add(tempAdv);

                orderedMoves.insertElementAt(move, index);
            }


            if (ab == null) {
                for (ChessMove move : orderedMoves) {
            
                    ChessBoard tempBoard = new ChessBoard(board);
                    tempBoard.makeMove(new ChessMove(move, tempBoard));
            
                    // First iter
                    if (adv == null) {
                        adv = depthEvaluate(tempBoard);
                        continue;
                    }
                
                    if (board.onTurn == WHITE) {
                        if (depthEvaluate(tempBoard).compareTo(adv) > 0) {
                            adv = depthEvaluate(tempBoard, adv);
                        }
                    }
                    else {
                        if (depthEvaluate(tempBoard).compareTo(adv) < 0) {
                            adv = depthEvaluate(tempBoard, adv);
                        }
                    }
                }
                memoMap.put(redBoard, adv);
            }
            // Alpha-beta prune
            else { // ab not null

                for (ChessMove move : orderedMoves) {
            
                    ChessBoard tempBoard = new ChessBoard(board);
                    tempBoard.makeMove(new ChessMove(move, tempBoard));
            
                    // First iter
                    if (adv == null) {
                        adv = depthEvaluate(tempBoard);
                        continue;
                    }
                
                    if (board.onTurn == WHITE) {
                        if (depthEvaluate(tempBoard).compareTo(adv) > 0) {
                            adv = depthEvaluate(tempBoard, adv);
                        }
                    }
                    else {
                        if (depthEvaluate(tempBoard).compareTo(adv) < 0) {
                            adv = depthEvaluate(tempBoard, adv);
                        }
                    }
                    //ab prune
                    if (board.onTurn == WHITE) {
                        if (adv.compareTo(ab) >= 0) {
                            return ab;
                        }
                    }
                    else {
                        if (adv.compareTo(ab) <= 0) {
                            return ab;
                        }
                    }
                }
            }
        }
        
        return adv;
    }

    public double depthEvaluate(ChessBoard board) {
        return depthEvaluate(board, null);
    }
    */
      
}