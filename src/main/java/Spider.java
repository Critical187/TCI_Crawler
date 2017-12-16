import java.util.*;

public class Spider {
    private final List<String> pagesVisited = new ArrayList<>();
    private final List<String> pagesToVisit = new ArrayList<>();
    private final List<Base> retrievedObjects = new ArrayList<>();
    private int id = 0;

    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param url              - The starting point of the spider
     * @param titleToSearchFor - The word or string that you are searching for
     */
    public List<Base> search(String url, String titleToSearchFor) {
        pagesToVisit.add(url);
        while (!pagesToVisit.isEmpty()) {
            String currentUrl = this.pagesToVisit.get(0);
            SpiderLeg leg = new SpiderLeg();
            BaseWithLinks baseWithLinks = leg.crawlAndGather(currentUrl, titleToSearchFor);
            if (baseWithLinks.getRetrievedObject() != null) {
                if (titleToSearchFor == null) {
                    this.retrievedObjects.add(baseWithLinks.getRetrievedObject());
                }// TODO leave this logic here for now. Maybe move to Leg later.
                else if (Objects.equals(baseWithLinks.getRetrievedObject().getName(), titleToSearchFor)) {
                    this.retrievedObjects.add(baseWithLinks.getRetrievedObject());
                    break;
                }
            }
            this.pagesVisited.add(currentUrl);
            this.pagesToVisit.remove(0);
            // TODO make this sets? uniqueness
            for (String newUrl : baseWithLinks.getRetrievedLinks()) {
                if (!this.pagesVisited.contains(newUrl) && !pagesToVisit.contains(newUrl)) {
                    pagesToVisit.add(newUrl);
                }
            }
        }
        return this.retrievedObjects;
    }
}