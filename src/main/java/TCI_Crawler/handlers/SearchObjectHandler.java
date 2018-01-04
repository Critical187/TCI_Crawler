package TCI_Crawler.handlers;

import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.searchObjects.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class, that extracts a single {@link SearchObjectBase} from a given HTML {@link Document}.
 */
public class SearchObjectHandler {

    /**
     * Scans the given HTML document and retrieves a {@link SearchObjectBase}. Can also return null
     * if no object was found on the document.
     *
     * @param htmlDocument The HTML document to scan.
     * @return A {@link SearchObjectBase} or null if no search object was found on the document.
     * @throws InvalidCategoryException If a search object was found but its category is different from the allowed
     *                                  ones : 'Music', 'Books' or 'Movies'.
     */
    public SearchObjectBase getSearchObjects(Document htmlDocument) throws InvalidCategoryException {
        Elements allTables = htmlDocument.select("div.media-details");
        // If there is exactly one table for a search object then parse it into a search object, else
        // return null.
        return allTables.size() == 1
                ? this.processDetails(this.getDetails(allTables.get(0)))
                : null;
    }

    /**
     * Retrieves a map that represents the {@link SearchObjectBase}'s properties from the given HTML element.
     *
     * @param detailsTable The HTML element to retrieve the properties from.
     * @return A map of {@link SearchObjectBase}'s properties. The key represents the name of the property and the value
     * represents the property's value itself.
     */
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

    /**
     * Maps the given properties and returns a new {@link SearchObjectBase} instance.
     *
     * @param dictionary The map to retrieve the properties and their values from.
     * @return A new instance of {@link SearchObjectBase}.
     * @throws InvalidCategoryException If the category of the found object is different from the allowed ones:
     *                                  'Music', 'Movies', 'Books'.
     */
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
                        dictionary.get("Writers").split(", "),
                        dictionary.get("Stars").split(", "));
            case "Books":
                return new Book(
                        name,
                        genre,
                        year,
                        format,
                        dictionary.get("Authors").split(", "),
                        dictionary.get("ISBN"),
                        dictionary.get("Publisher"));
            case "Music":
                return new Music(name, genre, year, format, dictionary.get("Artist"));
            default:
                throw new InvalidCategoryException(String.format("Unknown category '%s' found.", baseType));
        }
    }
}
