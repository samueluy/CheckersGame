public class Move {
    Board board = new Board();

    void move(int fromX, int fromY, int toX, int toY){
        if(checkValid(fromX,fromY,toX,toY)) {
            Piece temp = board.getCell(fromX, fromY).getPiece();
            board.getCell(fromX, fromY).setPiece(null);
            board.getCell(toX, toY).setPiece(temp);
        }
        else
            System.out.println("Invalid move! Please input again.");

        checkAndCapture();
    }

    // diagonal
    // outside
    // no piece after capture
    //
    boolean checkValid(int fromX, int fromY, int toX, int toY){
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

    void checkAndCapture() { // checks for available captures and if so, capture
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                // check up right
                capture(i+1, y-1);
                // check up left
                capture(i-1, y-1);
                // check down left
                capture(i-1, y+1);
                //check down right
                capture(i+1,y+1);
            }
        }
    }

    void capture(int toX, int toY){ // missing check if has piece near and capture
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++){
                if(checkValid(i,y,toX,toY)) {
                    Piece temp = board.getCell(i, y).getPiece();
                    board.getCell(i, y).setPiece(null);
                    board.getCell(toX, toY).setPiece(temp);
                }
            }
        }
    }
}
