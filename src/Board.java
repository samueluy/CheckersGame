public class Board {
    Cell[][] block = new Cell[8][8];

    void createBoard(){
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++){
                block[i][y] = new Cell();
                block[i][y].setCoord(i,y);
                if(y%2==0 && i%2==0){
                    if(i==0 || i==2){
                        Piece temp = new Piece(true, false, "w");
                        block[i][y].setPiece(temp);
                    }
                }
                else if(y%2==1 && i%2==1){
                    if(i==1){
                        Piece temp = new Piece(true, false, "w");
                        block[i][y].setPiece(temp);
                    }
                }
                else if(y % 2 == 0) {
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
    }

    Cell getCell(int x, int y){
        return block[y][x];
    }
}
