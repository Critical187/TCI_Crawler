package TCI_Crawler.crawler;

import java.util.List;

import TCI_Crawler.exceptions.*;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.*;
import org.jsoup.nodes.Document;

public class SpiderLeg {

    private final LinksHandler linksHandler;
    private final SearchObjectHandler searchObjectHandler;

    public SpiderLeg(LinksHandler linksHandler, SearchObjectHandler searchObjectHandler) {
        this.linksHandler = linksHandler;
        this.searchObjectHandler = searchObjectHandler;
    }

    public SearchObjectWithLinks getObjectAndLinks(Document htmlDocument) throws InvalidCategoryException {
        SearchObjectBase foundObject = this.searchObjectHandler.getSearchObjects(htmlDocument);
        List<String> validLinks = this.linksHandler.getValidLinks(htmlDocument);
        return new SearchObjectWithLinks(foundObject, validLinks);
    }
}