package TCI_Crawler.searchObjects;

/**
 * A class, that represents a book object, found by the web crawler.
 */
public class Book extends SearchObjectBase {

    /**
     * Represents the authors that have participated in writing the book.
     */
    private final String[] authors;

    /**
     * Represents the ISBN of the book.
     */
    private final String isbn;

    /**
     * Represents the publisher of the book.
     */
    private final String publisher;

    /**
     * Initializes a new instance of the {@link Book} class.
     *
     * @param name      Value for {@link Book#name}
     * @param genre     Value for {@link Book#genre}
     * @param year      Value for {@link Book#year}
     * @param format    Value for {@link Book#format}
     * @param authors   Value for {@link Book#authors}
     * @param isbn      Value for {@link Book#isbn}
     * @param publisher Value for {@link Book#publisher}
     */
    public Book(String name, String genre, int year, String format, String[] authors, String isbn, String publisher) {
        super(name, genre, year, format);
        this.authors = authors;
        this.isbn = isbn;
        this.publisher = publisher;
    }
}
