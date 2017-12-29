package TCI_Crawler.handlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
public class LinksHandlerTest {

=======


public class LinksHandlerTest {

    @Mock
    private List<String> forbiddenLinks;

    @Mock
    private Document mDoc;

>>>>>>> cda868dfb7695fcc7a30c2f3019a8aa813c17b34
    @InjectMocks
    private LinksHandler links;

    @Before
    public void setUp() throws Exception {
<<<<<<< HEAD
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetValidLinks() {
        String validLinksString = "<html><body>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=books\">Books</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                "</body></html>";
        Document htmlDoc = Jsoup.parse(validLinksString);
        List<String> validLinks = links.getValidLinks(htmlDoc);
        List<String> expectedLinks = Arrays.asList(
                "http://i315379.hera.fhict.nl/catalog.php",
                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                "http://i315379.hera.fhict.nl/catalog.php?cat=movies");
        assertEquals(expectedLinks, validLinks);
    }

    @Test
    public void testGetValidLinksWithNoDuplicates() {
        String validLinksString = "<html><body>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=books\">Books</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                "</body></html>";
        Document htmlDoc = Jsoup.parse(validLinksString);
        List<String> validLinks = links.getValidLinks(htmlDoc);
        List<String> expectedLinks = Arrays.asList(
                "http://i315379.hera.fhict.nl/catalog.php",
                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                "http://i315379.hera.fhict.nl/catalog.php?cat=movies");
        assertEquals(expectedLinks, validLinks);
    }

    @Test
    public void testGetValidLinksWithNoForbiddenLinks() {
        String validLinksString = "<html><body>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=books\">Books</a>" +
                "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                "<a href=\"http://facebook.com\">Facebook</a>" +
                "<a href=\"http://twitter.com\">Movies</a>" +
                "</body></html>";
        Document htmlDoc = Jsoup.parse(validLinksString);
        List<String> validLinks = links.getValidLinks(htmlDoc);
        List<String> expectedLinks = Arrays.asList(
                "http://i315379.hera.fhict.nl/catalog.php",
                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                "http://i315379.hera.fhict.nl/catalog.php?cat=movies");
        assertEquals(expectedLinks, validLinks);
=======
        forbiddenLinks = Arrays.asList("movies");
        MockitoAnnotations.initMocks(this);
        File path = new File(System.getProperty("user.dir") + "/TestPages/TestPage.html");
        mDoc = Jsoup.parse(path, "UTF-8", "http://i315379.hera.fhict.nl/");
    }

    @Test
    public void testGetValidLinks() throws Exception {
        List<String> els = links.getValidLinks(mDoc);
        List<String> validLinks = Arrays.asList(
                "http://i315379.hera.fhict.nl/catalog.php",
                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                "http://i315379.hera.fhict.nl/catalog.php?cat=movies");
        assertEquals(validLinks, els);
>>>>>>> cda868dfb7695fcc7a30c2f3019a8aa813c17b34
    }
}