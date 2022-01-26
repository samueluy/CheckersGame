// This class represents a single node

import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> children = new ArrayList<>(); // Children of this node
    private ArrayList<String> list; // List of moves possible
    private List<List<Cell>> newLayout = new ArrayList<List<Cell>>(); // Board layout of cells
    private int value; // Heuristic value of current layout

    public Node(int value, ArrayList<String> moveList, List<List<Cell>> pos) {
        this.children = new ArrayList<>();
        this.list = moveList;
        this.value = value;
        this.newLayout = pos;
    }

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }

    /**
     * @param parent : Parent node
     * @brief Adds a child to a given node
     */
    public void addChild(Node parent) {
        children.add(parent);
    }

    /**
     * @return Children of a node
     * @brief Gets the children of a node
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * @return Layout of a board
     * @brief Gets the list of Cells representing the layout of the board
     */
    public List<List<Cell>> getNewLayout() {
        return newLayout;
    }

    /**
     * @return Value of the Node
     * @brief Gets heuristic value set on the Node
     */
    public int getValue() {
        return value;
    }
}
