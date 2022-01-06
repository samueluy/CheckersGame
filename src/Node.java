import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> children = new ArrayList<>();
    ArrayList<String> list;
    List<List<Cell>> newLayout = new ArrayList<List<Cell>>();
    int value;

    public Node(int value, ArrayList<String> moveList, List<List<Cell>> pos) {
        this.children = new ArrayList<>();
        this.list = moveList;
        this.value = value;
        this.newLayout = pos;
    }

    public Node() {
    }

    public void addChild(Node child) {
        children.add(child);
    }

}