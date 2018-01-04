package TCI_Crawler.searchObjects;

/**
 * An abstract class, that represents an object, retrieved from the performed crawling on a given page.
 */
public abstract class SearchObjectBase implements Comparable<SearchObjectBase> {

    /**
     * The genre of the retrieved object.
     */
    private final String genre;

    /**
     * The year that the retrieved object was created or produced.
     */
    private final int year;

    /**
     * The format in which the retrieved object is distributed.
     */
    private final String format;

    /**
     * The name of the retrieved object.
     */
    private final String name;

    /**
     * Initializes a new instance of the {@link SearchObjectBase} class.
     *
     * @param name   Value for {@link SearchObjectBase#name}
     * @param genre  Value for {@link SearchObjectBase#genre}
     * @param year   Value for {@link SearchObjectBase#year}
     * @param format Value for {@link SearchObjectBase#format}
     */
    public SearchObjectBase(String name, String genre, int year, String format) {
        this.name = name;
        this.genre = genre;
        this.format = format;
        this.year = year;
    }

    /**
     * @return Gets the genre of the retrieved object.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @return Gets the year of the retrieved object.
     */
    public int getYear() {
        return year;
    }

    /**
     * @return Gets the format of the retrieved object.
     */
    public String getFormat() {
        return format;
    }

    /**
     * @return Gets the name of the retrieved object.
     */
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(SearchObjectBase o) {
        int value = 0;
        SearchObjectBase other = (SearchObjectBase) o;
        value = this.name.compareTo(other.name);
        return value;
    }

    @Override
    public String toString() {
        return "SearchObjectBase{" +
                "genre='" + genre + '\'' +
                ", year=" + year +
                ", format='" + format + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
