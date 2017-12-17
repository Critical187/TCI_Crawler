package TCI_Crawler.searchObjects;

public class Movie extends SearchObjectBase {

    private final String director;
    private final String[] writers;
    private final String[] stars;

    public Movie(String name, String genre, int year, String format, String director, String[] writers, String[] stars) {
        super(name, genre, year, format);
        this.director = director;
        this.writers = writers;
        this.stars = stars;
    }

    public String getDirector() {
        return director;
    }

    public String[] getWriters() {
        return writers;
    }

    public String[] getStars() {
        return stars;
    }
}
