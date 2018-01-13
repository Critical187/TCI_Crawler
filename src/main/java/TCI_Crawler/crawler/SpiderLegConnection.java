package TCI_Crawler.crawler;

import java.io.IOException;

import TCI_Crawler.exceptions.*;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.*;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * A class, that can open a connection to a given URL, extract the HTML document and use a {@link SpiderLeg} to parse
 * the HTML document.
 */
public class SpiderLegConnection {

    /**
     * The user agent such that the crawler can fake a real person, browsing the website.
     */
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    /**
     * The spider leg to be used to retrieve the contents of the HTML document.
     */
    private final SpiderLeg spiderLeg;

    /**
     * Initializes a new instance of the {@link SpiderLegConnection} class.
     */
    public SpiderLegConnection() {
        this.spiderLeg = new SpiderLeg(new LinksHandler(), new SearchObjectHandler());
    }

    /**
     * Performs a crawl on the given URL and gathers the object and all links found there. The found
     * object may be null if there was no such object on the document.
     *
     * @param url The url to crawl through.
     * @return The found object and all links.
     * @throws InvalidCategoryException If a search object was found but its category is different from the allowed
     *                                  ones : 'Music', 'Books' or 'Movies'.
     * @throws InvalidSiteException     When a connection to the site could not be established.
     */
    public SearchObjectWithLinks crawlAndGather(String url)
            throws InvalidCategoryException, InvalidSiteException {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();

            if (!connection.response().contentType().contains("text/html")) {
                   throw new InvalidSiteException(String.format("Website at URL '%s' does not contain HTML.", url));
            }
            if (connection.response().statusCode() != HttpStatus.SC_OK) {
                throw new InvalidSiteException("Status code of returned request is different than '200'");
            }

            return this.spiderLeg.getObjectAndLinks(htmlDocument);
        } catch (IOException ioe) {
            throw new InvalidSiteException(String.format("Could not establish connection to URL '%s'.", url));
        }
    }
}