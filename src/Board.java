import java.awt.*;

public class Board {
    Cell[][] block = new Cell[8][8];


    void createBoard(){
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++){
                block[i][y] = new Cell();
                block[i][y].setCoord(i,y);
                if(y%2==0 && i%2==0){
                    if(i==0 || i==2){
                        block[i][y].setWhite(true);
                        block[i][y].setSymbol("w");
                    }
                    else
                        block[i][y].setSymbol("+");
                }
                else if(y%2==1 && i%2==1){
                    if(i==1){
                        block[i][y].setWhite(true);
                        block[i][y].setSymbol("w");
                    }
                    else
                        block[i][y].setSymbol("+");
                }
                else if(y % 2 == 0) {
                    if (i == 5 || i == 7) {
                        block[i][y].setWhite(true);
                        block[i][y].setSymbol("b");
                    } else {
                        block[i][y].setSymbol("+");
                    }
                }
                else {
                    if (i==6) {
                        block[i][y].setWhite(true);
                        block[i][y].setSymbol("b");
                    } else {
                        block[i][y].setSymbol("+");
                    }
                }
            }
        }
    }

    void showBoard(){
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++)
                System.out.print(block[i][y].symbol);
            System.out.print("\n");
        }
    }
}
