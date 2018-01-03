package TCI_Crawler.TreeStructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T> {
    T data;
    boolean visited;
    Node<T> parent;
    int weight = -1;
    HashSet<Node> neighbours;

    public Node(T data) {
        this.data = data;
        this.neighbours = new HashSet<>();
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
    public void removeNeighbour(Node unwantedNeighbourNode){this.neighbours.remove(unwantedNeighbourNode);}
    public void addNeighbours(Node neighbourNode) {
        this.neighbours.add(neighbourNode);
    }

    public List getNeighbours() {
        return new ArrayList<>(neighbours);
    }

    public void setNeighbours(List neighbours) {
        this.neighbours = new HashSet<Node>(neighbours);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        for(Node node : neighbours){
            node.setWeight(weight);
        }
    }
}