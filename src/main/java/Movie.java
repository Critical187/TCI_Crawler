public class Movie extends Base {
    private String director;
    private String[] writers;
    private String[] stars;

    public Movie(String name, String genre, int year, String format, String director, String[] writers, String[] stars) {
        super(name, genre, year, format);
        this.director = director;
        this.writers = writers;
        this.stars = stars;
    }
}
