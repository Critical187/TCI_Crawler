package TCI_Crawler.TreeStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Node<T extends Comparable> implements Comparable {
    TreeSet<Node> neighbours;
    private T data;
    private boolean visited;
    private Node<T> parent;
    private int weight = -1;

    public Node(T data) {
        this.data = data;
        this.neighbours = new TreeSet<>();
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

    public void removeNeighbour(Node unwantedNeighbourNode) {
        Boolean removed = this.neighbours.remove(unwantedNeighbourNode);
    }

    public void addNeighbours(Node neighbourNode) {
        this.neighbours.add(neighbourNode);
    }

    public List getNeighbours() {
        return neighbours.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public void setNeighbours(List neighbours) {
        this.neighbours = new TreeSet<Node>(neighbours);

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        for (Node node : neighbours) {
            node.setWeight(weight);
        }
    }

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