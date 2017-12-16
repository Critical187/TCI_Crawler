package searchObjects;

public abstract class SearchObjectBase {

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
}
