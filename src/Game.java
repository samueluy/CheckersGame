public class Game {
    public static void main(String[] args) {
        Move main = new Move();
        main.board.createBoard();
        main.board.showBoard();

        main.move(0,0,1,4);
        main.board.showBoard();
    }
}
