package searchObjects;

public class Book extends SearchObjectBase {

    private final String[] authors;
    private final String isbn;
    private final String publisher;

    public Book(String name, String genre, int year, String format, String[] authors, String isbn, String publisher) {
        super(name, genre, year, format);
        this.authors = authors;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }
}
