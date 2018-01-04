package TCI_Crawler.handlers;

import TCI_Crawler.searchObjects.CrawlDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A class, that stores and retrieves details about a specific crawl.
 */
public class DetailsStorageHandler {

    /**
     * The identifier to be used when identifying a single crawl.
     */
    private int id = 0;

    /**
     * A dictionary to store the identifier and the crawl that is related to the identifier.
     */
    private final Map<Integer, CrawlDetails> detailsMap = new HashMap<>();

    /**
     * Adds a {@link CrawlDetails} object to the {@link DetailsStorageHandler#detailsMap}.
     *
     * @param details The details to be added.
     */
    public void addDetails(CrawlDetails details) {
        this.detailsMap.put(details.getID(), details);
    }

    /**
     * Retrieves an {@link Optional<CrawlDetails>} object, representing the crawl details, identified by the given id
     * or the absence of such details.
     *
     * @param id The id to return the crawl details for.
     * @return An {@link Optional<CrawlDetails>} object, representing a crawl details object or its absence.
     */
    public Optional<CrawlDetails> getDetails(Integer id) {
        CrawlDetails details = this.detailsMap.get(id);
        if (details == null) {
            return Optional.empty();
        }
        return Optional.of(details);
    }

    /**
     * Gets the next identifier that can be used to identify a {@link CrawlDetails} object.
     *
     * @return The next available unique identifier.
     */
    public int getNextId() {
        return id++;
    }
}
