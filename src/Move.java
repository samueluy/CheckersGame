import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Move {
    static int searchI = 0;
    static int searchY = 0;
    Board board = new Board();

    /*
    return 0 = Invalid move; 1 = Valid move-no capture; 2 = Valid move-capture
     */
    int move(Board inBoard, int fromX, int fromY, int toX, int toY, ArrayList<String> listOfMoves) {
        boolean flag = false;
        boolean captured = false;
        for(int x=0; x< listOfMoves.size(); x++){
            if(fromX == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(0))) &&
                fromY == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(1))) &&
                toX == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(2))) &&
                toY== Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(3)))){
                    Piece temp = inBoard.getCell(fromX, fromY).getPiece();
                    inBoard.getCell(fromX, fromY).setPiece(null);
                    inBoard.getCell(toX, toY).setPiece(temp);
                    inBoard.setWhiteSide(!inBoard.isWhiteSide());
                    flag=true;
            }
        }
        if(!flag){
            System.out.println("Invalid Move");
            return 0;
        }
        else{
            inBoard.convertToKing(inBoard);
            if(checkAndCapture(inBoard))
                return 2;
            return 1;
        }
    }

    ArrayList<String> generateValidMoves(Board inBoard) { // generate for king
        ArrayList<String> currentList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                if (checkValidAutomatic(inBoard, i, y, i + 1, y - 1, false)) // up right
                    currentList.add(String.valueOf(i) + String.valueOf(y) + String.valueOf(i + 1) + String.valueOf(y - 1));
                if (checkValidAutomatic(inBoard, i, y, i - 1, y - 1, false))// up left
                    currentList.add(String.valueOf(i) + String.valueOf(y) + String.valueOf(i - 1) + String.valueOf(y - 1));
                if (checkValidAutomatic(inBoard, i, y, i - 1, y + 1, false))// down left
                    currentList.add(String.valueOf(i) + String.valueOf(y) + String.valueOf(i - 1) + String.valueOf(y + 1));
                if (checkValidAutomatic(inBoard, i, y, i + 1, y + 1, false))// down right
                    currentList.add(String.valueOf(i) + String.valueOf(y) + String.valueOf(i + 1) + String.valueOf(y + 1));
            }
        }
        return currentList;
    }

    boolean checkValid(Board inBoard, int fromX, int fromY, int toX, int toY) {
        if (toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) { // out of bounds
            return false;
        }

        if (inBoard.getCell(fromX, fromY).getPiece() != null) {
            if (inBoard.isWhiteSide() != inBoard.getCell(fromX, fromY).piece.isWhite()) { // moving different side
                return false;
            } else if (inBoard.getCell(fromX, fromY).getPiece().isKing()) { // king
                int tempToX, tempToY, tempFromX, tempFromY;
                tempToX = toX;
                tempToY = toY;
                tempFromX = fromX;
                tempFromY = fromY;
                if (toX > fromX && toY < fromY) { // up right
                    while (tempToX != tempFromX || tempToY != tempFromY) {
                        tempToX--;
                        tempToY++;
                        if (tempToX < 0 || tempToY > 7) return false;
                        else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                    }
                    return true;
                } else if (toX < fromX && toY < fromY) { // up left
                    while (tempToX != tempFromX || tempToY != tempFromY) {
                        tempToX++;
                        tempToY++;
                        if (tempToX > 7 || tempToY > 7) return false;
                        else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                    }
                    return true;
                } else if (toX < fromX && toY > fromY) { // down left
                    while (tempToX != tempFromX || tempToY != tempFromY) {
                        tempToX++;
                        tempToY--;
                        if (tempToX > 7 || tempToY < 0) return false;
                        else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                    }
                    return true;
                } else if (toX > fromX && toY > fromY) { // down right
                    while (tempToX != tempFromX || tempToY != tempFromY) {
                        tempToX--;
                        tempToY--;
                        if (tempToX < 0 || tempToY < 0) return false;
                        else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                    }
                    return true;
                }
            } else {
                if (inBoard.getCell(fromX, fromY).piece.isWhite()) { // white move back
                    if (toX == fromX + 1 || toX == fromX - 1) return true;
                    else if (toY != fromY + 1) {
                        return false;
                    } else {
                        return false;
                    }

                } else if (!inBoard.getCell(fromX, fromY).piece.isWhite()) { // black move back
                    if (toX == fromX + 1 || toX == fromX - 1) return true;
                    else if (toY != fromY - 1) {
                        return false;
                    } else {
                        return false;
                    }
                }
            }
        } else if (inBoard.getCell(fromX, fromY) == null) {
            return false;
        } else if (inBoard.getCell(toX, toY).getPiece() != null) {
            return false;
        } else return true;

        return true;
    }

    boolean checkValidAutomatic(Board inBoard, int fromX, int fromY, int toX, int toY, boolean capture) { // no text print,
        if (toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) // out of bounds
            return false;

        if (inBoard.getCell(fromX, fromY).getPiece() != null) { // check if side is valid
            if (inBoard.isWhiteSide() != inBoard.getCell(fromX, fromY).piece.isWhite()) return false;

            if (capture) {
                if (inBoard.getCell(toX, toY).getPiece() != null) {
                    // same side
                    if (inBoard.getCell(fromX, fromY).getPiece().isKing()) { // king
                        int tempToX, tempToY, tempFromX, tempFromY;
                        tempToX = toX;
                        tempToY = toY;
                        tempFromX = fromX;
                        tempFromY = fromY;
                        if (toX > fromX && toY < fromY) { // up right
                            while (tempToX != tempFromX || tempToY != tempFromY) {
                                tempToX--;
                                tempToY++;
                                if (tempToX < 0 || tempToY > 7) return false;
                                else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                            }
                            return true;
                        } else if (toX < fromX && toY < fromY) { // up left
                            while (tempToX != tempFromX || tempToY != tempFromY) {
                                tempToX++;
                                tempToY++;
                                if (tempToX > 7 || tempToY > 7) return false;
                                else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                            }
                            return true;
                        } else if (toX < fromX && toY > fromY) { // down left
                            while (tempToX != tempFromX || tempToY != tempFromY) {
                                tempToX++;
                                tempToY--;
                                if (tempToX > 7 || tempToY < 0) return false;
                                else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                            }
                            return true;
                        } else if (toX > fromX && toY > fromY) { // down right
                            while (tempToX != tempFromX || tempToY != tempFromY) {
                                tempToX--;
                                tempToY--;
                                if (tempToX < 0 || tempToY < 0) return false;
                                else if (inBoard.getCell(tempToX, tempToY).getPiece() != null) return false;
                            }
                            return true;
                        }
                    }
                    return inBoard.getCell(fromX, fromY).getPiece().isWhite() != inBoard.getCell(toX, toY).getPiece().isWhite();
                }
            } else {
                return inBoard.getCell(toX, toY).getPiece() == null;
            }
        } else return false;

        return true;
    }

    boolean checkAndCapture(Board inBoard) { // checks for available captures and if so, capture
        boolean captured = false;
        for (searchI = 0; searchI < 8; searchI++) {
            for (searchY = 0; searchY < 8; searchY++) {
                // check up right
                if (!(searchI + 1 >= 8 || searchY - 1 < 0)) {
                    if (inBoard.getCell(searchI + 1, searchY - 1).getPiece() != null)
                        if (capture(inBoard, searchI, searchY, searchI + 2, searchY - 2, searchI + 1, searchY - 1))
                            captured = true;
                }

                // check up left
                if (!(searchI - 1 < 0 || searchY - 1 < 0)) {
                    if (inBoard.getCell(searchI - 1, searchY - 1).getPiece() != null)
                        if (capture(inBoard, searchI, searchY, searchI - 2, searchY - 2, searchI - 1, searchY - 1))
                            captured = true;
                }
                // check down left
                if (!(searchI - 1 < 0 || searchY + 1 >= 8)) {
                    if (inBoard.getCell(searchI - 1, searchY + 1).getPiece() != null)
                        if (capture(inBoard, searchI, searchY, searchI - 2, searchY + 2, searchI - 1, searchY + 1))
                            captured = true;
                }
                //check down right
                if (!(searchI + 1 >= 8 || searchY + 1 >= 8)) {
                    if (inBoard.getCell(searchI + 1, searchY + 1).getPiece() != null)
                        if (capture(inBoard, searchI, searchY, searchI + 2, searchY + 2, searchI + 1, searchY + 1))
                            captured = true;
                }
            }
        }

        return captured;
    }

    boolean capture(Board inBoard, int fromX, int fromY, int toX, int toY, int capturedX, int capturedY) { // missing check if has piece near and capture
        if (checkValidAutomatic(inBoard, fromX, fromY, capturedX, capturedY, true)) { // originally toX toY
            if (checkValidAutomatic(inBoard, fromX, fromY, toX, toY, false)) { // originally wala to
                Piece temp = inBoard.getCell(fromX, fromY).getPiece();
                inBoard.getCell(fromX, fromY).setPiece(null);
                inBoard.getCell(toX, toY).setPiece(temp);
                inBoard.setWhiteSide(!inBoard.isWhiteSide());

                if (inBoard.getCell(capturedX, capturedY).getPiece().isKing()) {
                    if (inBoard.getCell(capturedX, capturedY).getPiece().isWhite())
                        inBoard.setWhiteKingCount(inBoard.getWhiteKingCount() - 1);
                    else inBoard.setBlackCount(inBoard.getBlackCount() - 1);
                } else {
                    if (inBoard.getCell(capturedX, capturedY).getPiece().isWhite())
                        inBoard.setWhiteCount(inBoard.getWhiteCount() - 1);
                    else inBoard.setBlackCount(inBoard.getBlackCount() - 1);
                }
                inBoard.getCell(capturedX, capturedY).setPiece(null);
                searchI = 0;
                searchY = 0;
                return true;
            }
            return true;
        }
        return false;
    }

    /*
    generate all possible moves for current board
    create a temporary board for each move, left to right
    generate all possible moves for that board
    create a temporary board for each move, left to right
    calculate heuristic score
    store the value of the best move, down from first
    */

    void miniMaxTemp(Board current) {
        Node tree = new Node();
        ArrayList<String> possibleMoves = new ArrayList<>();
        possibleMoves = generateValidMoves(current); // parent
        moveAI(tree, possibleMoves, false, true); // depth 2 black

        for (int i = 0; i < tree.children.size(); i++) {
            moveAI(tree.children.get(i), tree.children.get(i).list, true, false); // depth 3 white (assume best move for white)
        }

        List<List<Cell>> bestMove;
        bestMove = tree.children.get(0).newLayout; // first move by default
        int curHeuristic = tree.children.get(0).value; // first heuristic value by default
        for (int i = 0; i < tree.children.size(); i++) {
            if (tree.children.get(i).value < curHeuristic) bestMove = tree.children.get(i).newLayout;
        }
        board.setBlock(bestMove);
        //board.showBoard();
        board.setWhiteSide(!board.isWhiteSide());
    }

    // Driver
    void miniMaxMove(Board current){
        Node MAX = new Node(1000);
        Node MIN = new Node(-1000);
        Node tree = new Node();
        ArrayList<String> possibleMoves;
        possibleMoves = generateValidMoves(current);
        moveAI(tree, possibleMoves, false, true); // depth 2

        Node bestMove = miniMaxAlgorithm(0, false, tree, MIN, MAX);

        board.setBlock(bestMove.newLayout);
        board.setWhiteSide(!board.isWhiteSide());
    }

    Node miniMaxAlgorithm(int depth, Boolean maximizingPlayer, Node current, Node alpha, Node beta){
        Node MAX = new Node(1000);
        Node MIN = new Node(-1000);
        Board tempBoard = new Board();
        List<List<Cell>> temp = new ArrayList<List<Cell>>();

        // create copy of current board
        for (int i = 0; i < 8; i++) {
            temp.add(new ArrayList<Cell>());
            for (int y = 0; y < 8; y++) {
                temp.get(i).add(new Cell(board.getCell(y, i)));
            }
        }

        //tempBoard.setBlock(temp);
        tempBoard.setWhiteSide(!tempBoard.isWhiteSide());

        if(depth == 3)
             return current;

        if(maximizingPlayer) {
            Node best = MIN;
            for (int i = 0; i < current.children.size(); i++) {
                tempBoard.setBlock(current.children.get(i).newLayout);
                //moveAI(current.children.get(i), current.children.get(i).list, true, true);

                Node val = miniMaxAlgorithm(depth+1, false, current.children.get(i), alpha, beta);

                //best = Math.max(best,val);
                if(best.value < tempBoard.calcHeuristic())
                    best = current.children.get(i);

                //alpha = Math.max(alpha,best);
                if(alpha.value < best.value)
                    alpha = best;

                if(beta.value <= alpha.value)
                    break;
            }
            return best;
        }
        else {
            Node best = MAX;

            for (int i = 0; i < current.children.size(); i++) {
                tempBoard.setBlock(current.children.get(i).newLayout);
                //moveAI(current.children.get(i), current.children.get(i).list, false, true);
                Node val = miniMaxAlgorithm(depth + 1, true, current.children.get(i), alpha, beta);

                //best = Math.min(best,val);
                if(best.value > tempBoard.calcHeuristic())
                    best = current.children.get(i);

                //alpha = Math.min(alpha,best);
                if(alpha.value > best.value)
                    alpha = best;

                if (beta.value <= alpha.value) break;
            }
            return best;
        }
    }

    void moveAI(Node tree, ArrayList<String> listOfMoves, boolean white, boolean first) {
        int curHeuristic;
        List<ArrayList<String>> nextValid = new ArrayList<>();
        List<List<Cell>> newLayout = new ArrayList<List<Cell>>();
        for (int x = 0; x < listOfMoves.size(); x++) {
            Board tempBoard = new Board();
            List<List<Cell>> temp = new ArrayList<List<Cell>>();

            // create copy of current board
            for (int i = 0; i < 8; i++) {
                temp.add(new ArrayList<Cell>());
                for (int y = 0; y < 8; y++) {
                    temp.get(i).add(new Cell(board.getCell(y, i)));
                }
            }

            tempBoard.setBlock(temp);
            tempBoard.setWhiteSide(!tempBoard.isWhiteSide());

            int fromX = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(0)));
            int fromY = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(1)));
            int toX = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(2)));
            int toY = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(3)));

            if (move(tempBoard, fromX, fromY, toX, toY, listOfMoves)==2) { // a capture is found
                nextValid.add(generateValidMoves(tempBoard)); // generate valid moves for next layer
                curHeuristic = tempBoard.calcHeuristic();
                tree.addChild(new Node(curHeuristic, nextValid.get(x), temp));
            } else {
                nextValid.add(generateValidMoves(tempBoard)); // generate valid moves for next layer
                curHeuristic = tempBoard.calcHeuristic();
                tree.addChild(new Node(curHeuristic, nextValid.get(x), temp));
            }
        }
    }
}

