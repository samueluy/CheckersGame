// This class represent each square of the game board

import java.awt.*;

public class Cell {

    public Cell() {
        coord = new Point();
    }

    public Cell(Cell orig) {
        this.piece = orig.piece;
        this.coord = orig.coord;
    }

    private Piece piece; // Piece in the cell
    private Point coord; // Coordinate of the cell

    /**
     * @param piece : Piece to be added to the Cell
     * @brief Adds a piece to a cell
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * @param x : X Coordinate to be set
     * @param y : Y Coordinate to be set
     * @brief Adds a piece to a cell
     */
    public void setCoord(int x, int y) {
        this.coord.setLocation(x, y);
    }

    /**
     * @return Piece in the Cell
     * @brief Gets the current piece of this Cell
     */
    public Piece getPiece() {
        return piece;
    }
}
