package TCI_Crawler.crawler;

import TCI_Crawler.TreeStructure.Node;
import TCI_Crawler.TreeStructure.Searcher;
import TCI_Crawler.dto.SearchDetails;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.DetailsStorageHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TreeSpider {
    private final HashMap<String, Node<SearchObjectBase>> listOfNodes = new HashMap<>();
    private final List<String> pagesVisited = new ArrayList<>();
    private final DetailsStorageHandler detailsStorageHandler;
    private final List<SearchObjectBase> retrievedObjects = new ArrayList<>();
    private Node<SearchObjectBase> rootNode;
    private Searcher searcher;

    public TreeSpider(DetailsStorageHandler detailsStorageHandler) {
        this.detailsStorageHandler = detailsStorageHandler;
        searcher = new Searcher(retrievedObjects);
    }

    public static void main(String... arg) {
        TreeSpider ts = new TreeSpider(new DetailsStorageHandler());
        try {
            System.out.println(ts.search("http://i315379.hera.fhict.nl/", null));
        } catch (InvalidCategoryException e) {
            e.printStackTrace();
        } catch (InvalidSiteException e) {
            e.printStackTrace();
        }
    }

    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();
        makeNode(null, url);
        searcher.setTitleToSearchFor(titleToSearchFor);
        searcher.dfs(rootNode);

        long elapsedTime = System.currentTimeMillis() - startTime;
        int id = this.detailsStorageHandler.getNextId();
        this.detailsStorageHandler.addDetails(new TCI_Crawler.searchObjects.SearchDetails(
                id,
                elapsedTime,
                this.pagesVisited.size(),
                searcher.getDepth()));

        return new SearchResult(id, new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    public Optional<SearchDetails> getSearchDetails(Integer id) {
        return this.detailsStorageHandler
                .getDetails(id)
                .flatMap(x -> Optional.of(new SearchDetails(x)));
    }

    //Might be placed in separate class for separating responsibilities
    private void makeNode(Node parent, String url) throws InvalidCategoryException, InvalidSiteException {
        if (!pagesVisited.contains(url)) {
            pagesVisited.add(url);
            SpiderLegConnection leg = new SpiderLegConnection();
            SearchObjectWithLinks searchObjectWithLinks = leg.crawlAndGather(url);
            Node newNode = new Node<SearchObjectBase>(searchObjectWithLinks.getRetrievedObject());
            listOfNodes.put(url, newNode);
            if (parent == null) {
                rootNode = newNode;
                rootNode.setWeight(0);
            } else {
                parent.addneighbours(newNode);
                newNode.setWeight(parent.getWeight() + 1);
            }
            for (String link : searchObjectWithLinks.getRetrievedLinks()) {
                makeNode(newNode, link);
            }

        } else {
            if (parent == null)
                return;
            Node oldNode = listOfNodes.get(url);
            if (parent.getWeight() < oldNode.getWeight()) {
                oldNode.setWeight(parent.getWeight() + 1);
                parent.addneighbours(oldNode);
            }
        }
    }

    public void clear() {
        rootNode = null;
        this.pagesVisited.clear();
        this.retrievedObjects.clear();
    }
}
