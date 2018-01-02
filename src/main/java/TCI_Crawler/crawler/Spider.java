package TCI_Crawler.crawler;

import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Spider {
    private final List<String> pagesVisited = new ArrayList<>();
    private final Stack<String> pagesToVisit = new Stack<>();
    private final List<SearchObjectBase> retrievedObjects = new ArrayList<>();
    private int id = 0;

    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();
        pagesToVisit.add(url);
        while (!pagesToVisit.isEmpty()) {
            String currentUrl = this.pagesToVisit.pop();
            SpiderLegConnection leg = new SpiderLegConnection();
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
            for (String newUrl : searchObjectWithLinks.getRetrievedLinks()) {
                if (!this.pagesVisited.contains(newUrl) && !pagesToVisit.contains(newUrl)) {
                    pagesToVisit.add(newUrl);
                }
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        return new SearchResult(new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    public int getNumberOfPagesExplored() {
        return pagesVisited.size();
    }

    public void clear() {
        this.pagesToVisit.clear();
        this.pagesVisited.clear();
        this.retrievedObjects.clear();
    }
}