package TCI_Crawler.dto;

import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class SearchResult {

    private final int id;
    private final ArrayList<Book> books;
    private final ArrayList<Music> music;
    private final ArrayList<Movie> movies;
    private final long time;

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

        /*this.sort();*/
    }

    private void sort() {
        Collator usCollator = Collator.getInstance(Locale.US);
        this.books.sort((o1, o2) -> usCollator.compare(o1.getName(), o2.getName()));
        this.music.sort((o1, o2) -> usCollator.compare(o1.getName(), o2.getName()));
        this.movies.sort((o1, o2) -> usCollator.compare(o1.getName(), o2.getName()));
    }
}
