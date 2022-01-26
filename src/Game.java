// Driver class for the Checkers game with Minimax Alpha-Beta Pruning AI

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean done = false;
        Scanner in = new Scanner(System.in);
        int fromX, fromY, toX, toY;
        Move main = new Move();
        main.getBoard().createBoard();
        main.getBoard().showBoard();

        while (!done) { // Loop until game is over
            do {
                System.out.println(main.generateValidMoves(main.getBoard()).toString()); // Print available moves
                System.out.print("(F) Enter X: ");
                fromX = in.nextInt();
                System.out.print("(F) Enter Y: ");
                fromY = in.nextInt();
                System.out.print("(T) Enter X: ");
                toX = in.nextInt();
                System.out.print("(T) Enter Y: ");
                toY = in.nextInt();
            } while (main.move(main.getBoard(), fromX, fromY, toX, toY, main.generateValidMoves(main.getBoard())) == 0); // Loop till valid input

            main.miniMaxMove(main.getBoard());
            main.getBoard().showBoard();

            done = main.getBoard().over();
        }
    }
}
