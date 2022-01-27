// This class represents an individual piece in the game

public class Piece {

    public Piece(boolean white, boolean king, String str) {
        this.bWhite = white;
        this.bKing = king;
        this.symbol = str;
    }

    private boolean bWhite; // If Piece is white
    private boolean bKing; // if Piece is king
    private String symbol = ""; // Symbol representation of the piece

    /**
     * @param king : If the current piece is a king
     * @brief Sets a piece to a king
     */
    public void setKing(boolean king) {
        this.bKing = king;
    }

    /**
     * @return If the current piece is white
     * @brief To know if a piece is a white piece
     */
    public boolean isWhite() {
        return bWhite;
    }

    /**
     * @return If the current piece is a king
     * @brief To know if a piece is a king
     */
    public boolean isKing() {
        return bKing;
    }

    /**
     * @return The character representation of a piece
     * @brief To know the symbol of a piece
     */
    public String getSymbol() {
        return symbol;
    }
}
