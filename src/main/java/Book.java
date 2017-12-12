public class Book extends Base {
    String author;
    String isbn;
    String publisher;

    public Book(String genre, String year, String format, String author, String isbn, String publisher) {
        super(genre, year, format);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
    }
}
