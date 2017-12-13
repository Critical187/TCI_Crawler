public class Music extends Base {
    private String artist;

    public Music(String name, String genre, String year, String format, String artist) {
        super(name, genre, year, format);
        this.artist = artist;
    }
}
