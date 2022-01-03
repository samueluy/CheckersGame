import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean done = false;
        Scanner in = new Scanner(System.in);
        int fromX,fromY,toX,toY;
        Move main = new Move();
        main.board.createBoard();
        main.board.showBoard();
        ArrayList<String> moveList= new ArrayList<>();

        while(!done){
            System.out.println(main.generateValidMoves(main.board).toString()); // print available moves

            System.out.print("(F) Enter X: ");
            fromX=in.nextInt();
            System.out.print("(F) Enter Y: ");
            fromY=in.nextInt();
            System.out.print("(T) Enter X: ");
            toX=in.nextInt();
            System.out.print("(T) Enter Y: ");
            toY=in.nextInt();
            main.move(main.board, fromX,fromY,toX,toY);

            moveList = main.generateValidMoves(main.board); // generate bot moves
            main.moveAI(moveList);

            main.board.showBoard();
            done=main.board.over();
        }
        //main.move(0,0,1,4);
    }
}
