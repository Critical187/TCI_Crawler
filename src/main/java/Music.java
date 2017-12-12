public class Music extends Base {
    public String artist;

    public Music(String genre, String year, String format, String artist) {
        super(genre, year, format);
        this.artist = artist;
    }
}
