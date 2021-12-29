import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean done = false;
        Scanner in = new Scanner(System.in);
        int fromX,fromY,toX,toY;
        Move main = new Move();
        main.board.createBoard();
        main.board.showBoard();
        while(!done){
            System.out.print("(F) Enter X: ");
            fromX=in.nextInt();
            System.out.print("(F) Enter Y: ");
            fromY=in.nextInt();
            System.out.print("(T) Enter X: ");
            toX=in.nextInt();
            System.out.print("(T) Enter Y: ");
            toY=in.nextInt();
            main.move(fromX,fromY,toX,toY);
            done=main.board.over();
        }
        //main.move(0,0,1,4);
    }
}
