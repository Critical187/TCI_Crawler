package TCI_Crawler.searchObjects;

/**
 * A class, that represents a music object, found by the web crawler.
 */

public class Music extends SearchObjectBase {

    /**
     * Represents the artist who has created the song.
     */
    private final String artist;

    /**
     * Initializes a new instance of the {@link Music} class.
     *
     * @param name   Value for {@link Music#name}
     * @param genre  Value for {@link Music#genre}
     * @param year   Value for {@link Music#year}
     * @param format Value for {@link Music#format}
     * @param artist Value for {@link Music#artist}
     */
    public Music(String name, String genre, int year, String format, String artist) {
        super(name, genre, year, format);
        this.artist = artist;
    }
}
