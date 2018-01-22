package TCI_Crawler.handlers;

import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;
import org.junit.*;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jsoup.nodes.Document;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;


public class SearchObjectHandlerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private SearchObjectHandler objHandler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDetailsForMusic() throws InvalidCategoryException {
        String htmlString = "<html><body>" +
                "<div class=\"media-details\">" +
                "<h1>Beethoven: Complete Symphonies</h1>" +
                "<table>" +
                "<tr><th>Category</th><td>Music</td></tr>" +
                "<tr><th>Genre</th><td>Classical</td></tr>" +
                "<tr><th>Format</th><td>CD</td></tr>" +
                "<tr><th>Year</th><td>2012</td></tr>" +
                "<tr><th>Artist</th><td>Ludwig van Beethoven</td></tr>" +
                "</table></div></body></html>";
        Document htmlDocument = Jsoup.parse(htmlString);
        SearchObjectBase actualObject = this.objHandler.getSearchObjects(htmlDocument);
        Music expectedObject = new Music(
                "Beethoven: Complete Symphonies",
                "Classical",
                2012,
                "CD",
                "Ludwig van Beethoven");

        // Assert that both objects are of the same class.
        assertEquals(expectedObject.getClass(), actualObject.getClass());
        // Assert that both objects have the exact same properties.
        assertThat(expectedObject, samePropertyValuesAs(actualObject));
    }

    @Test
    public void testGetDetailsForMovie() throws InvalidCategoryException {
        String htmlString = "<html><body>" +
                "<div class=\"media-details\">" +
                "<h1>Forrest Gump</h1>" +
                "<table>" +
                "<tr><th>Category</th><td>Movies</td></tr>" +
                "<tr><th>Genre</th><td>Drama</td></tr>" +
                "<tr><th>Format</th><td>DVD</td></tr>" +
                "<tr><th>Year</th><td>1994</td></tr>" +
                "<tr><th>Director</th><td>Robert Zemeckis</td></tr>" +
                "<tr><th>Writers</th><td>Winston Groom, Eric Roth</td></tr>" +
                "<tr><th>Stars</th><td>Tom Hanks, Rebecca Williams, Sally Field</td></tr>" +
                "</table></div></body></html>";
        Document htmlDocument = Jsoup.parse(htmlString);
        SearchObjectBase actualObject = this.objHandler.getSearchObjects(htmlDocument);
        Movie expectedObject = new Movie(
                "Forrest Gump",
                "Drama",
                1994,
                "DVD",
                "Robert Zemeckis",
                new String[]{"Winston Groom", "Eric Roth"},
                new String[]{"Tom Hanks", "Rebecca Williams", "Sally Field"});

        // Assert that both objects are of the same class.
        assertThat(expectedObject.getClass(),is(equalTo(actualObject.getClass())));
        // Assert that both objects have the exact same properties.
        assertThat(expectedObject, samePropertyValuesAs(actualObject));
    }

    @Test
    public void testGetDetailsForBook() throws InvalidCategoryException {
        String htmlString = "<html><body>" +
                "<div class=\"media-details\">" +
                "<h1>Clean Code: A Handbook of Agile Software Craftsmanship</h1>" +
                "<table>" +
                "<tr><th>Category</th><td>Books</td></tr>" +
                "<tr><th>Genre</th><td>Tech</td></tr>" +
                "<tr><th>Format</th><td>Ebook</td></tr>" +
                "<tr><th>Year</th><td>2008</td></tr>" +
                "<tr><th>Authors</th><td>Robert C. Martin</td></tr>" +
                "<tr><th>ISBN</th><td>978-0132350884</td></tr>" +
                "<tr><th>Publisher</th><td>Prentice Hall</td></tr>" +
                "</table></div></body></html>";
        Document htmlDocument = Jsoup.parse(htmlString);
        SearchObjectBase actualObject = this.objHandler.getSearchObjects(htmlDocument);
        Book expectedObject = new Book(
                "Clean Code: A Handbook of Agile Software Craftsmanship",
                "Tech",
                2008,
                "Ebook",
                new String[]{"Robert C. Martin"},
                "978-0132350884",
                "Prentice Hall");

        // Assert that both objects are of the same class.
        assertThat(expectedObject.getClass(),is(equalTo(actualObject.getClass())));
        // Assert that both objects have the exact same properties.
        assertThat(expectedObject, samePropertyValuesAs(actualObject));
    }

    @Test
    public void testGetDetailsForInvalidCategoryObject() throws InvalidCategoryException {
        // Set the expect exception to expect InvalidCategoryException and a certain message.
        thrown.expect(InvalidCategoryException.class);
        thrown.expectMessage("Unknown category 'Songs' found.");

        String htmlString = "<html><body>" +
                "<div class=\"media-details\">" +
                "<h1>Clean Code: A Handbook of Agile Software Craftsmanship</h1>" +
                "<table>" +
                "<tr><th>Category</th><td>Songs</td></tr>" +
                "<tr><th>Genre</th><td>Tech</td></tr>" +
                "<tr><th>Format</th><td>Ebook</td></tr>" +
                "<tr><th>Year</th><td>2008</td></tr>" +
                "</table></div></body></html>";
        Document htmlDocument = Jsoup.parse(htmlString);

        // getSearchObjects(..) will throw an exception since the category of the object is 'Songs'.
        this.objHandler.getSearchObjects(htmlDocument);
    }

    @Test
    public void testGetDetailsForNoObject() throws InvalidCategoryException {
        String htmlString = "<html><body></body></html>";
        Document htmlDocument = Jsoup.parse(htmlString);
        SearchObjectBase retrievedObject = this.objHandler.getSearchObjects(htmlDocument);

        // Assert that an HTML page with no div of class 'media-details' will simply return a null object.
        assertNull(retrievedObject);
    }
}