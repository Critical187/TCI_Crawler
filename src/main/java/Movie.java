public class Movie extends Base {
    public String director;
    public String[] writers;
    public String[] stars;

    public Movie(String genre, String year, String format, String director, String[] writers, String[] stars) {
        super(genre, year, format);
        this.director = director;
        this.writers = writers;
        this.stars = stars;
    }
}
