public abstract class Base {
    private String genre;
    private int year;
    private String format;
    private String name;

    public Base(String name, String genre, int year, String format){
        this.name = name;
        this.genre = genre;
        this.format = format;
        this.year = year;
    }
}
