package handlers;

import exceptions.InvalidCategoryException;
import searchObjects.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

public class SearchObjectHandler {

    public SearchObjectBase getSearchObjects(Document htmlDocument) throws InvalidCategoryException {
        Elements allTables = htmlDocument.select("div.media-details");
        return allTables.size() == 1
                ? this.processDetails(this.getDetails(allTables.get(0)))
                : null;
    }

    private Map<String, String> getDetails(Element detailsTable) {
        String stringTitle = detailsTable.getElementsByTag("h1").get(0).text();
        List<String> tableDefinitions = detailsTable
                .getElementsByTag("th").stream().map(Element::text).collect(Collectors.toList());
        List<String> tableValues = detailsTable
                .getElementsByTag("td").stream().map(Element::text).collect(Collectors.toList());
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("Title", stringTitle);
        for (int i = 0; i < tableDefinitions.size(); i++) {
            dictionary.put(tableDefinitions.get(i), tableValues.get(i));
        }

        return dictionary;
    }

    private SearchObjectBase processDetails(Map<String, String> dictionary) throws InvalidCategoryException {
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
                throw new InvalidCategoryException(String.format("Unknown category '%s' found.", baseType));
        }
    }
}
