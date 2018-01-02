package TCI_Crawler.dto;

import com.google.gson.annotations.SerializedName;

public class SearchDetails {
    private final int id;

    @SerializedName("time_elapsed")
    private final String timeElapsed;

    @SerializedName("pages_explored")
    private final int pagesExplored;

    @SerializedName("search_depth")
    private final int searchDepth;

    public SearchDetails(TCI_Crawler.searchObjects.SearchDetails details) {
        this.id = details.getID();
        this.timeElapsed = details.getTimeElapsed();
        this.pagesExplored = details.getPagesExplored();
        this.searchDepth = details.getSearchDepth();
    }

    public int getID() {
        return id;
    }
}


