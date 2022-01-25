import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean done = false;
        Scanner in = new Scanner(System.in);
        int fromX, fromY, toX, toY;
        Move main = new Move();
        main.board.createBoard();
        main.board.showBoard();

        while (!done) { // until game is over
            do {
                System.out.println(main.generateValidMoves(main.board).toString()); // print available moves
                System.out.print("(F) Enter X: ");
                fromX = in.nextInt();
                System.out.print("(F) Enter Y: ");
                fromY = in.nextInt();
                System.out.print("(T) Enter X: ");
                toX = in.nextInt();
                System.out.print("(T) Enter Y: ");
                toY = in.nextInt();
            }
            while(main.move(main.board, fromX, fromY, toX, toY, main.generateValidMoves(main.board))==0); // Loop till valid input

            main.miniMaxMove(main.board);
            main.board.showBoard();

            done = main.board.over();
        }
    }
}

// https://checkers.fandom.com/wiki/Rules_of_Checkers
