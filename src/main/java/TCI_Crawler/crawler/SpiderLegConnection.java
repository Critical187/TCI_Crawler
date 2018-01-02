package TCI_Crawler.crawler;

import java.io.IOException;

import TCI_Crawler.exceptions.*;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SpiderLegConnection {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private final SpiderLeg spiderLeg;

    public SpiderLegConnection() {
        this.spiderLeg = new SpiderLeg(new LinksHandler(), new SearchObjectHandler());
    }

    public SearchObjectWithLinks crawlAndGather(String url)
            throws InvalidSiteException, InvalidCategoryException {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();

            if (!connection.response().contentType().contains("text/html")) {
                throw new InvalidSiteException(String.format("Website at URL '%s' does not contain HTML.", url));
            }
            if (connection.response().statusCode() != 200) {
                throw new InvalidSiteException(String.format("Could not establish connection to URL '%s'.", url));
            }

            return this.spiderLeg.getObjectAndLinks(htmlDocument);
        } catch (IOException ioe) {
            throw new InvalidSiteException(String.format("Could not establish connection to URL '%s'.", url));
        }
    }
}