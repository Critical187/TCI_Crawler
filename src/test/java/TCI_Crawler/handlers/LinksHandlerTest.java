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



public class LinksHandlerTest {

    @Mock
    private List<String> forbiddenLinks;

    @Mock
    private Document mDoc;

    @InjectMocks
    private LinksHandler links;

    @Before
    public void setUp() throws Exception {
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
    }
}