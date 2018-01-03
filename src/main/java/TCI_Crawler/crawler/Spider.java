package TCI_Crawler.crawler;

import TCI_Crawler.dto.SearchDetails;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.DetailsStorageHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import java.util.*;

public class Spider {
    private final List<String> pagesVisited = new ArrayList<>();
    private final DetailsStorageHandler detailsStorageHandler;
    private final Stack<String> pagesToVisit = new Stack<>();
    private final List<SearchObjectBase> retrievedObjects = new ArrayList<>();
    private int id = 0;

    public Spider(DetailsStorageHandler detailsStorageHandler) {
        this.detailsStorageHandler = detailsStorageHandler;
    }

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
        int id = this.detailsStorageHandler.getNextId();
        this.detailsStorageHandler.addDetails(new TCI_Crawler.searchObjects.SearchDetails(
                id,
                elapsedTime,
                this.pagesVisited.size(),
                1));

        return new SearchResult(id, new ArrayList<>(this.retrievedObjects), elapsedTime);
    }

    public Optional<SearchDetails> getSearchDetails(Integer id) {
        return this.detailsStorageHandler
                .getDetails(id)
                .flatMap(x -> Optional.of(new SearchDetails(x)));
    }

    public void clear() {
        this.pagesToVisit.clear();
        this.pagesVisited.clear();
        this.retrievedObjects.clear();
    }
}