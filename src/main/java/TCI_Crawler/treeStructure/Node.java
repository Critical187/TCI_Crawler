package TCI_Crawler.treeStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    //The children of the Node, if it has none than it's a leaf.
    private TreeSet<Node> children;
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

//    public void setParent(Node<T> parent) {
//        this.parent = parent;
//    }

    public T getData() {
        return data;
    }

    public void removeChild(Node unwantedChildNode) {
        this.children.remove(unwantedChildNode);
    }

    public void addChild(Node childNode) {
        this.children.add(childNode);
        childNode.parent = this;
    }

    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    public void setChildren(List<Node> children) {
        this.children = new TreeSet<>(children);
        for (Node child : children){
            child.parent = this;
        }
    }

    public int getWeight() {
        return weight;
    }
    //it's pretty dynamic. The moment you hang the node higher in the tree the children automaticly get a smaller weight as well.
    public void setWeight(int weight) {
        this.weight = weight;
        for (Node node : children) {
            node.setWeight(weight + 1);
        }
    }
    //this bad boy checks if the data is the same
    //if data is missing: it checks if the nodes are the same using the hashcodes.
    @Override
    public int compareTo(Node<T> otherNode) {
        if (otherNode.data == null || this.data == null) {
            //nodes with data should go on the left side of a tree
            return otherNode.hashCode() - this.hashCode();
        }

        return this.data.compareTo(otherNode.data);
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Node<?> node = (Node<?>) o;
//
//        if (visited != node.visited) return false;
//        if (weight != node.weight) return false;
//        if (children != null ? !children.equals(node.children) : node.children != null) return false;
//        if (data != null ? !data.equals(node.data) : node.data != null) return false;
//        return parent != null ? parent.equals(node.parent) : node.parent == null;
//    }

    @Override
    public int hashCode() {
        int result = children != null ? children.size() : 0;
        result += (data != null ? data.hashCode() : 0);
        result += (visited ? 1 : 0);
        result += (parent != null ? parent.hashCode() : 0);
        result += weight;
        result = 31 * result + result;
        return result;
    }
}