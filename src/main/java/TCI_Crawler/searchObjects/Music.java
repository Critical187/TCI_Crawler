package TCI_Crawler.searchObjects;

public class Music extends SearchObjectBase {

    private final String artist;

    public Music(String name, String genre, int year, String format, String artist) {
        super(name, genre, year, format);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }
}