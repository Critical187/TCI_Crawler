package TCI_Crawler.treeStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Represents a single node, holding an object of type {@link T}.
 * @param <T> The type of object, being held in this node.
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    /**
     * The children of this node. If this list is empty then the node is a leaf.
     */
    private TreeSet<Node> children;

    /**
     * The data of the node.
     */
    private T data;

    /**
     * Indicates if the node has already been visited upon a search.
     */
    private boolean visited;

    /**
     * The parent of this node. If null then this node is the root.
     */
    private Node<T> parent;

    /**
     * The weight of the node.
     */
    private int weight = -1;

    /**
     * Initializes a new instance of the {@link Node} class.
     *
     * @param data Value for {@link Node#data}
     */
    public Node(T data) {
        this.data = data;
        this.children = new TreeSet<>();
    }

    /**
     * @return Gets a value indicating whether the node has been visited or not.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Sets the visited value for this node.
     *
     * @param visited The new visited value.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * @return Gets the parent of this node. Null if the node is the root.
     */
    public Node<T> getParent() {
        return this.parent;
    }

    /**
     * @return Get the data, contained in the node.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Removes the specified child from the children list.
     *
     * @param unwantedChildNode The child to remove.
     */
    public void removeChild(Node unwantedChildNode) {
        this.children.remove(unwantedChildNode);
    }

    /**
     * Adds the specified child to the children list.
     *
     * @param childNode The child to add.
     */
    public void addChild(Node childNode) {
        this.children.add(childNode);
        childNode.parent = this;
    }

    /**
     * @return Gets the children of the node.
     */
    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Sets the children of this node and for each one of them marks the parent as this node.
     *
     * @param children The children to set.
     */
    public void setChildren(List<Node> children) {
        this.children = new TreeSet<>(children);
        for (Node child : children) {
            child.parent = this;
        }
    }

    /**
     * @return Gets the weight of this node.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Sets the weight of this node. The momenet that this node is hung higher in the tree structure it will get a
     * smaller weight.
     *
     * @param weight The weight to set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
        for (Node node : this.children) {
            node.setWeight(weight + 1);
        }
    }

    /**
     * Asserts whether this and the otherNode are equal. It will compare the data in both objects
     * and if there is data missing in any - it will compare the hash codes of the nodes.
     *
     * @param otherNode The other node to compare.
     * @return An integer, indicating which node is 'higher' according to how they should be compared.
     */
    @Override
    public int compareTo(Node<T> otherNode) {
        if (otherNode.data == null || this.data == null) {
            //nodes with data should go on the left side of a tree
            return otherNode.hashCode() - this.hashCode();
        }

        return this.data.compareTo(otherNode.data);
    }

    // TODO remove this.
    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    /**
     * @return Gets the hash code of this node.
     */
    @Override
    public int hashCode() {
        int result = children != null ? children.hashCode() / 245643 : 0;
        result += (data != null ? data.hashCode() / 4293451 : 0);
        result += (visited ? 1 : 0);
        result += (parent != null ? parent.hashCode() / 634351324 : 0);
        result += weight;
        result = 21 * result + result;
        return Math.abs(result);
    }
}