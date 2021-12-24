import java.awt.*;

public class Cell {
    boolean bWhite;
    Point coord = new Point();
    String symbol = "";

    void setWhite(boolean white){
        this.bWhite=white;
    }

    void setSymbol(String symbol){
        this.symbol=symbol;
    }

    void setCoord(int x, int y){
        this.coord.setLocation(x,y);
    }

    boolean isWhite(){
        return bWhite;
    }

    Point getCoord(){
        return coord;
    }

    String getSymbol(){
        return symbol;
    }
}
