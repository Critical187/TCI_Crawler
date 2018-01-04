package TCI_Crawler.searchObjects;

import java.util.List;

/**
 * A class, that represents a retrieved search object and all links found on a single crawled
 * web page.
 */
public class SearchObjectWithLinks {

    /**
     * Represents the found search object. Value can be equal to null if no object was found.
     */
    private final SearchObjectBase retrievedObject;

    /**
     * Represents the retrieved links from the web page. Can be empty list if no links were found.
     */
    private final List<String> retrievedLinks;

    /**
     * Initializes a new instance of the {@link SearchObjectWithLinks} class.
     *
     * @param retrievedObject Value for {@link SearchObjectWithLinks#retrievedObject}
     * @param retrievedLinks  Value for {@link SearchObjectWithLinks#retrievedLinks}
     */
    public SearchObjectWithLinks(SearchObjectBase retrievedObject, List<String> retrievedLinks) {
        this.retrievedObject = retrievedObject;
        this.retrievedLinks = retrievedLinks;
    }

    /**
     * @return Gets the retrieved links. List can be empty if no links were found.
     */
    public List<String> getRetrievedLinks() {
        return retrievedLinks;
    }

    /**
     * @return Gets the retrieved object. Can be null if no object was found.
     */
    public SearchObjectBase getRetrievedObject() {
        return retrievedObject;
    }
}
