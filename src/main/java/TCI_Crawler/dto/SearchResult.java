package TCI_Crawler.dto;

import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchResult {

    private ArrayList<Book> books;
    private ArrayList<Music> music;
    private ArrayList<Movie> movies;

    private final long time;

    public SearchResult(ArrayList<SearchObjectBase> retrievedObjects, long time) {
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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Music> getMusic() {
        return music;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public long getTime() {
        return time;
    }
}
