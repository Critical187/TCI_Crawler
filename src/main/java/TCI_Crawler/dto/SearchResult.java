package TCI_Crawler.dto;

import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A class, that represents the data transfer object, sent through the API that represents the result of a single crawl.
 */
public class SearchResult {

    /**
     * Represents the identifier of the crawl.
     */
    private final int id;

    /**
     * Represents the books that were found during the crawl.
     */
    private final ArrayList<Book> books;

    /**
     * Represents the music that was found during the crawl.
     */
    private final ArrayList<Music> music;

    /**
     * Represents the movies that were found during the crawl.
     */
    private final ArrayList<Movie> movies;

    /**
     * Represents the time in milliseconds at which point the crawl was finished.
     */
    private final long time;

    /**
     * Initializes a new instance of the {@link SearchResult} class.
     *
     * @param id               Value for {@link SearchResult#id}
     * @param retrievedObjects All search objects that will be sent through the API in this instance.
     * @param time             Value for {@link SearchResult#time}
     */
    public SearchResult(int id, ArrayList<SearchObjectBase> retrievedObjects, long time) {
        this.id = id;
        this.time = time;
        this.books = retrievedObjects.stream()
                .filter(x -> x instanceof Book)
                .map(x -> (Book) x)
                .collect(Collectors.toCollection(ArrayList::new));
        this.music = retrievedObjects.stream()
                .filter(x -> x instanceof Music)
                .map(x -> (Music) x)
                .collect(Collectors.toCollection(ArrayList::new));
        this.movies = retrievedObjects.stream()
                .filter(x -> x instanceof Movie)
                .map(x -> (Movie) x)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
