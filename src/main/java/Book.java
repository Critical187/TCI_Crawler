public class Book extends Base {
    private String[] authors;
    private String isbn;
    private String publisher;

    public Book(String name, String genre, int year, String format, String[] authors, String isbn, String publisher) {
        super(name, genre, year, format);
        this.authors = authors;
        this.isbn = isbn;
        this.publisher = publisher;
    }
}
