package tci_crawler.integration_testing;

import TCI_Crawler.service.SpiderService;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;

public class SpiderIntegrationTest {

    private SpiderService spiderService;
    private String URL = "i315379.hera.fhict.nl/";
    private static String JSON_REGEX = "(AndrÃ©)|([AndrÃ©])|\\W|(\\r)|(\\n)|\\s+";

    @Before
    public void setUp() {
        this.spiderService = new SpiderService();
    }

    @Test
    public void testCrawlForInvalidWebsiteShouldReturnBadRequest() {
        String websiteURL = "notarealwebsite.comm";
        Response response = this.spiderService.crawlWebsite(websiteURL);
        String expectedMessage = "Could not establish connection to URL 'http://notarealwebsite.comm/'.";
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
        assertEquals(expectedMessage, response.getEntity().toString());
    }

    @Test
    public void testCrawlForInvalidWebsiteWithSearchWordShouldReturnBadRequest() {
        String websiteURL = "notarealwebsite.comm";
        Response response = this.spiderService.crawlWebsiteForItem(websiteURL, "Some title");
        String expectedMessage = "Could not establish connection to URL 'http://notarealwebsite.comm/'.";
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
        assertEquals(expectedMessage, response.getEntity().toString());
    }

    @Test
    public void testCrawlValidWebsiteWithNoSearchWord() {
        Response response = this.spiderService.crawlWebsite(this.URL);
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("TotalCrawl.JSON");

        String actualMessage = response.getEntity().toString();
        String actualMessageCleaned = IntegrationTestsUtil.setTimeToZero(actualMessage).replaceAll(JSON_REGEX, "");
        String expectedMessageCleaned = IntegrationTestsUtil.setTimeToZero(expectedMessage).replaceAll(JSON_REGEX, "");
        assertEquals(expectedMessageCleaned, actualMessageCleaned);
    }

    @Test
    public void testCrawlValidWebsiteWithSearchWord() {
        String searchWord = "Forrest Gump";
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("SingleCrawlForForrestGump.JSON");
        Response response = this.spiderService.crawlWebsiteForItem(this.URL, searchWord);

        String actualMessage = response.getEntity().toString();
        String actualMessageCleaned = IntegrationTestsUtil.setTimeToZero(actualMessage).replaceAll(JSON_REGEX, "");
        String expectedMessageCleaned = IntegrationTestsUtil.setTimeToZero(expectedMessage).replaceAll(JSON_REGEX, "");
        assertEquals(expectedMessageCleaned, actualMessageCleaned);
    }

    @Test
    public void testGetCrawlDetailsForNonExistingCrawl() {
        Response response = this.spiderService.getDetailsForCrawlID(1);
        String expectedMessage = "No crawl details found for ID '1'.";
        String actualMessage = response.getEntity().toString();
        assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatus());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetCrawlDetailsForExistingCrawl() {
        this.spiderService.crawlWebsite(this.URL);
        Response response = this.spiderService.getDetailsForCrawlID(1);
        String actualMessage = response.getEntity().toString();
        String expectedMessage = IntegrationTestsUtil.ConvertFromJSONFileToString("SearchDetailsForOne.JSON");
        String actualMessageCleaned = IntegrationTestsUtil.setElapsedTimeToZero(actualMessage).replaceAll(JSON_REGEX,"");
        String expectedMessageCleaned = IntegrationTestsUtil.setElapsedTimeToZero(expectedMessage).replaceAll(JSON_REGEX,"");
        assertEquals(expectedMessageCleaned, actualMessageCleaned);
    }
}
