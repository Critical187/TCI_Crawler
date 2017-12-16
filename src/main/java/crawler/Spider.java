package crawler;

import dto.SearchResult;
import exceptions.InvalidCategoryException;
import exceptions.InvalidSiteException;
import searchObjects.SearchObjectBase;
import searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Spider {
    private final List<String> pagesVisited = new ArrayList<>();
    private final List<String> pagesToVisit = new ArrayList<>();
    private final List<SearchObjectBase> retrievedObjects = new ArrayList<>();
    private int id = 0;

    public SearchResult search(String url, String titleToSearchFor)
            throws InvalidCategoryException, InvalidSiteException {
        long startTime = System.currentTimeMillis();
        pagesToVisit.add(url);
        while (!pagesToVisit.isEmpty()) {
            String currentUrl = this.pagesToVisit.get(0);
            SpiderLeg leg = new SpiderLeg();
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

        return new SearchResult(this.retrievedObjects, elapsedTime);
    }

    public void clear() {
        this.pagesToVisit.clear();
        this.pagesVisited.clear();
        this.retrievedObjects.clear();
    }
}