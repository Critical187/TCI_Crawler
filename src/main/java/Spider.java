import java.util.*;

public class Spider {
    private List<String> pagesVisited = new ArrayList<>();
    private List<String> pagesToVisit = new ArrayList<>();
    private List<Base> retrievedObjects = new ArrayList<>();
    private int id = 0;

    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param url        - The starting point of the spider
     * @param searchWord - The word or string that you are searching for
     */
    public List<Base> search(String url, String searchWord) {
        pagesToVisit.add(url);
        while (!pagesToVisit.isEmpty()) {
            String currentUrl = this.pagesToVisit.get(0);
            SpiderLeg leg = new SpiderLeg();
            BaseWithLinks baseWithLinks = leg.crawlAndGather(currentUrl);
            if (baseWithLinks.getRetrievedObject() != null) {
                this.retrievedObjects.add(baseWithLinks.getRetrievedObject());
            }
            this.pagesVisited.add(currentUrl);
            this.pagesToVisit.remove(0);
            for (String newUrl : baseWithLinks.getRetrievedLinks()) {
                if (!this.pagesVisited.contains(newUrl) && !pagesToVisit.contains(newUrl)) {
                    pagesToVisit.add(newUrl);
                }
            }
        }
        return this.retrievedObjects;
    }
}