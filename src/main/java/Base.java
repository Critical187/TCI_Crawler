public abstract class Base {
    protected String genre;
    String year;
    String format;

    public Base(String genre, String year, String format){
        this.genre = genre;
        this.format = format;
        this.year = year;
    }
}
