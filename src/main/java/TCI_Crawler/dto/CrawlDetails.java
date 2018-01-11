package TCI_Crawler.dto;

import com.google.gson.annotations.SerializedName;

/**
 * A class, that represents the data transfer object, sent through the API that represents the details about a single
 * crawl.
 */
public class CrawlDetails {

    /**
     * Represents the identifier of the crawl that these details relate to.
     */
    private final int id;

    /**
     * Represents the time that was needed to finish the crawl.
     */
    @SerializedName("time_elapsed")
    private final String timeElapsed;

    /**
     * Represents the amount of pages explored during the crawl.
     */
    @SerializedName("pages_explored")
    private final int pagesExplored;

    /**
     * Represents the search depth of the crawl.
     */
    @SerializedName("search_depth")
    private final int searchDepth;

    /**
     * Initializes a new instance of the {@link CrawlDetails} class.
     *
     * @param details The {@link TCI_Crawler.searchObjects.CrawlDetails} object to initialize this DTO for.
     */
    public CrawlDetails(TCI_Crawler.searchObjects.CrawlDetails details) {
        this.id = details.getID();
        this.timeElapsed = details.getTimeElapsed();
        this.pagesExplored = details.getPagesExplored();
        this.searchDepth = details.getSearchDepth();
    }
}


