package TCI_Crawler.handlers;

import TCI_Crawler.searchObjects.SearchDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A class, that stores and retrieves details about a specific crawl.
 */
public class DetailsStorageHandler {

    /**
     * The identifier to be used when identifying a single search.
     */
    private int id = 0;

    /**
     * A dictionary to store the identifier and the search that is related to the identifier.
     */
    private final Map<Integer, SearchDetails> detailsMap = new HashMap<>();

    /**
     * Adds a {@link SearchDetails} object to the {@link DetailsStorageHandler#detailsMap}.
     *
     * @param details The details to be added.
     */
    public void addDetails(SearchDetails details) {
        this.detailsMap.put(details.getID(), details);
    }

    /**
     * Retrieves an {@link Optional<SearchDetails>} object, representing the search details, identified by the given id
     * or the absence of such details.
     *
     * @param id The id to return the search details for.
     * @return An {@link Optional<SearchDetails>} object, representing a search detail or its absence.
     */
    public Optional<SearchDetails> getDetails(Integer id) {
        SearchDetails details = this.detailsMap.get(id);
        if (details == null) {
            return Optional.empty();
        }
        return Optional.of(details);
    }

    /**
     * Gets the next identifier that can be used to identify a {@link SearchDetails} object.
     *
     * @return The next available unique identifier.
     */
    public int getNextId() {
        id++;
        return id;
    }
}
