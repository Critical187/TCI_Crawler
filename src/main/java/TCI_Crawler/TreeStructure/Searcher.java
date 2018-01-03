package TCI_Crawler.TreeStructure;

import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.List;
import java.util.Objects;

public class Searcher {

    private List retrievedObjects;
    private int depth = -1;
    private int tempDepth = 0;
    private String titleToSearchFor;

    public Searcher(List retrievedObjects) {
        this.retrievedObjects = retrievedObjects;
    }

    // Recursive DFS
    public void dfs(Node node) {
        tempDepth++;
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
                return;
            }
        }
        tempDepth = 0;
    }

    public void setTitleToSearchFor(String titleToSearchFor) {
        this.titleToSearchFor = titleToSearchFor;
    }

    public int getDepth() {
        return depth;
    }
}
