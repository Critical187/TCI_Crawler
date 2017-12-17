package TCI_Crawler.dto;

import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.List;

public class SearchResult {

    private final List<SearchObjectBase> retrievedObjects;
    private final long time;

    public SearchResult(List<SearchObjectBase> retrievedObjects, long time) {
        this.retrievedObjects = retrievedObjects;
        this.time = time;
    }

    public List<SearchObjectBase> getRetrievedObjects() {
        return retrievedObjects;
    }

    public long getTime() {
        return time;
    }
}
