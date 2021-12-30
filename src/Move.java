//fix side

public class Move {
    static int searchI=0;
    static int searchY=0;
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
    boolean checkValid(int fromX, int fromY, int toX, int toY) { // + make similar to notext
        if (toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) { // out of bounds
            System.out.println("Out of bounds");
            return false;
        }

        if (board.getCell(fromX, fromY).getPiece() != null) {
            if (board.isWhiteSide() != board.getCell(fromX, fromY).piece.isWhite()) { // moving different side
                System.out.println("Invalid side");
                return false;
            } else if (board.getCell(fromX, fromY).getPiece().isKing()) { // king
                int tempToX, tempToY, tempFromX, tempFromY;
                tempToX=toX;
                tempToY=toY;
                tempFromX=fromX;
                tempFromY=fromY;
                if (toX > fromX && toY < fromY) { // up right
                    while(tempToX!=tempFromX || tempToY!=tempFromY){
                        tempToX--;
                        tempToY++;
                        if(tempToX < 0 || tempToY > 7)
                            return false;
                        else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                            return false;
                    }
                    return true;
                } else if (toX < fromX && toY < fromY) { // up left
                    while(tempToX!=tempFromX || tempToY!=tempFromY){
                        tempToX++;
                        tempToY++;
                        if(tempToX > 7 || tempToY > 7)
                            return false;
                        else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                            return false;
                    }
                    return true;
                } else if (toX < fromX && toY > fromY) { // down left
                    while(tempToX!=tempFromX || tempToY!=tempFromY){
                        tempToX++;
                        tempToY--;
                        if(tempToX > 7 || tempToY < 0)
                            return false;
                        else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                            return false;
                    }
                    return true;
                } else if (toX > fromX && toY > fromY) { // down right
                    while(tempToX!=tempFromX || tempToY!=tempFromY){
                        tempToX--;
                        tempToY--;
                        if(tempToX < 0 || tempToY < 0)
                            return false;
                        else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                            return false;
                    }
                    return true;
                }
            }
            else {
                if (board.getCell(fromX, fromY).piece.isWhite()) { // white move back
                    if (toX == fromX + 1 || toX == fromX - 1) return true;
                    else if (toY != fromY + 1) {
                        System.out.println("Piece is not a king");
                        return false;
                    }
                    else{
                        System.out.println("Piece is not a king");
                        return false;
                    }

                } else if (!board.getCell(fromX, fromY).piece.isWhite()) { // black move back
                    if (toX == fromX + 1 || toX == fromX - 1) return true;
                    else if (toY != fromY - 1){
                        System.out.println("Piece is not a king");
                        return false;}
                    else{
                        System.out.println("Piece is not a king");
                        return false;}
                }
            }
        } else if (board.getCell(fromX, fromY) == null) {
            System.out.println("Not a valid piece");
            return false;
        } else if (board.getCell(toX, toY).getPiece() != null) {
            System.out.println("Can not land over a piece");
            return false;
        } else return true;

        return true;
    }

