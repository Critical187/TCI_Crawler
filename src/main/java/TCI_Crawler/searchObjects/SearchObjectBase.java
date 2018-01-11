package TCI_Crawler.searchObjects;

public abstract class SearchObjectBase implements Comparable<SearchObjectBase> {

    private final String genre;
    private final int year;
    private final String format;
    private final String name;

    public SearchObjectBase(String name, String genre, int year, String format) {
        this.name = name;
        this.genre = genre;
        this.format = format;
        this.year = year;

    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public String getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }
    @Override
    public int compareTo(SearchObjectBase other) {
        int value = 0;
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
