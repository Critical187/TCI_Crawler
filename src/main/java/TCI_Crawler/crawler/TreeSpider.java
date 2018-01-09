package TCI_Crawler.crawler;

import TCI_Crawler.treeStructure.Searcher;
import TCI_Crawler.dto.SearchDetails;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.DetailsStorageHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

public class TreeSpider {
    private final DetailsStorageHandler detailsStorageHandler;
    private final TreeSet<SearchObjectBase> retrievedObjects = new TreeSet<>();
    private Searcher searcher;

    public TreeSpider(DetailsStorageHandler detailsStorageHandler) {
        this.detailsStorageHandler = detailsStorageHandler;
        searcher = new Searcher(retrievedObjects);
    }

    public static void main(String... arg) {
        TreeSpider ts = new TreeSpider(new DetailsStorageHandler());
        try {
            System.out.println(ts.search("http://i315379.hera.fhict.nl/", "A"));
            ts.searcher.printTree();
        } catch (InvalidCategoryException | InvalidSiteException e) {
            e.printStackTrace();
        }
    }

    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();

        searcher.setTitleToSearchFor(titleToSearchFor);
        searcher.makeNode(null, url);
        searcher.depthFirstSearch();

        long elapsedTime = System.currentTimeMillis() - startTime;
        int id = this.detailsStorageHandler.getNextId();
        this.detailsStorageHandler.addDetails(new TCI_Crawler.searchObjects.SearchDetails(
                id,
                elapsedTime,
                searcher.getPagesExplored(),
                searcher.getDepth()));

        return new SearchResult(id, new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    public Optional<SearchDetails> getSearchDetails(Integer id) {
        return this.detailsStorageHandler
                .getDetails(id)
                .flatMap(x -> Optional.of(new SearchDetails(x)));
    }

    //Might be placed in separate class for separating responsibilities


    public void clear() {
        this.searcher.clear();
        this.retrievedObjects.clear();
    }
}
