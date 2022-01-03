public class Piece {

    Piece(){}
    Piece(boolean white, boolean king, String str){
        this.bWhite=white;
        this.bKing=king;
        this.symbol=str;
    }

    boolean bWhite;
    boolean bKing;
    String symbol = "";

    void setWhite(boolean white){
        this.bWhite=white;
    }

    void setSymbol(String symbol){
        this.symbol=symbol;
    }

    void setKing(boolean king){
        this.bKing=king;
    }

    boolean isWhite(){
        return bWhite;
    }

    boolean isKing(){
        return bKing;
    }

    String getSymbol(){
        return symbol;
    }
}
