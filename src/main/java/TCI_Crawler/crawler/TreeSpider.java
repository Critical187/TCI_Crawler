package TCI_Crawler.crawler;

import TCI_Crawler.treeStructure.Searcher;
import TCI_Crawler.dto.CrawlDetails;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.DetailsStorageHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

/**
 * The spider, performing the crawl and gathering objects and links.
 */
public class TreeSpider {

    /**
     * The details storage handler, used to store and provide details about a specific crawl.
     */
    private final DetailsStorageHandler detailsStorageHandler;

    /**
     * The tree set of objects, populated during the crawl.
     */
    private final TreeSet<SearchObjectBase> retrievedObjects = new TreeSet<>();

    /**
     * The searcher to actually perform the crawl.
     */
    private final Searcher searcher;

    /**
     * Initializes a new instance of the {@link TreeSpider} class.
     *
     * @param detailsStorageHandler Value for {@link TreeSpider#detailsStorageHandler}
     */
    public TreeSpider(DetailsStorageHandler detailsStorageHandler) {
        this.detailsStorageHandler = detailsStorageHandler;
        this.searcher = new Searcher(this.retrievedObjects);
    }

    // TODO remove this.
    public static void main(String... arg) {
        TreeSpider ts = new TreeSpider(new DetailsStorageHandler());
        try {
            System.out.println(ts.search("http://i315379.hera.fhict.nl/", "A"));
            ts.searcher.printTree();
        } catch (InvalidCategoryException | InvalidSiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a search on the website, located on the given URL. If titleToSearchFor is not null then the search
     * will stop if the object with that title has been found, else a full crawl will be performed.
     *
     * @param url              The website's url.
     * @param titleToSearchFor The title to look for. Null to crawl the entire website.
     * @return An object, containing all the found objects and the time it took to perform the crawl.
     * @throws InvalidCategoryException If a search object was found but its category is different from the allowed
     *                                  ones : 'Music', 'Books' or 'Movies'
     * @throws InvalidSiteException     When a connection to the site could not be established.
     */
    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();

        this.searcher.setTitleToSearchFor(titleToSearchFor);
        this.searcher.makeNode(null, url);
        this.searcher.depthFirstSearch();

        long elapsedTime = System.currentTimeMillis() - startTime;
        int id = this.detailsStorageHandler.getNextId();
        this.detailsStorageHandler.addDetails(new TCI_Crawler.searchObjects.CrawlDetails(
                id,
                elapsedTime,
                this.searcher.getPagesExplored(),
                this.searcher.getDepth()));

        return new SearchResult(id, new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    /**
     * Gets the details about a specific crawl. If no crawl with the identifier exists then an empty {@link Optional}
     * will be returned instead.
     *
     * @param id The identifier of the crawl to retrieve details for.
     * @return An {@link Optional} which will either contain an {@link CrawlDetails} object or will be empty if no such
     * crawl with the provided id exists.
     */
    public Optional<CrawlDetails> getSearchDetails(Integer id) {
        return this.detailsStorageHandler
                .getDetails(id)
                .flatMap(x -> Optional.of(new CrawlDetails(x)));
    }

    /**
     * Clears the searcher and retrieved objects, allowing for another crawl.
     */
    public void clear() {
        this.searcher.clear();
        this.retrievedObjects.clear();
    }
}
