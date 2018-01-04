package TCI_Crawler.handlers;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class, that extracts all links from a given HTML {@link Document}.
 */
public class LinksHandler {

    /**
     * Represents a list of links that should be ignored when retrieving the valid links.
     */
    private static List<String> forbiddenLinksList = Arrays.asList("facebook", "twitter");

    /**
     * Represents the forbidden links list in a read-only list. Trying to add elements to this list will result in a
     * {@link UnsupportedOperationException}.
     */
    public static List<String> forbiddenLinks = Collections.unmodifiableList(forbiddenLinksList);

    /**
     * Retrieves all the valid links from a given HTML document.
     *
     * @param htmlDocument The HTML document to scan.
     * @return A list of valid links, that were found in the HTML document.
     */
    public List<String> getValidLinks(Document htmlDocument) {
        Elements linksOnPage = htmlDocument.select("a[href]");
        return linksOnPage
                .stream()
                .map(x -> x.absUrl("href"))
                .filter(x -> LinksHandler.forbiddenLinks.stream().noneMatch(x::contains))
                .distinct()
                .collect(Collectors.toList());
    }
}
