package TCI_Crawler.handlers;

import TCI_Crawler.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class LinksHandlerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies"),
                        3,
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=books",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies")
                },
                {
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies"),
                        2,
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://i315379.hera.fhict.nl/catalog.php?cat=movies")
                },
                {
                        Arrays.asList(
                                "http://i315379.hera.fhict.nl/catalog.php",
                                "http://facebook.com",
                                "http://twitter.com"),
                        1,
                        Arrays.asList("http://i315379.hera.fhict.nl/catalog.php")
                }
        });
    }

    private final Document htmlDocument;
    private final int expectedArraySize;
    private final List<String> expectedLinks;

    @InjectMocks
    private LinksHandler links;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.links = new LinksHandler();
    }

    public LinksHandlerTest(List<String> linksToParse, int expectedArraySize, List<String> expectedLinks) {
        this.htmlDocument = TestUtils.LinksToHtmlDocument(linksToParse);
        this.expectedArraySize = expectedArraySize;
        this.expectedLinks = expectedLinks;
    }

    @Test
    public void testGetValidLinks() {
        List<String> validLinks = this.links.getValidLinks(this.htmlDocument);

        // Assert that the array size of the retrieved links is as expected.
        assertThat(this.expectedArraySize, is(equalTo(validLinks.size())));
        // Assert that the retrieved links are equal to the expected links.
        assertArrayEquals(this.expectedLinks.toArray(), validLinks.toArray());
        // Assert that there are no forbidden links in the retrieved links.
        assertFalse(LinksHandler.forbiddenLinks.stream().anyMatch(validLinks::contains));
    }
}