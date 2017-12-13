public abstract class Base {
    private String genre;
    private String year;
    private String format;
    private String name;

    public Base(String name, String genre, String year, String format){
        this.name = name;
        this.genre = genre;
        this.format = format;
        this.year = year;
    }
}
