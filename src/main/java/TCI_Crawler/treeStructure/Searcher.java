package TCI_Crawler.treeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Searcher {
    //uses the url as a key to store the nodes
    private final TreeMap<String, Node<SearchObjectBase>> listOfNodes = new TreeMap<>();
    //The Root Node for easy accessing.
    protected Node<SearchObjectBase> rootNode;
    //All the SearchObjectBases from the nodes collected toegether.
    private TreeSet retrievedObjects;
    //The deepest depth of our search
    private int depth = -1;
    //The depth of the recursive used to check how deep we are.
    private int tempDepth = 0;
    //The nodes that we visited to collect the data.
    private int pagesExplored = 0;
    //if we find the object we should stop our recursive.
    private boolean objectFound = false;
    //The title to search for, for easy accessing.
    private String titleToSearchFor;
    //link the retrieved objects from the Spider with our own variable.
    public Searcher(TreeSet retrievedObjects) {
        this.retrievedObjects = retrievedObjects;
    }

    public int getPagesExplored() {
        return pagesExplored;
    }

    // Recursive DFS
    private void dfs(Node node) {
        //as this is a recursive we need to check if we didn't already find the object.
        if (objectFound)
            return;
        //Each time we go deeper and we store that with the variable.
        tempDepth++;
        //We look at a new page.
        pagesExplored++;
        //If this is the deepest search so far, then we store that.
        if (tempDepth > depth)
            depth = tempDepth;
        //time to loop through the children
        List<Node> children = node.getChildren();
        //we have visited this node so flag him so we don't accidentally do it twice.
        node.setVisited(true);
        //let's see if this one has children that hasn't been searched for yet. We need the deepest first.
        for (int i = 0; i < children.size(); i++) {
            Node n = children.get(i);
            if (n != null && !n.isVisited()) {
                //here is where our recursive magic happens
                dfs(n);
            }
        }
        //We check if we found one that actually holds information.
        if (node.getData() != null) {
            if (titleToSearchFor == null) {
                this.retrievedObjects.add(node.getData());
            } else if (Objects.equals(((SearchObjectBase) node.getData()).getName(), titleToSearchFor)) {
                this.retrievedObjects.add(node.getData());
                objectFound = true;
            }
        }
        //We reached the point where we can't go deeper.
        tempDepth--;
    }
    //this calls our inner method which is recursive.
    public void depthFirstSearch() {
        dfs(rootNode);
    }

    public void setTitleToSearchFor(String titleToSearchFor) {
        this.titleToSearchFor = titleToSearchFor;
    }

    public int getDepth() {
        return depth;
    }
    //Clean up so that we can have another round without using values from the previous search
    public void clear() {
        depth = -1;
        tempDepth = 0;
        titleToSearchFor = null;
        listOfNodes.clear();
        pagesExplored = 0;
        objectFound = false;
    }

    /**
     * This beast is another recursive wonder. We use recursion to map out the tree.
     * It sorts out if there is a faster way to a node
     * @param parent The parent of the node about to be created
     * @param url The url of the node
     * @throws InvalidCategoryException
     * @throws InvalidSiteException
     */
    public void makeNode(Node parent, String url) throws InvalidCategoryException, InvalidSiteException {
        //Make sure that we don't have some strange cat?Books and cat?books which point to the same page. But gets treated differently
        String uniqueURL = url.toLowerCase();
        //Check if we didn't already visited this page.
        if (!listOfNodes.containsKey(uniqueURL)) {
            //Alright new page so lets get diggin'.
            //Get a new leg to fetch the contents from the HTML.
            SpiderLegConnection leg = createSpiderLegConnection();
            //Store it inside a variable as we need it more than once.
            SearchObjectWithLinks searchObjectWithLinks = leg.crawlAndGather(url);
            //Create a new node with setting the data from SearchObjectWithLInks
            Node newNode = new Node<SearchObjectBase>(searchObjectWithLinks.getRetrievedObject());
            //Put our new node in the list of Nodes
            listOfNodes.put(uniqueURL, newNode);
            //This one doesn't have a parent so it must be the Root Node.
            if (parent == null) {
                rootNode = newNode;
                rootNode.setWeight(0);
            } else {
                //Link our node to the parent
                parent.addChild(newNode);
                newNode.setWeight(parent.getWeight() + 1);
            }
            //Link our parent to the new node
            //newNode.setParent(parent); //The parent is automatically set when you attach it to the parent
            //Make the children from this node
            for (String link : searchObjectWithLinks.getRetrievedLinks()) {
                //recursive magic happens here
                makeNode(newNode, link);
            }

        } else {    //Alright our Node already exists but we can check if the path towards this node is shorter now.
            if (parent == null) //NVM this is the Root. ABORT
                return;
            //Get that oldNode because we need to do some checks
            Node<SearchObjectBase> oldNode = listOfNodes.get(uniqueURL);
            //Check if the parent is higher up the tree than node
            if (parent.getWeight() < oldNode.getWeight()) { //If that's the case we detach from our old parent and this parent becomes our new parent
                oldNode.getParent().removeChild(oldNode);
                oldNode.setWeight(parent.getWeight() + 1);
                //oldNode.setParent(parent); //Parent is set automatically
                parent.addChild(oldNode);
            }
        }
    }

    protected SpiderLegConnection createSpiderLegConnection() {
        return new SpiderLegConnection();
    }

    //HA we actually breadth First Search the Tree to print the correct layout
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
            for (Node child : node.getChildren())
                queue.add(child);
        }
        System.out.println(row);
    }
}
