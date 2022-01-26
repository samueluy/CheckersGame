// This class represents the board of the game

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable {
    private int whiteCount = 12; // Count of regular white pieces
    private int blackCount = 12; // Count of regular black pieces
    private int whiteKingCount = 0; // Count of white king pieces
    private int blackKingCount = 0; // Count of black king pieces
    List<List<Cell>> block = new ArrayList<List<Cell>>(); // List of cells representing each square
    private boolean whiteSide = true; // Current side playing

    public Board() {
    }

    /**
     * @param white : If the board is white side
     * @brief Changes the side playing
     */
    public void setWhiteSide(boolean white) {
        this.whiteSide = white;
    }

    /**
     * @param block : List of cells
     * @brief Sets the locations of the pieces given a list of Cells
     */
    public void setBlock(List<List<Cell>> block) {
        this.block = block;
    }

    /**
     * @param n : Number of white pieces
     * @brief Set the number of white pieces
     */
    public void setWhiteCount(int n) {
        this.whiteCount = n;
    }

    /**
     * @param n : Number of black pieces
     * @brief Set the number of black pieces
     */
    public void setBlackCount(int n) {
        this.blackCount = n;
    }

    /**
     * @param n : Number of white king pieces
     * @brief Set the number of white king pieces
     */
    public void setWhiteKingCount(int n) {
        this.whiteKingCount = n;
    }

    /**
     * @param n : Number of black king pieces
     * @brief Set the number of black king pieces
     */
    public void setBlackKingCount(int n) {
        this.blackKingCount = n;
    }

    /**
     * @return Number of white pieces
     * @brief Get the number of white pieces
     */
    public int getWhiteCount() {
        return whiteCount;
    }

    /**
     * @return Number of black pieces
     * @brief Get the number of black pieces
     */
    public int getBlackCount() {
        return blackCount;
    }

    /**
     * @return Number of white king pieces
     * @brief Get the number of white king pieces
     */
    public int getWhiteKingCount() {
        return whiteKingCount;
    }

    /**
     * @return Number of white king pieces
     * @brief Get the number of white king pieces
     */
    public int getBlackKingCount() {
        return blackKingCount;
    }

    /**
     * @brief Initialize the starting position of pieces
     */
    public void createBoard() {
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                block.add(new ArrayList<>());
                block.get(i).add(new Cell());
                block.get(i).get(y).setCoord(i, y);

                if (y % 2 == 1) {
                    if (i == 0 || i == 2) {
                        Piece temp = new Piece(true, false, "w");
                        block.get(i).get(y).setPiece(temp);
                    }
                } else {
                    if (i == 1) {
                        Piece temp = new Piece(true, false, "w");
                        block.get(i).get(y).setPiece(temp);
                    }
                }

                if (y % 2 == 0) {
                    if (i == 5 || i == 7) {
                        Piece temp = new Piece(false, false, "b");
                        block.get(i).get(y).setPiece(temp);
                    }
                } else {
                    if (i == 6) {
                        Piece temp = new Piece(false, false, "b");
                        block.get(i).get(y).setPiece(temp);
                    }
                }
            }
        }
    }

    /**
     * @brief Display the current board
     */
    public void showBoard() {
        int vert = 0;
        int hor = 0;
        System.out.print("  ");
        for (int i = 0; i < 8; i++) {
            System.out.print(hor + " ");
            hor++;
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.print(vert + " ");
            vert++;
            for (int y = 0; y < 8; y++) {
                if (block.get(i).get(y).getPiece() != null)
                    System.out.print(block.get(i).get(y).getPiece().getSymbol());
                else System.out.print("+");
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * @return If the game is over
     * @brief Check if the game is over and display the winner
     */
    public boolean over() {
        if (whiteCount == 0 || blackCount == 0) {
            if (whiteCount == 0) System.out.println("Black win!");
            else System.out.println("White win!");

            return true;
        }
        return false;
    }

    /**
     * @return If white is the side currently playing
     * @brief Check what side is currently playing
     */
    public boolean isWhiteSide() {
        return whiteSide;
    }

    /**
     * @param x : X coordinate of Cell
     * @param y : Y coordinate of Cell
     * @return Cell located in the given X and Y coordinate
     * @brief Get the cell given x and y coordinate
     */

    public Cell getCell(int x, int y) {
        return block.get(y).get(x);
    }

    /**
     * @param moves : ArrayList of possible moves in state
     * @return Cell located in the given X and Y coordinate
     * @brief Calculate the heuristic of the current layout
     */
    public int calcHeuristic(ArrayList<String> moves) {
        int pieceValue = whiteCount * 3 - blackCount * 3; // Each piece is given the value of 3
        int kingValue = (whiteKingCount * 5 - blackKingCount * 5); // Each king is given the value of 5
        int nMoves = moves.size(); // Each possible move is given the value of 1
        return pieceValue + kingValue + nMoves;
    }

    /**
     * @param inBoard : Current board working on
     * @brief Convert a regular piece to a king when it reaches the other side of the board
     */
    public void convertToKing(Board inBoard) {
        for (int i = 0; i < 8; i++) {
            if (inBoard.getCell(i, 0).getPiece() != null) {
                if (!inBoard.getCell(i, 0).getPiece().isWhite()) {
                    inBoard.setBlackCount(inBoard.getBlackCount() - 1);
                    inBoard.setBlackKingCount(inBoard.getBlackKingCount() + 1);
                    inBoard.getCell(i, 0).getPiece().setKing(true);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if (inBoard.getCell(i, 7).getPiece() != null) {
                if (inBoard.getCell(i, 7).getPiece().isWhite()) {
                    inBoard.setWhiteCount(inBoard.getWhiteCount() - 1);
                    inBoard.setWhiteKingCount(inBoard.getWhiteKingCount());
                    inBoard.getCell(i, 0).getPiece().setKing(true);
                }
            }
        }
    }
}
