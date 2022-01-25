import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable {
    private int whiteCount = 12;
    private int blackCount = 12;
    private int whiteKingCount = 0;
    private int blackKingCount = 0;
    List<List<Cell>> block = new ArrayList<List<Cell>>();
    private boolean whiteSide = true;


    public Board() {
    }

    public Board(Board inBoard) {
        this.setBlock(inBoard.getBlock());
        this.setBlackKingCount(inBoard.getBlackKingCount());
        this.setWhiteKingCount(inBoard.getWhiteKingCount());
        this.setBlackCount(inBoard.getBlackCount());
        this.setWhiteCount(inBoard.getWhiteCount());
        this.setWhiteSide(inBoard.isWhiteSide());
    }

    List<List<Cell>> getBlock() {
        return block;
    }

    void setWhiteSide(boolean white) {
        this.whiteSide = white;
    }

    void setBlock(List<List<Cell>> block) {
        this.block = block;
    }

    void setWhiteCount(int n) {
        this.whiteCount = n;
    }

    void setBlackCount(int n) {
        this.blackCount = n;
    }

    void setWhiteKingCount(int n) {
        this.whiteKingCount = n;
    }

    void setBlackKingCount(int n) {
        this.blackKingCount = n;
    }

    int getWhiteCount() {
        return whiteCount;
    }

    int getBlackCount() {
        return blackCount;
    }

    int getWhiteKingCount() {
        return whiteKingCount;
    }

    int getBlackKingCount() {
        return blackKingCount;
    }

    void createBoard() {
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

    void showBoard() {
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

    boolean over() {
        if (whiteCount == 0 || blackCount == 0) {
            if (whiteCount == 0) System.out.println("Black win!");
            else System.out.println("White win!");

            return true;
        }
        return false;
    }

    boolean isWhiteSide() {
        return whiteSide;
    }

    Cell getCell(int x, int y) {
        return block.get(y).get(x);
    }

    int calcHeuristic(ArrayList<String> moves) {
        int pieceValue = whiteCount*3 - blackCount*3;
        int kingValue = (whiteKingCount*5 - blackKingCount*5);
        int nMoves = moves.size();
        return pieceValue + kingValue + nMoves;
    }

    List<List<Cell>> clone(Board original) {
        List<List<Cell>> temp = new ArrayList<List<Cell>>();

        for (int i = 0; i < 8; i++) {
            temp.add(new ArrayList<Cell>());
            for (int y = 0; y < 8; y++) {
                temp.get(i).add(new Cell(original.getCell(y, i)));
            }
        }

        return temp;
    }

    void convertToKing(Board inBoard) {
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
