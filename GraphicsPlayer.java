import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;
import java.io.File;
import java.awt.image.RenderedImage;
import java.lang.System;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;



public class GraphicsPlayer extends ChessPlayer {

    /** Initialization of variables */
    private JImageDisplay display; // Reference to object to be displayed
    private javax.swing.JFrame frame;
    private JComboBox<ChessMove> comboBox;
    private static final int squareSize = 100;
    private static final int displaySize = squareSize * 8;
    private int rowS;
    private int colS;
    private int rowE;
    private int colE;


    public GraphicsPlayer() {

        display = new JImageDisplay(displaySize, displaySize);
        display.addMouseListener(new MouseHandler());

        frame = new javax.swing.JFrame("Bolton's Player");

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(display, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        // Displays frame in a window.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawSquare(int rank, int file, ChessBoard board) {

        Color backgroundColor;
        if ((rank + file) % 2 == 0) {
            backgroundColor = Color.GREEN;
        } else {
            backgroundColor = Color.YELLOW;
        }

        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                display.drawPixel(file * squareSize + j, 
                  displaySize - rank * squareSize - i - 1,
                  backgroundColor.getRGB());
            }
        }

        ChessPiece piece = board.getPiece(rank, file);
        if (piece != null) {
            Color pieceColor;
            if (piece.side == ChessPiece.WHITE) {
                pieceColor = Color.WHITE;
            } else {
                pieceColor = Color.BLACK;
            }

            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    double x = 2 * ((double)i)/((double)squareSize) - 1;
                    double y = 2 * ((double)j)/((double)squareSize) - 1;
                    boolean toColor = false;
                    if (piece instanceof Rook) {
                        toColor = (Math.abs(x) < 0.2 || Math.abs(y) < 0.2 );
                    }
                    if (piece instanceof Bishop) {
                        toColor = (Math.abs(x+y) < 0.2 || Math.abs(x-y) < 0.2 );
                    }
                    if (piece instanceof Queen) {
                        toColor = (Math.abs(x) < 0.2 || Math.abs(y) < 0.2 ) ||
                                  (Math.abs(x+y) < 0.2 || Math.abs(x-y) < 0.2 );
                    }
                    if (piece instanceof Knight) {
                        toColor = (0.5 < (x*x + y*y) && (x*x + y*y) < 1);
                    }
                    if (piece instanceof Pawn) {
                        toColor = (Math.abs(x+y) < 0.5 && Math.abs(x-y) < 0.5) ;
                    }
                    if (piece instanceof King) {
                        toColor = Math.abs(x) < 0.7 && Math.abs(y) < 0.7 ;
                    }

                    if (toColor) {
                        display.drawPixel(file * squareSize + j, 
                          displaySize - rank * squareSize - i - 1,
                          pieceColor.getRGB());
                    }
                }
            }
        }
    }

    private void drawBoard(ChessBoard board) {
        // Loops that iterate through all pixels on the display.
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                drawSquare(rank, file, board);
            }
        }
        // Repaint the display screen, to show the new colors.
        Rectangle toRepaint = new Rectangle(displaySize, displaySize);
        display.repaint(toRepaint);
    }

    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            // Find pixel coordinates on which click occurred.
            int x = e.getX();
            int y = e.getY();
            // Find square on board corresponding to these.
            int file = x / squareSize;
            int rank = 7 - y / squareSize;
            // Set
            if (rowS == -1) {
                rowS = rank;
                colS = file;
                System.out.format("Set start square (%d, %d) %n", rank, file);
            } else if (rowE == -1) {
                rowE = rank;
                colE = file;
                System.out.format("Set end square (%d, %d)%n", rank, file);
            } else {
                System.out.format("Cleared set squares %n");
                rowS = -1;
                colS = -1;
                rowE = -1;
                colE = -1;
            }
        }
    }

    public ChessMove getMove(ChessBoard board) {

        drawBoard(board);

        HashSet<ChessMove> legal = board.legalMoves();

        frame.pack();

        rowS = -1;
        colS = -1;
        rowE = -1;
        colE = -1;

        ChessMove move = null;
        
        while (move == null) {
            for (ChessMove posMove : legal) {
                if (rowS == posMove.piece.getSquare().rank &&
                    colS == posMove.piece.getSquare().file &&
                    rowE == posMove.square.rank &&
                    colE == posMove.square.file) {

                    move = posMove;
                }
            }
        }

        return move;

    }
    
}
