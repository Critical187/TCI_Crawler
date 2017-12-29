package TCI_Crawler.handlers;

import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.Music;
import TCI_Crawler.searchObjects.SearchObjectBase;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import org.jsoup.nodes.Document;
import org.mockito.*;

import java.io.File;

import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class SearchObjectHandlerTest {

    @Mock
    private Document mDoc;

    @InjectMocks
    private SearchObjectHandler objHandler;

    private File path;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSearchObject_Music() throws Exception {
        path = new File(System.getProperty("user.dir") + "/TestPages/TestPage_Music.html");
        mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
        SearchObjectBase base = objHandler.getSearchObjects(mDoc);
        SearchObjectBase expected = new Music(
                "Beethoven: Complete Symphonies",
                "Clasical",
                2012,
                "CD",
                "Ludwig van Beethoven");
        assertThat(expected, samePropertyValuesAs(base));
    }

    @Test
    public void testGetSearchObject_Movie() throws Exception {
        path = new File(System.getProperty("user.dir") + "/TestPages/TestPage_Movie.html");
        mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
        SearchObjectBase base = objHandler.getSearchObjects(mDoc);
        SearchObjectBase expected = new Movie(
                "Forrest Gump",
                "Drama",
                1994,
                "DVD",
                "Robert Zemeckis",
                new String[]{"Winston Groom", "Eric Roth"},
                new String[]{"Tom Hanks", "Rebecca Williams", "Sally Field", "Michael Conner Humphreys"}
        );
        assertThat(expected, samePropertyValuesAs(base));
    }

    @Test
    public void testGetSearchObject_Book() throws Exception {
        path = new File(System.getProperty("user.dir") + "/TestPages/TestPage_Book.html");
        mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
        SearchObjectBase base = objHandler.getSearchObjects(mDoc);
        SearchObjectBase expected = new Book(
                "Clean Code: A Handbook of Agile Software Craftsmanship",
                "Tech",
                2008,
                "Ebook",
                new String[]{ "Robert C. Martin"},
                "978-0132350884",
                "Prentice Hall"
        );
        assertThat(expected, samePropertyValuesAs(base));
    }

    @Test(expected = InvalidCategoryException.class)
    public void testGetSearchObject_ThrowInvalidCategoryException() throws Exception {
        path = new File(System.getProperty("user.dir") + "/TestPages/TestPage_InvalidCategory.html");
        mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
        SearchObjectBase base = objHandler.getSearchObjects(mDoc);
    }

}