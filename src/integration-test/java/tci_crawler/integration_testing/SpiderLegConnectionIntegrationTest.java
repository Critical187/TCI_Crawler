package tci_crawler.integration_testing;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.Movie;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SpiderLegConnectionIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SpiderLegConnection spiderLegConnection;

    @Before
    public void setUp() {
        this.spiderLegConnection = new SpiderLegConnection();
    }

    @Test
    public void testCrawlAndGatherForNonExistingWebsiteShouldThrowException()
            throws InvalidCategoryException, InvalidSiteException {
        String nonExistingWebsiteURL = "http://notarealwebsite.comm/";
        String expectedMessage = String.format("Could not establish connection to URL '%s'.", nonExistingWebsiteURL);

        this.expectedException.expect(InvalidSiteException.class);
        this.expectedException.expectMessage(expectedMessage);

        this.spiderLegConnection.crawlAndGather(nonExistingWebsiteURL);
    }

    @Test
    public void testCrawlAndGatherForWebsiteThatContainsNoHtmlShouldThrowException()
            throws InvalidCategoryException, InvalidSiteException {
        String websiteUrlThatContainsNoHTML = "https://httpstat.us/300";
        String expectedMessage = String.format(
                "Website at URL '%s' does not contain HTML.", websiteUrlThatContainsNoHTML);

        this.expectedException.expect(InvalidSiteException.class);
        this.expectedException.expectMessage(expectedMessage);

        this.spiderLegConnection.crawlAndGather(websiteUrlThatContainsNoHTML);
    }

    @Test
    public void testCrawlAndGatherForWebsiteThatReturnsCodeOtherThan200ShouldThrowException()
            throws InvalidCategoryException, InvalidSiteException {
        String websiteUrlThatReturnsCode300 = "http://the-internet.herokuapp.com/status_codes/300";
        String expectedMessage = "Status code of returned request is different than '200'";

        this.expectedException.expect(InvalidSiteException.class);
        this.expectedException.expectMessage(expectedMessage);

        this.spiderLegConnection.crawlAndGather(websiteUrlThatReturnsCode300);
    }

    @Test
    public void testCrawlAndGatherForValidWebsiteShouldProperObjectAndLinks()
            throws InvalidCategoryException, InvalidSiteException {

        String validWebsiteURL = "http://i315379.hera.fhict.nl/details.php?id=202";
        SearchObjectWithLinks retrievedObjectWithLinks = this.spiderLegConnection.crawlAndGather(validWebsiteURL);
        Movie expectedObject = new Movie(
                "Office Space",
                "Comedy",
                1999,
                "Blu-ray",
                "Mike Judge",
                new String[]{"William Goldman"},
                new String[]{"Ron Livingston", "Jennifer Aniston", "David Herman", "Ajay Naidu", "Diedrich Bader", "Stephen Root"});

        assertThat(expectedObject, samePropertyValuesAs(retrievedObjectWithLinks.getRetrievedObject()));
        assertFalse(retrievedObjectWithLinks.getRetrievedLinks().isEmpty());
    }
}