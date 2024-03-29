public class Board {
    static int whiteCount=12;
    static int blackCount=12;
    static int whiteKingCount=0;
    static int blackKingCount=0;
    Cell[][] block = new Cell[8][8];
    boolean whiteSide = true;
    double heuristicVal=0; // - favor black; + favor white

    void setWhiteSide(boolean white){
        this.whiteSide=white;
    }

    void createBoard(){
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++){
                block[i][y] = new Cell();
                block[i][y].setCoord(i,y);
                if(y%2==1){
                    if(i==0 || i==2){
                        Piece temp = new Piece(true, false, "w");
                        block[i][y].setPiece(temp);
                    }
                }
                else {
                    if(i==1){
                        Piece temp = new Piece(true, false, "w");
                        block[i][y].setPiece(temp);
                    }
                }

                if(y % 2 == 0) {
                    if (i == 5 || i == 7) {
                        Piece temp = new Piece(false, false, "b");
                        block[i][y].setPiece(temp);
                    }
                }
                else {
                    if (i==6) {
                        Piece temp = new Piece(false, false, "b");
                        block[i][y].setPiece(temp);
                    }
                }
            }
        }
    }

    void showBoard(){
        int vert=0;
        int hor=0;
        System.out.print("  ");
        for(int i=0; i<8; i++) {
            System.out.print(hor + " ");
            hor++;
        }
        System.out.println();
        for(int i=0; i<8; i++){
            System.out.print(vert + " ");
            vert++;
            for(int y=0; y<8; y++) {
                if (block[i][y].getPiece() != null)
                    System.out.print(block[i][y].getPiece().getSymbol());
                else System.out.print("+");
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    boolean over(){
        if(whiteCount == 0 || blackCount == 0){
            if(whiteCount==0)
                System.out.println("Black win!");
            else
                System.out.println("White win!");

            return true;
        }
        return false;
    }
    boolean isWhiteSide(){
        return whiteSide;
    }

    Cell getCell(int x, int y){
        return block[y][x];
    }

    int calcHeuristic(){
        int pieceValue = whiteCount-blackCount;
        int kingValue = (whiteKingCount-blackKingCount)*3;
        // get number of valid moves
        return pieceValue+kingValue;
    }
}
