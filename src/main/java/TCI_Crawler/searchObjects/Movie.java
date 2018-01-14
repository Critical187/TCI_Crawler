package TCI_Crawler.searchObjects;

/**
 * A class, that represents a movie object, found by the web crawler.
 */
public class Movie extends SearchObjectBase {

    /**
     * Represents the director of the movie.
     */
    private final String director;

    /**
     * Represents the writers of the movie's script.
     */
    private final String[] writers;

    /**
     * Represents the stars that have participated in the movie.
     */
    private final String[] stars;

    /**
     * Initializes a new instance of the {@link Movie} class.
     *
     * @param name     Value for {@link Movie#name}
     * @param genre    Value for {@link Movie#genre}
     * @param year     Value for {@link Movie#year}
     * @param format   Value for {@link Movie#format}
     * @param director Value for {@link Movie#director}
     * @param writers  Value for {@link Movie#writers}
     * @param stars    Value for {@link Movie#stars}
     */
    public Movie(
            String name,
            String genre,
            int year,
            String format,
            String director,
            String[] writers,
            String[] stars) {
        super(name, genre, year, format);
        this.director = director;
        this.writers = writers;
        this.stars = stars;
    }
}
