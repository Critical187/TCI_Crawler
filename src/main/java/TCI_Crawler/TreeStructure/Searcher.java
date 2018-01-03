package TCI_Crawler.TreeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Searcher {
    private final List<String> pagesVisited = new ArrayList<>();
    private final TreeMap<String, Node<SearchObjectBase>> listOfNodes = new TreeMap<>();
    private Node<SearchObjectBase> rootNode;
    private TreeSet retrievedObjects;
    private int depth = -1;
    private int tempDepth = 0;
    private int pagesExplored = 0;
    private boolean objectFound = false;
    private String titleToSearchFor;

    public Searcher(TreeSet retrievedObjects) {
        this.retrievedObjects = retrievedObjects;
    }

    public int getPagesExplored() {
        return pagesExplored;
    }

    // Recursive DFS
    private void dfs(Node node) {
        if (objectFound)
            return;
        tempDepth++;
        pagesExplored++;
        if (tempDepth > depth)
            depth = tempDepth;
        List<Node> neighbours = node.getNeighbours();
        node.setVisited(true);
        for (int i = 0; i < neighbours.size(); i++) {
            Node n = neighbours.get(i);
            if (n != null && !n.isVisited()) {
                dfs(n);
            }
        }
        if (node.getData() != null) {
            if (titleToSearchFor == null) {
                this.retrievedObjects.add(node.getData());
            } else if (Objects.equals(((SearchObjectBase) node.getData()).getName(), titleToSearchFor)) {
                this.retrievedObjects.add(node.getData());
                objectFound = true;
            }
        }
        tempDepth = 0;
    }

    public void depthFirstSearch() {
        dfs(rootNode);
    }

    public void setTitleToSearchFor(String titleToSearchFor) {
        this.titleToSearchFor = titleToSearchFor;
    }

    public int getDepth() {
        return depth;
    }

    public void clear() {
        depth = -1;
        tempDepth = 0;
        titleToSearchFor = null;
        listOfNodes.clear();
        pagesVisited.clear();
        pagesExplored = 0;
        objectFound = false;
    }

    public void makeNode(Node parent, String url) throws InvalidCategoryException, InvalidSiteException {
        String uniqueURL = url.toLowerCase();
        if (!pagesVisited.contains(uniqueURL)) {
            pagesVisited.add(url);
            SpiderLegConnection leg = new SpiderLegConnection();
            SearchObjectWithLinks searchObjectWithLinks = leg.crawlAndGather(url);
            Node newNode = new Node<SearchObjectBase>(searchObjectWithLinks.getRetrievedObject());
            listOfNodes.put(url, newNode);
            if (parent == null) {
                rootNode = newNode;
                rootNode.setWeight(0);
            } else {
                parent.addNeighbours(newNode);
                newNode.setWeight(parent.getWeight() + 1);
            }
            newNode.setParent(parent);
            for (String link : searchObjectWithLinks.getRetrievedLinks()) {
                makeNode(newNode, link);
            }

        } else {
            if (parent == null)
                return;
            Node<SearchObjectBase> oldNode = listOfNodes.get(uniqueURL);
            if (parent.getWeight() < oldNode.getWeight()) {
                oldNode.getParent().removeNeighbour(oldNode);
                oldNode.setWeight(parent.getWeight() + 1);
                for(Node<SearchObjectBase> child : oldNode.neighbours)
                { child.setWeight(oldNode.getWeight()+1);}

                oldNode.setParent(parent);
                parent.addNeighbours(oldNode);
            }
        }
    }


    public void printTree() {
        //BreadthFirstSearch As a best
        Queue<Node<SearchObjectBase>> queue = new LinkedList<Node<SearchObjectBase>>();
        String row = "";
        queue.add(rootNode);
        int printDepth = 0;
        while (!queue.isEmpty()) {
            Node<SearchObjectBase> node = queue.remove();
            if (node.getWeight() > printDepth) {
                System.out.println(row);
                printDepth++;
                row = "";
            }
            row += "\u2600";
            row+= (node.getData()!=null) ? ((SearchObjectBase)node.getData()).getName()+ " " :" " + node.getWeight() + " ";
            for (Node child : node.neighbours)
                queue.add(child);
        }
        System.out.println(row);
    }
}
