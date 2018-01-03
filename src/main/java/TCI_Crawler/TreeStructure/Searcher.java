package TCI_Crawler.TreeStructure;

import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.List;
import java.util.Objects;

public class Searcher {

    private List retrievedObjects;
    private int depth;
    private int tempDepth;
    private String titleToSearchFor;
    public Searcher(List retrievedObjects){
        this.retrievedObjects = retrievedObjects;
    }
    // Recursive DFS
    public  void dfs(Node node)
    {
        tempDepth++;
        if(tempDepth>depth)
            depth=tempDepth;
        List<Node> neighbours=node.getNeighbours();
        node.visited=true;
        for (int i = 0; i < neighbours.size(); i++) {
            Node n=neighbours.get(i);
            if(n!=null && !n.visited)
            {
                dfs(n);
            }
        }
        if (node.data != null) {
            if (titleToSearchFor == null) {
                this.retrievedObjects.add(node.data);
            } else if (Objects.equals(((SearchObjectBase)node.data).getName(), titleToSearchFor)) {
                this.retrievedObjects.add(node.data);
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
