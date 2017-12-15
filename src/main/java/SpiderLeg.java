import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    public BaseWithLinks crawlAndGather(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();

            if (!connection.response().contentType().contains("text/html") || connection.response().statusCode() == 200) {
                // TODO throw exception if not an html page or if connection was unsuccessful?
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            Elements allTables = htmlDocument.select("div.media-details");
            if (allTables.size() != 1) {
                // TODO null as param?
                return new BaseWithLinks(null,this.getValidLinks(linksOnPage));
            }
            Element detailsTable = allTables.get(0);
            Map<String, String> dictionary = this.getDetails(detailsTable);
            return new BaseWithLinks(this.processDetails(dictionary), this.getValidLinks(linksOnPage));
        } catch (IOException ioe) {
            // We were not successful in our HTTP request
            // TODO propagate exception to controller. remove return null statement.
            return null;
        }
    }

    private List<String> getValidLinks(Elements linksOnPage) {
        // TODO add extra 'forbidden links' ?
        return linksOnPage
                .stream()
                .map(x -> x.absUrl("href"))
                .filter(x -> !(x.contains("facebook") || x.contains("twitter")))
                .collect(Collectors.toList());
    }

    private Map<String,String> getDetails(Element detailsTable) {
        Element title = detailsTable.getElementsByTag("h1").get(0);
        String stringTitle = title.text();
        List<String> tableDefinitions = detailsTable
                .getElementsByTag("th").stream().map(Element::text).collect(Collectors.toList());
        List<String> tableValues = detailsTable
                .getElementsByTag("td").stream().map(Element::text).collect(Collectors.toList());
        Map<String, String> dictionary = new HashMap<>();
        if (tableDefinitions.size() != tableValues.size()) {
            // TODO throw exception.
        }
        dictionary.put("Title", stringTitle);
        for (int i = 0; i < tableDefinitions.size(); i++) {
            dictionary.put(tableDefinitions.get(i), tableValues.get(i));
        }

        return dictionary;
    }

    private Base processDetails(Map<String, String> dictionary) {
        String baseType = dictionary.get("Category");
        String name = dictionary.get("Title");
        String genre = dictionary.get("Genre");
        String format = dictionary.get("Format");
        int year = Integer.parseInt(dictionary.get("Year"));
        switch (baseType) {
            case "Movies":
                return new Movie(
                        name,
                        genre,
                        year,
                        format,
                        dictionary.get("Director"),
                        dictionary.get("Writers").split(","),
                        dictionary.get("Stars").split(","));
            case "Books":
                return new Book(
                        name,
                        genre,
                        year,
                        format,
                        dictionary.get("Authors").split(","),
                        dictionary.get("Publisher"),
                        dictionary.get("ISBN"));
            case "Music":
                return new Music(name, genre, year, format, dictionary.get("Artist"));
            default:
                // TODO change to exception?
                return null;
        }
    }
}