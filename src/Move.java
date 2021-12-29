//fix side

public class Move {
    Board board = new Board();

    void move(int fromX, int fromY, int toX, int toY){
        if(checkValid(fromX,fromY,toX,toY)) { // + make similar to check capture
            Piece temp = board.getCell(fromX, fromY).getPiece();
            board.getCell(fromX, fromY).setPiece(null);
            board.getCell(toX, toY).setPiece(temp);
            board.setWhiteSide(!board.isWhiteSide());
            board.showBoard();
        }
        else
            System.out.println("Invalid move! Please input again.");

        checkAndCapture();
    }

    // diagonal
    // outside
    // no piece after capture
    //
    boolean checkValid(int fromX, int fromY, int toX, int toY){ // + make similar to notext
        if(board.getCell(fromX,fromY).getPiece() != null) { // check if side is valid
            if (board.isWhiteSide() != board.getCell(fromX, fromY).piece.isWhite()) {
                System.out.println("Invalid side");
                return false;
            }
        }
        if(toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0){ // out of bounds
            System.out.println("Out of bounds");
            return false;
        }
        else if(board.getCell(fromX,fromY) == null){
            System.out.println("Not a valid piece");
            return false;
        }
        else if(board.getCell(toX,toY).getPiece() != null){
            System.out.println("Can not land over a piece");
            return false;
        }
        else
            return true;
    }

    boolean checkValidNoText(int fromX, int fromY, int toX, int toY){ // no text print,
        if(toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) // out of bounds
            return false;

        if(board.getCell(fromX,fromY).getPiece() != null) { // check if side is valid
            if (board.isWhiteSide() != board.getCell(fromX, fromY).piece.isWhite()) return false;

            if(board.getCell(toX,toY).getPiece() != null){
                // same side
                return board.getCell(fromX, fromY).getPiece().isWhite() != board.getCell(toX, toY).getPiece().isWhite();
            }
        }
        else if(board.getCell(fromX,fromY) == null)
            return false;
        else if(board.getCell(toX,toY).getPiece() != null)
            return false;

            return true;
    }

    // swap side move
    void checkAndCapture() { // checks for available captures and if so, capture
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                // check up right
                if(!(i + 1 >= 8 || y - 1 < 0)){
                    if(board.getCell(i+1,y-1).getPiece()!=null)
                        capture(i,y,i+2, y-2,i+1,y-1);
                }

                // check up left
                if(!(i-1 < 0 || y-1 < 0)){
                    if(board.getCell(i-1,y-1).getPiece()!=null)
                        capture(i,y,i-2, y-2,i-1,y-1);
                    }
                // check down left
                if(!(i-1 <0 || y+1 >= 8)) {
                    if (board.getCell(i - 1, y + 1).getPiece() != null) capture(i,y,i - 2, y + 2,i-1,y+1);
                }
                //check down right
                if(!(i+1 >= 8 || y+1 >= 8)) {
                    if (board.getCell(i + 1, y + 1).getPiece() != null) capture(i,y,i + 2, y + 2,i+1,y+1);
                }
            }
        }
    }

    void capture(int fromX, int fromY, int toX, int toY, int capturedX, int capturedY){ // missing check if has piece near and capture
        if(checkValidNoText(fromX,fromY,capturedX,capturedY)) { // originally toX toY
            if(checkValidNoText(fromX,fromY,toX,toY)) { // originally wala to
                Piece temp = board.getCell(fromX, fromY).getPiece();
                board.getCell(fromX, fromY).setPiece(null);
                board.getCell(toX, toY).setPiece(temp);
                board.setWhiteSide(!board.isWhiteSide());

                if(board.getCell(capturedX,capturedY).getPiece().isWhite())
                    Board.whiteCount--;
                else
                    Board.blackCount--;

                board.getCell(capturedX, capturedY).setPiece(null);
                board.showBoard();
            }
        }
    }
}
