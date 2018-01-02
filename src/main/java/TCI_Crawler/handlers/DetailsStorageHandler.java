package TCI_Crawler.handlers;

import TCI_Crawler.searchObjects.SearchDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DetailsStorageHandler {

    private int id = 0;
    private final Map<Integer, SearchDetails> detailsMap = new HashMap<>();

    public void addDetails(SearchDetails details) {
        this.detailsMap.put(details.getID(), details);
    }

    public Optional<SearchDetails> getDetails(Integer id) {
        SearchDetails details = this.detailsMap.get(id);
        if (details == null) {
            return Optional.empty();
        }
        return Optional.of(details);
    }

    public int getNextId() {
        return id++;
    }
}
