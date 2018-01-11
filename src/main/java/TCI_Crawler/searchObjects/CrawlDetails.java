package TCI_Crawler.searchObjects;

/**
 * A class, that represents the details about a specific crawl.
 */
public class CrawlDetails {

    /**
     * Represents the identifier of the crawl that these details relate to.
     */
    private final int id;

    /**
     * Represents the time that was needed to finish the crawl.
     */
    private final String timeElapsed;

    /**
     * Represents the amount of pages explored during the crawl.
     */
    private final int pagesExplored;

    /**
     * Represents the search depth of the crawl.
     */
    private final int searchDepth;

    /**
     * Initializes a new instance of the {@link CrawlDetails} class.
     *
     * @param id            Value for {@link CrawlDetails#id}
     * @param timeElapsed   Value for {@link CrawlDetails#timeElapsed}
     * @param pagesExplored Value for {@link CrawlDetails#pagesExplored}
     * @param searchDepth   Value for {@link CrawlDetails#searchDepth}
     */
    public CrawlDetails(int id, long timeElapsed, int pagesExplored, int searchDepth) {
        this.id = id;
        this.timeElapsed = timeConverter(timeElapsed);
        this.pagesExplored = pagesExplored;
        this.searchDepth = searchDepth;
    }

    /**
     * Converts the time elapsed into a readable string.
     *
     * @param timeElapsed The time elapsed.
     * @return A readable string, showing the time it took to perform the crawl.
     */
    private String timeConverter(long timeElapsed) {
        long hoursInMilliSeconds = 60 * 60 * 1000;
        long minutesInMilliSeconds = 60 * 1000;
        long secondsInMilliSeconds = 1000;
        String convenientTime = "";
        long timeRemaining = timeElapsed;

        // Hours
        if (timeRemaining / hoursInMilliSeconds > 0) {
            convenientTime += timeRemaining / hoursInMilliSeconds + "h ";
            timeRemaining %= hoursInMilliSeconds;
        }

        // Minutes
        if (timeRemaining / minutesInMilliSeconds > 0) {
            convenientTime += timeRemaining / minutesInMilliSeconds + "m ";
            timeRemaining %= minutesInMilliSeconds;
        }

        // Seconds
        if ((timeRemaining * 1.0) / secondsInMilliSeconds > 0.01) {
            convenientTime += (1.0 * timeRemaining) / secondsInMilliSeconds + "s ";
        }

        return convenientTime.trim();
    }

    /**
     * @return Gets the identifier of the crawl.
     */
    public int getID() {
        return id;
    }

    /**
     * @return Gets the time that was needed to perform the crawl.
     */
    public String getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * @return Gets the amount of pages explored during the crawl.
     */
    public int getPagesExplored() {
        return pagesExplored;
    }

    /**
     * @return Gets the search depth of the crawl.
     */
    public int getSearchDepth() {
        return searchDepth;
    }
}