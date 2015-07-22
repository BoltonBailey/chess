
import java.io.*;
import java.util.*;

public class TreePlayer extends ChessPlayer {

    // Numbers to represent the piece colors
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public static final long defaultTime = 1000;

    public long time;
    public ChessHeuristic heuristic;
    
    public TreePlayer(ChessHeuristic h, long time) {
        heuristic = h;
        this.time = time;
    }


    public TreePlayer(ChessHeuristic h) {
        heuristic = h;
        this.time = defaultTime;
    }


    public ChessMove getMove(ChessBoard board) {
        
        ChessTree gameTree = new ChessTree(board, heuristic);

        long start = new Date().getTime();
        int count = 0;

        while (new Date().getTime() - start < time) {
        	gameTree.expandLeaf();
        	count++;
        }
        long elapsedTime = new Date().getTime() - start;

        System.out.println("At time " + elapsedTime + "ms");
        System.out.println("Expanded " + count + " leaves");

        return gameTree.getBestRootMove();
    }
}