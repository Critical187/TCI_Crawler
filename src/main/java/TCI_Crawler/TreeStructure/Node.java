package TCI_Crawler.TreeStructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T> {
    HashSet<Node> neighbours;
    private T data;
    private boolean visited;
    private Node<T> parent;
    private int weight = -1;
    public Node(T data) {
        this.data = data;
        this.neighbours = new HashSet<>();
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
        this.neighbours.remove(unwantedNeighbourNode);
    }

    public void addNeighbours(Node neighbourNode) {
        this.neighbours.add(neighbourNode);
    }

    public List getNeighbours() {
        return neighbours.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public void setNeighbours(List neighbours) {
        this.neighbours = new HashSet<Node>(neighbours);

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
}