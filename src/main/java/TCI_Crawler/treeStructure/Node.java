package TCI_Crawler.treeStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Node<T extends Comparable> implements Comparable {
    //The children of the Node, if it has none than it's a leaf.
    TreeSet<Node> children;
    //this holds the information of the node
    private T data;
    //when searching the tree you won't want to visit the same node twice
    private boolean visited;
    //The parent of the node, if zero that means that this little fellow is the Root.
    private Node<T> parent;
    //The Depth of the node. Root will be zero children of Root will be 1.
    private int weight = -1;

    public Node(T data) {
        this.data = data;
        this.children = new TreeSet<>();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public void removeChild(Node unwantedChildNode) {
        this.children.remove(unwantedChildNode);
    }

    public void addChild(Node childNode) {
        this.children.add(childNode);
    }

    public List getChildren() {
        return children.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public void setChildren(List children) {
        this.children = new TreeSet<Node>(children);

    }

    public int getWeight() {
        return weight;
    }
    //it's pretty dynamic. The moment you hang the node higher in the tree the children automaticly get a smaller weight as well.
    public void setWeight(int weight) {
        this.weight = weight;
        for (Node node : children) {
            node.setWeight(weight);
        }
    }
    //this bad boy checks if the data is the same
    //if data is missing: it checks if the nodes are the same using the hashcodes.
    @Override
    public int compareTo(Object o) {
        int value = 0;
        Node<T> otherNode = (Node<T>) o;
        if (otherNode.data == null || this.data == null) {

            value = this.hashCode() > otherNode.hashCode() ? -1 : 1;
            if(this.hashCode() == otherNode.hashCode())value = 0;
            return value;
        }

        value = this.data.compareTo(otherNode.data);

        return value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}