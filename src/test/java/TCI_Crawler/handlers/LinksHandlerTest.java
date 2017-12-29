package TCI_Crawler.handlers;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class LinksHandlerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"<html><body>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=books\">Books</a>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                        "</body></html>",
                        3,
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies")
                },
                {"<html><body>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php?cat=movies\">Movies</a>" +
                        "</body></html>",
                        2,
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies")
                },
                {"<html><body>" +
                        "<a href=\"http://i315379.hera.fhict.nl/catalog.php\">Personal Media Library</a>" +
                        "<a href=\"http://facebook.com\">Facebook</a>" +
                        "<a href=\"http://twitter.com\">Twitter</a>" +
                        "</body></html>",
                        1,
                        Arrays.asList("http://i315379.hera.fhict.nl/catalog.php")
                }
        });
    }

    private final String htmlString;
    private final int expectedArraySize;
    private final List<String> expectedLinks;

    @InjectMocks
    private LinksHandler links;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.links = new LinksHandler();
    }

    public LinksHandlerTest(String htmlString, int expectedArraySize, List<String> expectedLinks) {
        this.htmlString = htmlString;
        this.expectedArraySize = expectedArraySize;
        this.expectedLinks = expectedLinks;
    }

    @Test
    public void testGetValidLinks() {
        Document htmlDoc = Jsoup.parse(this.htmlString);
        List<String> validLinks = this.links.getValidLinks(htmlDoc);
        assertArrayEquals(this.expectedLinks.toArray(), validLinks.toArray());
        assertEquals(this.expectedArraySize, validLinks.size());
    }
}