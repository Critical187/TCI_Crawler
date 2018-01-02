package TCI_Crawler.searchObjects;

public class SearchDetails {
    private final int id;
    private final String timeElapsed;
    private final int pagesExplored;
    private final int searchDepth;

    public SearchDetails(int id, long timeElapsed, int pagesExplored, int searchDepth) {
        this.id = id;
        this.timeElapsed = timeConverter(timeElapsed);
        this.pagesExplored = pagesExplored;
        this.searchDepth = searchDepth;
    }

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

    public int getID() {
        return id;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public int getPagesExplored() {
        return pagesExplored;
    }

    public int getSearchDepth() {
        return searchDepth;
    }
}