package TCI_Crawler.treeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

/**
 * A searcher that can crawl through a tree of nodes.
 */
public class Searcher {

    /**
     * A map of strings and nodes. Uses the URL from where the object was retrieved as a key.
     */
    private final TreeMap<String, Node<SearchObjectBase>> listOfNodes = new TreeMap<>();

    /**
     * The root node of the tree, used by this searcher.
     */
    protected Node<SearchObjectBase> rootNode;

    /**
     * The tree of retrieved objects from all nodes.
     */
    private TreeSet<SearchObjectBase> retrievedObjects;

    /**
     * The highest depth of the search.
     */
    private int depth = -1;

    /**
     * The current depth, used to verify how deep the search is.
     */
    private int tempDepth = 0;

    /**
     * The amount of pages explored.
     */
    private int pagesExplored = 0;

    /**
     * Indicates whether the object that is looked for has been found. If so the search can be stopped.
     */
    private boolean objectFound = false;

    /**
     * The title to search for. Can be null to perform a complete search.
     */
    private String titleToSearchFor;

    /**
     * Initializes a new instance of the {@link Searcher} class.
     *
     * @param retrievedObjects The tree set to search.
     */
    public Searcher(TreeSet<SearchObjectBase> retrievedObjects) {
        this.retrievedObjects = retrievedObjects;
    }

    /**
     * @return Gets the amount of pages explored.
     */
    public int getPagesExplored() {
        return this.pagesExplored;
    }

    /**
     * Recursively uses the DFS algorithm to search, starting from the root node.
     *
     * @param node The node to look at.
     */
    private void depthFirstSearch(Node<SearchObjectBase> node) {
        //as this is a recursive we need to check if we didn't already find the object.
        if (objectFound){
            return;
        }
        // Each time we go deeper and we store that with the variable.
        this.tempDepth++;
        this.pagesExplored++;

        // If this is the deepest search so far, then we store that.
        if (this.tempDepth > this.depth) {
            this.depth = this.tempDepth;
        }

        // Time to loop through the children
        List<Node> children = node.getChildren();
        // We have visited this node so flag him so we don't accidentally do it twice.
        node.setVisited(true);
        // Let's see if this one has children that hasn't been searched for yet. We need the deepest first.
        for (Node n : children) {
            if (n != null && !n.isVisited()) {
                // Here is where our recursive magic happens
                this.depthFirstSearch(n);
            }
        }

        // We check if we found one that actually holds information.
        if (node.getData() != null) {
            if (titleToSearchFor == null) {
                this.retrievedObjects.add(node.getData());
            } else if (Objects.equals(( node.getData()).getName(), titleToSearchFor)) {
                this.retrievedObjects.add(node.getData());
                objectFound = true;
            }
        }
        // We reached the point where we can't go deeper.
        this.tempDepth--;
    }

    /**
     * Performs a depth first search for the root node.
     */
    public void depthFirstSearch() {
        this.depthFirstSearch(this.rootNode);
    }

    /**
     * Sets the title to search for
     *
     * @param titleToSearchFor The new title to search for.
     */
    public void setTitleToSearchFor(String titleToSearchFor) {
        this.titleToSearchFor = titleToSearchFor;
    }

    /**
     * @return Gets the depth of this search.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Cleans up the search so that another round without using values from the previous search can be performed.
     */
    public void clear() {
        this.depth = -1;
        this.tempDepth = 0;
        this.titleToSearchFor = null;
        this.listOfNodes.clear();
        this.pagesExplored = 0;
        this.objectFound = false;
    }

    /**
     * Map out the tree recursively and sorts out if there is a faster way to a node
     *
     * @param parent The parent of the node about to be created
     * @param url    The url of the node
     * @throws InvalidCategoryException If a search object was found but its category is different from the allowed
     *                                  ones : 'Music', 'Books' or 'Movies'
     * @throws InvalidSiteException     When a connection to the site could not be established.
     */
    public void makeNode(Node parent, String url) throws InvalidCategoryException, InvalidSiteException {
        // Make sure that we don't have some strange cat?Books and cat?books which point to the same page but gets
        // treated differently
        String uniqueURL = url.toLowerCase();
        // Check if we didn't already visited this page.
        if (!this.listOfNodes.containsKey(uniqueURL)) {
            // Alright new page so lets get diggin'.
            // Get a new leg to fetch the contents from the HTML.
            SpiderLegConnection leg = createSpiderLegConnection();
            // Store it inside a variable as we need it more than once.
            SearchObjectWithLinks searchObjectWithLinks = leg.crawlAndGather(url);

            Node<SearchObjectBase> newNode = new Node<>(searchObjectWithLinks.getRetrievedObject());
            // Put our new node in the list of Nodes
            this.listOfNodes.put(uniqueURL, newNode);
            // This one doesn't have a parent so it must be the Root Node.
            if (parent == null) {
                this.rootNode = newNode;
                this.rootNode.setWeight(0);
            } else {
                //Link our node to the parent
                parent.addChild(newNode);
                newNode.setWeight(parent.getWeight() + 1);
            }
            // Link our parent to the new node and make the children from this node.
            for (String link : searchObjectWithLinks.getRetrievedLinks()) {
                //recursive magic happens here
                this.makeNode(newNode, link);
            }

        } else {    //Alright our Node already exists but we can check if the path towards this node is shorter now.
            /*if (parent == null) //NVM this is the Root. ABORT (Wait maybe a second ROOT!)
                return; */
            // Get that oldNode because we need to do some checks
            Node<SearchObjectBase> oldNode = listOfNodes.get(uniqueURL);
            // Check if the parent is higher up the tree than node
            if (parent.getWeight() - oldNode.getWeight() >= 2) { //If that's the case we detach from our old parent and this parent becomes our new parent
                oldNode.getParent().removeChild(oldNode);
                parent.addChild(oldNode);
                oldNode.setWeight(parent.getWeight() + 1);

            }
        }
    }

    /**
     * Creates a new {@link SpiderLegConnection} instance.
     *
     * @return A new {@link SpiderLegConnection} object.
     */
    protected SpiderLegConnection createSpiderLegConnection() {
        return new SpiderLegConnection();
    }

    // TODO remove this.
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
            row += (node.getData() != null) ?
                    (node.getData()).getName() + " " : " " + node.getWeight() + " ";
            for (Node child : node.getChildren())
                queue.add(child);
        }
        System.out.println(row);
    }
}
