package TCI_Crawler.crawler;

import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Spider {
    private final List<String> pagesVisited = new ArrayList<>();
    private final List<String> pagesToVisit = new ArrayList<>();
    private final List<SearchObjectBase> retrievedObjects = new ArrayList<>();
    private final LinksHandler linksHandler = new LinksHandler();
    private final SearchObjectHandler searchObjectHandler = new SearchObjectHandler();
    private int id = 0;

    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();
        pagesToVisit.add(url);
        while (!pagesToVisit.isEmpty()) {
            String currentUrl = this.pagesToVisit.get(0);
            SpiderLeg leg = new SpiderLeg(this.linksHandler, this.searchObjectHandler);
            SearchObjectWithLinks searchObjectWithLinks = leg.crawlAndGather(currentUrl);
            if (searchObjectWithLinks.getRetrievedObject() != null) {
                if (titleToSearchFor == null) {
                    this.retrievedObjects.add(searchObjectWithLinks.getRetrievedObject());
                } else if (Objects.equals(searchObjectWithLinks.getRetrievedObject().getName(), titleToSearchFor)) {
                    this.retrievedObjects.add(searchObjectWithLinks.getRetrievedObject());
                    break;
                }
            }
            this.pagesVisited.add(currentUrl);
            this.pagesToVisit.remove(0);
            for (String newUrl : searchObjectWithLinks.getRetrievedLinks()) {
                if (!this.pagesVisited.contains(newUrl) && !pagesToVisit.contains(newUrl)) {
                    pagesToVisit.add(newUrl);
                }
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        return new SearchResult(new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    public void clear() {
        this.pagesToVisit.clear();
        this.pagesVisited.clear();
        this.retrievedObjects.clear();
    }
}