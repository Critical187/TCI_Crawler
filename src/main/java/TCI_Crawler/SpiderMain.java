package TCI_Crawler;

import TCI_Crawler.crawler.Spider;
import TCI_Crawler.dto.SearchResult;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.List;

public class SpiderMain {
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     *
     * @param args - not used
     */
    public static void main(String[] args) {
        Spider spider = new Spider();
        SearchObjectBase base;
        try {
            File path = new File(System.getProperty("user.dir") + "/TestPages/TestPage.html");
            Document mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
            SearchObjectHandler objHandler = new SearchObjectHandler();
            base = objHandler.getSearchObjects(mDoc);
            SearchResult b = spider.search("http://i315379.hera.fhict.nl/", null);
            String  z = ";";

        } catch (Exception e) {

        }
    }
}