package TCI_Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

public  class TestUtils {

    public static Document LinksToHtmlDocument(List<String> links) {
        String linksHtml = links.stream().map(x -> String.format("<a href=\"%s\"</a>", x)).collect(Collectors.joining());
        String htmlString = String.format(
                "<html><body>%s</body></html>", linksHtml);

        return Jsoup.parse(htmlString);
    }
}
