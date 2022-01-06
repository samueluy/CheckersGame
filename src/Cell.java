import java.awt.*;

public class Cell {

    Cell() {
        coord = new Point();
    }

    Cell(Piece unit) {
        coord = new Point();
        piece = unit;
    }

    Cell(Cell orig) {
        this.piece = orig.piece;
        this.coord = orig.coord;
    }

    Piece piece;
    Point coord = new Point();

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    void setCoord(int x, int y) {
        this.coord.setLocation(x, y);
    }

    Point getCoord() {
        return coord;
    }

    Piece getPiece() {
        return piece;
    }
}