    boolean checkValidAutomatic(int fromX, int fromY, int toX, int toY, boolean capture){ // no text print,
        if(toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) // out of bounds
            return false;

        if(board.getCell(fromX,fromY).getPiece() != null) { // check if side is valid
            if (board.isWhiteSide() != board.getCell(fromX, fromY).piece.isWhite()) return false;

            if(capture) {
                if (board.getCell(toX, toY).getPiece() != null) {

                    // same side
                    if (board.getCell(fromX, fromY).getPiece().isKing()) { // king
                        int tempToX, tempToY, tempFromX, tempFromY;
                        tempToX=toX;
                        tempToY=toY;
                        tempFromX=fromX;
                        tempFromY=fromY;
                        if (toX > fromX && toY < fromY) { // up right
                            while(tempToX!=tempFromX || tempToY!=tempFromY){
                                tempToX--;
                                tempToY++;
                                if(tempToX < 0 || tempToY > 7)
                                    return false;
                                else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                                    return false;
                            }
                            return true;
                        } else if (toX < fromX && toY < fromY) { // up left
                            while(tempToX!=tempFromX || tempToY!=tempFromY){
                                tempToX++;
                                tempToY++;
                                if(tempToX > 7 || tempToY > 7)
                                    return false;
                                else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                                    return false;
                            }
                            return true;
                        } else if (toX < fromX && toY > fromY) { // down left
                            while(tempToX!=tempFromX || tempToY!=tempFromY){
                                tempToX++;
                                tempToY--;
                                if(tempToX > 7 || tempToY < 0)
                                    return false;
                                else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                                    return false;
                            }
                            return true;
                        } else if (toX > fromX && toY > fromY) { // down right
                            while(tempToX!=tempFromX || tempToY!=tempFromY){
                                tempToX--;
                                tempToY--;
                                if(tempToX < 0 || tempToY < 0)
                                    return false;
                                else if(board.getCell(tempToX,tempToY).getPiece()!=null)
                                    return false;
                            }
                            return true;
                        }
                    }
                    return board.getCell(fromX, fromY).getPiece().isWhite() != board.getCell(toX, toY).getPiece().isWhite();
                }
            }
            else{
                return board.getCell(toX, toY).getPiece() == null;
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
        for (searchI = 0; searchI < 8; searchI++) {
            for (searchY = 0; searchY < 8; searchY++) {
                // check up right
                if(!(searchI + 1 >= 8 || searchY - 1 < 0)){
                    if(board.getCell(searchI+1,searchY-1).getPiece()!=null)
                        capture(searchI,searchY,searchI+2, searchY-2,searchI+1,searchY-1);
                }

                // check up left
                if(!(searchI-1 < 0 || searchY-1 < 0)){
                    if(board.getCell(searchI-1,searchY-1).getPiece()!=null)
                        capture(searchI,searchY,searchI-2, searchY-2,searchI-1,searchY-1);
                    }
                // check down left
                if(!(searchI-1 <0 || searchY+1 >= 8)) {
                    if (board.getCell(searchI - 1, searchY + 1).getPiece() != null) capture(searchI,searchY,searchI - 2, searchY + 2,searchI-1,searchY+1);
                }
                //check down right
                if(!(searchI+1 >= 8 || searchY+1 >= 8)) {
                    if (board.getCell(searchI + 1, searchY + 1).getPiece() != null) capture(searchI,searchY,searchI + 2, searchY + 2,searchI+1,searchY+1);
                }
            }
        }
    }

    void capture(int fromX, int fromY, int toX, int toY, int capturedX, int capturedY){ // missing check if has piece near and capture
        if(checkValidAutomatic(fromX,fromY,capturedX,capturedY, true)) { // originally toX toY
            if(checkValidAutomatic(fromX,fromY,toX,toY, false)) { // originally wala to
                System.out.println("in");
                Piece temp = board.getCell(fromX, fromY).getPiece();
                System.out.println(temp.getSymbol());
                board.getCell(fromX, fromY).setPiece(null);
                board.getCell(toX, toY).setPiece(temp);
                board.setWhiteSide(!board.isWhiteSide());

                if(board.getCell(capturedX,capturedY).getPiece().isKing()){
                    if(board.getCell(capturedX,capturedY).getPiece().isWhite())
                    Board.whiteKingCount--;
                else
                    Board.blackCount--;
                }
                else{
                    if(board.getCell(capturedX,capturedY).getPiece().isWhite())
                        Board.whiteCount--;
                    else
                        Board.blackCount--;
                }


                board.getCell(capturedX, capturedY).setPiece(null);
                searchI=0;
                searchY=0;
                board.showBoard();
            }
        }
    }

    void convertToKing() {
        for (int i = 0; i < 8; i++) {
            if (board.getCell(i, 0).getPiece() != null) {
                if (!board.getCell(i, 0).getPiece().isWhite()){
                    Board.blackCount--;
                    Board.blackKingCount++;
                    board.getCell(0, i).getPiece().setKing(true);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if (board.getCell(i, 7).getPiece() != null) {
                if (board.getCell(i, 7).getPiece().isWhite()){
                    Board.whiteCount--;
                    Board.whiteKingCount++;
                    board.getCell(i, 0).getPiece().setKing(true);
                }
            }
        }
    }

    void aiMove(){
    }
}
