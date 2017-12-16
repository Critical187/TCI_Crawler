package searchObjects;

import java.util.List;

public class SearchObjectWithLinks {

    private final SearchObjectBase retrievedObject;
    private final List<String> retrievedLinks;

    public SearchObjectWithLinks(SearchObjectBase retrievedObject, List<String> retrievedLinks) {
        this.retrievedObject = retrievedObject;
        this.retrievedLinks = retrievedLinks;
    }

    public List<String> getRetrievedLinks() {
        return retrievedLinks;
    }

    public SearchObjectBase getRetrievedObject() {
        return retrievedObject;
    }
}
