package tci_crawler.integration_testing;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;

public class SpiderLegConnectionIntegrationTest {

    private SpiderLegConnection spiderLegConnection;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.spiderLegConnection = new SpiderLegConnection();
    }

    @Test
    public void testCrawlAndGatherForInvalidWebsite()
            throws InvalidCategoryException, InvalidSiteException {
        String websiteURL = "https://notarealwebsite.comm";
        thrown.expect(InvalidSiteException.class);
        thrown.expectMessage(String.format("Could not establish connection to URL '%s'.", websiteURL));

        this.spiderLegConnection.crawlAndGather(websiteURL);
    }

    @Test
    public void testCrawlAndGatherForValidWebsite()
            throws InvalidCategoryException, InvalidSiteException {
        String websiteURL = "http://i315379.hera.fhict.nl/details.php?id=103";
        SearchObjectWithLinks retrievedObject = this.spiderLegConnection.crawlAndGather(websiteURL);
        Book expectedObject = new Book(
                "Refactoring: Improving the Design of Existing Code",
                "Tech",
                1999,
                "Hardcover",
                new String[]{"Martin Fowler", "Kent Beck", "John Brant", "William Opdyke", "Don Roberts"},
                "978-0201485677",
                "Addison-Wesley Professional");

        // Assert that both objects are of the same class.
        assertEquals(expectedObject.getClass(), retrievedObject.getRetrievedObject().getClass());
        // Assert that both objects have the exact same properties.
        assertThat(expectedObject, samePropertyValuesAs(retrievedObject.getRetrievedObject()));
    }
}
