package tci_crawler.integration_testing;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import org.junit.Test;

public class SpiderLegIntegrationTest {

    // TODO Naming, mocks.
    @Test(expected = InvalidSiteException.class)
    public void testerino() throws InvalidCategoryException, InvalidSiteException {
        SpiderLegConnection theSpiderLegDude = new SpiderLegConnection();
        theSpiderLegDude.crawlAndGather("https://notarealwebsitemyDude.comm");

    }
}
