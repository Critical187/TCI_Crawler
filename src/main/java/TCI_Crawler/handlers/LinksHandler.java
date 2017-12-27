package TCI_Crawler.handlers;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinksHandler {

    private List<String> forbiddenLinks = Arrays.asList("facebook", "twitter");

    public List<String> getValidLinks(Document htmlDocument) {
        Elements linksOnPage = htmlDocument.select("a[href]");
        return linksOnPage
                .stream()
                .map(x -> x.absUrl("href"))
                .filter(x -> this.forbiddenLinks.stream().noneMatch(x::contains))
                .collect(Collectors.toList());
    }
}
