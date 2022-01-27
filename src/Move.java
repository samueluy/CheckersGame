// Supports all the player and AI moves as well as the MiniMax Algorithm

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Move {
    private static int searchI = 0; // x Coordinate counter for capture
    private static int searchY = 0; // y Coordinate counter for capture
    private static int depth1 = 0; // Node counter for depth 1
    private static int depth2 = 0; // Node counter for depth 2
    private static int depth3 = 0; // Node counter for depth 3
    private static int visitCount = 0;
    private final Map<Integer, Integer> branchCount = new HashMap<Integer, Integer>();
    private final Board board = new Board();

    /**
     * @param inBoard     : Current board working on
     * @param fromX       : Starting coordinate X
     * @param fromY       : Starting coordinate Y
     * @param toX         : Destination coordinate X
     * @param toY         : Destination coordinate Y
     * @param listOfMoves : List of possible moves
     * @return 0 = Invalid move; 1 = Valid move-no capture; 2 = Valid move-capture
     * @brief Executes a player move. Changes the coordinate of a piece to a valid coordinate.
     */
    public int move(Board inBoard, int fromX, int fromY, int toX, int toY, ArrayList<String> listOfMoves) {
        boolean flag = false;
        for (int x = 0; x < listOfMoves.size(); x++) {
            if (fromX == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(0))) && fromY == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(1))) && toX == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(2))) && toY == Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(3)))) {
                Piece temp = inBoard.getCell(fromX, fromY).getPiece();
                inBoard.getCell(fromX, fromY).setPiece(null);
                inBoard.getCell(toX, toY).setPiece(temp);
                inBoard.setWhiteSide(!inBoard.isWhiteSide());
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Invalid Move");
            return 0;
        } else {
            inBoard.convertToKing(inBoard);
            if (checkAndCapture(inBoard)) return 2;
            return 1;
        }
    }

    /**
     * @param inBoard : Current board working on
     * @return ArrayList of valid moves
     * @brief Generate valid moves given the current side and position of the board
     */
    public ArrayList<String> generateValidMoves(Board inBoard) {
        ArrayList<String> currentList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                if (checkValidAutomatic(inBoard, i, y, i + 1, y - 1, false) && !inBoard.isWhiteSide()) // up right
                    currentList.add(String.valueOf(i) + y + (i + 1) + (y - 1));
                if (checkValidAutomatic(inBoard, i, y, i - 1, y - 1, false) && !inBoard.isWhiteSide())// up left
                    currentList.add(String.valueOf(i) + y + (i - 1) + (y - 1));
                if (checkValidAutomatic(inBoard, i, y, i - 1, y + 1, false) && inBoard.isWhiteSide())// down left
                    currentList.add(String.valueOf(i) + y + (i - 1) + (y + 1));
                if (checkValidAutomatic(inBoard, i, y, i + 1, y + 1, false) && inBoard.isWhiteSide())// down right
                    currentList.add(String.valueOf(i) + y + (i + 1) + (y + 1));
            }
        }
        generateValidMovesKing(inBoard, currentList);

        return currentList;
    }

    /**
     * @param inBoard       : Current board working on
     * @param possibleMoves : List of current possible moves
     * @brief Adds more moves to the valid move list for king pieces
     */
    public void generateValidMovesKing(Board inBoard, ArrayList<String> possibleMoves) {
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                if (inBoard.getCell(i, y).getPiece() != null) {
                    if (inBoard.getCell(i, y).getPiece().isKing()) {
                        if (checkValidAutomatic(inBoard, i, y, i + 1, y - 1, false) && inBoard.isWhiteSide()) // up right
                            possibleMoves.add(String.valueOf(i) + y + (i + 1) + (y - 1));
                        if (checkValidAutomatic(inBoard, i, y, i - 1, y - 1, false) && inBoard.isWhiteSide())// up left
                            possibleMoves.add(String.valueOf(i) + y + (i - 1) + (y - 1));
                        if (checkValidAutomatic(inBoard, i, y, i - 1, y + 1, false) && !inBoard.isWhiteSide())// down left
                            possibleMoves.add(String.valueOf(i) + y + (i - 1) + (y + 1));
                        if (checkValidAutomatic(inBoard, i, y, i + 1, y + 1, false) && !inBoard.isWhiteSide())// down right
                            possibleMoves.add(String.valueOf(i) + y + (i + 1) + (y + 1));
                    }
                }
            }
        }
    }

    /**
     * @param inBoard : Current board working on
     * @param fromX   : Starting coordinate X
     * @param fromY   : Starting coordinate Y
     * @param toX     : Destination coordinate X
     * @param toY     : Destination coordinate Y
     * @param capture : If the move is a capture
     * @return if the move is valid
     * @brief Checks if a move is valid
     */
    public boolean checkValidAutomatic(Board inBoard, int fromX, int fromY, int toX, int toY, boolean capture) {
        if (toX >= 8 || toY >= 8 || fromX >= 8 || fromY >= 8 || toX < 0 || toY < 0 || fromX < 0 || fromY < 0) // out of bounds
            return false;

        if (inBoard.getCell(fromX, fromY).getPiece() != null) { // check if side is valid
            if (inBoard.isWhiteSide() != inBoard.getCell(fromX, fromY).getPiece().isWhite()) return false;

            if (capture) {
                if (inBoard.getCell(toX, toY).getPiece() != null) {
                    return inBoard.getCell(fromX, fromY).getPiece().isWhite() != inBoard.getCell(toX, toY).getPiece().isWhite();
                }
            } else {
                return inBoard.getCell(toX, toY).getPiece() == null;
            }
        } else return false;

        return true;
    }

    /**
     * @param inBoard : Current board working on
     * @return if a capture is made
     * @brief Checks if there is an available capture and if so, execute the move
     */
    public boolean checkAndCapture(Board inBoard) {
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

    /**
     * @param inBoard   : Current board working on
     * @param fromX     : Starting coordinate X
     * @param fromY     : Starting coordinate Y
     * @param toX       : Destination coordinate X
     * @param toY       : Destination coordinate Y
     * @param capturedX : Captured piece coordinate X
     * @param capturedY : Captured piece coordinate Y
     * @return if the move is valid
     * @brief Captures a piece
     */
    public boolean capture(Board inBoard, int fromX, int fromY, int toX, int toY, int capturedX, int capturedY) {
        if (checkValidAutomatic(inBoard, fromX, fromY, capturedX, capturedY, true)) {
            if (checkValidAutomatic(inBoard, fromX, fromY, toX, toY, false)) {
                Piece temp = inBoard.getCell(fromX, fromY).getPiece();
                inBoard.getCell(fromX, fromY).setPiece(null);
                inBoard.getCell(toX, toY).setPiece(temp);

                if (inBoard.getCell(capturedX, capturedY).getPiece().isKing()) {
                    if (inBoard.getCell(capturedX, capturedY).getPiece().isWhite())
                        inBoard.setWhiteKingCount(inBoard.getWhiteKingCount() - 1);
                    else inBoard.setBlackCount(inBoard.getBlackKingCount() - 1);
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

    /**
     * @param current : Current board working on
     * @brief Driver code for the miniMax algorithm
     */
    public void miniMaxMove(Board current) {
        branchCount.put(1, 0); // Initialize hashmap of depth 1
        branchCount.put(2, 0); // Initialize hashmap of depth 2
        branchCount.put(3, 0); // Initialize hashmap of depth 3
        int averageBranchingFactor;
        Node MAX = new Node(1000);
        Node MIN = new Node(-1000);
        Node tree = new Node();
        ArrayList<String> possibleMoves;
        possibleMoves = generateValidMoves(current);
        moveAI(tree, possibleMoves, false, true);

        long start = System.nanoTime(); // Start runtime timer
        Node bestMove = miniMaxAlgorithm(0, false, tree, MIN, MAX);
        long end = System.nanoTime(); // End runtime timer

        int n1 = branchCount.get(1) / depth1;
        int n2 = branchCount.get(2) / depth2;
        int n3 = branchCount.get(3) / depth3;
        averageBranchingFactor = (n1 + n2 + n3) / 3;
        int value = visitCount;
        System.out.println("Average branching factor: " + averageBranchingFactor);
        System.out.println("Effective branching factor: " + Math.round(((value)) * 10.0) / 10.0);
        visitCount = 0;
        depth1 = 0;
        depth2 = 0;
        depth3 = 0;

        board.setBlock(bestMove.getNewLayout());
        if (!board.isWhiteSide()) // to check if there were captures
            board.setWhiteSide(!board.isWhiteSide());

        long time = end - start;
        System.out.println("Execution time: " + time + " nanoseconds");

        branchCount.clear(); // Empty the current values of the hashmap
    }

    /**
     * @param depth            : Current depth from origin
     * @param maximizingPlayer : If the current player is the maximizer (white) or not (black)
     * @param current          : Parent node
     * @param alpha            : Alpha value
     * @param beta             : Beta value
     * @return a new node
     * @brief Recursive minimax alpha beta pruning method.
     */
    public Node miniMaxAlgorithm(int depth, Boolean maximizingPlayer, Node current, Node alpha, Node beta) {
        Node MAX = new Node(1000); // Default value
        Node MIN = new Node(-1000); // Default value
        Board tempBoard = new Board();
        List<List<Cell>> temp = new ArrayList<List<Cell>>();

        // create copy of current board
        for (int i = 0; i < 8; i++) {
            temp.add(new ArrayList<>());
            for (int y = 0; y < 8; y++) {
                temp.get(i).add(new Cell(board.getCell(y, i)));
            }
        }

        tempBoard.setWhiteSide(!tempBoard.isWhiteSide());
        tempBoard.setBlock(temp);

        // Create children for current node
        moveAI(current, generateValidMoves(tempBoard), tempBoard.isWhiteSide(), false);

        // Get branching factor
        switch (depth) {
            case 1:
                branchCount.put(1, branchCount.get(1) + current.getChildren().size());
                depth1++;
            case 2:
                branchCount.put(2, branchCount.get(2) + current.getChildren().size());
                depth2++;
            case 3:
                branchCount.put(3, branchCount.get(3) + current.getChildren().size());
                depth3++;
        }

        // Move Ordering -> sort by heuristic value
        if (maximizingPlayer) current.getChildren().sort((n1, n2) -> n2.getValue() - n1.getValue()); // descending order
        else current.getChildren().sort((n1, n2) -> n1.getValue() - n2.getValue()); // ascending order

        // Terminating value. Ends at depth 3
        if (depth == 3) return current;

        Node best;
        if (maximizingPlayer) {
            best = MIN;
            for (int i = 0; i < current.getChildren().size(); i++) {
                Node val = miniMaxAlgorithm(depth + 1, false, current.getChildren().get(i), alpha, beta);
                tempBoard.setBlock(val.getNewLayout());

                // Set the current best value to the larger value
                if (best.getValue() < tempBoard.calcHeuristic(generateValidMoves(tempBoard)))
                    best = current.getChildren().get(i);
                else best = new Node(val);

                if (alpha.getValue() < best.getValue()) {
                    visitCount++;
                    alpha = best;
                }

                // Terminate function / prune
                if (beta.getValue() <= alpha.getValue()) break;

            }
        } else {
            best = MAX;
            for (int i = 0; i < current.getChildren().size(); i++) {
                Node val = miniMaxAlgorithm(depth + 1, true, current.getChildren().get(i), alpha, beta);
                tempBoard.setBlock(val.getNewLayout());

                // Set the current best value to the smaller value
                if (best.getValue() > tempBoard.calcHeuristic(generateValidMoves(tempBoard)))
                    best = current.getChildren().get(i);
                else best = new Node(val);

                if (beta.getValue() > best.getValue()) {
                    visitCount++;
                    beta = best;
                }

                // Terminate function / prune
                if (beta.getValue() <= alpha.getValue()) break;
            }
            // Return the best move
        }
        return best;
    }

    /**
     * @param tree        : Parent node
     * @param listOfMoves : List of possible moves
     * @param white       : If the current player is white
     * @param first       : If called for the first time
     * @brief Execute move simulation and create children for current node
     */
    public void moveAI(Node tree, ArrayList<String> listOfMoves, boolean white, boolean first) {
        int curHeuristic;
        List<ArrayList<String>> nextValid = new ArrayList<>();
        for (int x = 0; x < listOfMoves.size(); x++) {
            Board tempBoard = new Board();
            List<List<Cell>> temp = new ArrayList<>();

            // create copy of current board
            for (int i = 0; i < 8; i++) {
                temp.add(new ArrayList<>());
                for (int y = 0; y < 8; y++) {
                    temp.get(i).add(new Cell(board.getCell(y, i)));
                }
            }

            tempBoard.setBlock(temp);
            //tempBoard.setWhiteSide(!tempBoard.isWhiteSide());

            int fromX = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(0)));
            int fromY = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(1)));
            int toX = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(2)));
            int toY = Integer.parseInt(String.valueOf(listOfMoves.get(x).charAt(3)));

            if (move(tempBoard, fromX, fromY, toX, toY, listOfMoves) == 2) { // a capture is found
                nextValid.add(generateValidMoves(tempBoard)); // generate valid moves for next layer
                curHeuristic = tempBoard.calcHeuristic(nextValid.get(nextValid.size() - 1));
                tree.addChild(new Node(curHeuristic, nextValid.get(x), temp));
            } else {
                nextValid.add(generateValidMoves(tempBoard)); // generate valid moves for next layer
                curHeuristic = tempBoard.calcHeuristic(nextValid.get(nextValid.size() - 1));
                tree.addChild(new Node(curHeuristic, nextValid.get(x), temp));
            }
        }
    }

    /**
     * @return Current Board
     * @brief Gets board of the current object
     */
    public Board getBoard() {
        return board;
    }
}

