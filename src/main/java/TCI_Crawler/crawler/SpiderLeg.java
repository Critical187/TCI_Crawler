package TCI_Crawler.crawler;

import java.util.List;

import TCI_Crawler.exceptions.*;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.*;
import org.jsoup.nodes.Document;

/**
 * A spider leg that can parse an HTML document and retrieve links and found objects.
 */
public class SpiderLeg {

    /**
     * The links handler, that can retrieve links from a given HTMl document.
     */
    private final LinksHandler linksHandler;

    /**
     * The search objects handler, that can retrieve objects from a given HTMl document.
     */
    private final SearchObjectHandler searchObjectHandler;

    /**
     * Initializes a new instance of the {@link SpiderLeg} class.
     *
     * @param linksHandler        Value for {@link SpiderLeg#linksHandler}.
     * @param searchObjectHandler Value for {@link SpiderLeg#searchObjectHandler}.
     */
    public SpiderLeg(LinksHandler linksHandler, SearchObjectHandler searchObjectHandler) {
        this.linksHandler = linksHandler;
        this.searchObjectHandler = searchObjectHandler;
    }

    /**
     * Reads through the given HTMl document and retrieves a single found object and all links on the page. The found
     * object may be null if there was no such object on the document.
     *
     * @param htmlDocument The HTML document to parse.
     * @return The found object and all links.
     * @throws InvalidCategoryException If a search object was found but its category is different from the allowed
     *                                  ones : 'Music', 'Books' or 'Movies'.
     */
    public SearchObjectWithLinks getObjectAndLinks(Document htmlDocument) throws InvalidCategoryException {
        SearchObjectBase foundObject = this.searchObjectHandler.getSearchObjects(htmlDocument);
        List<String> validLinks = this.linksHandler.getValidLinks(htmlDocument);
        return new SearchObjectWithLinks(foundObject, validLinks);
    }
}