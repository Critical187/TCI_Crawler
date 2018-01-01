package TCI_Crawler.crawler;

import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class SpiderLegTest {

    @Mock
    private SearchObjectWithLinks objWithLinks;

    @Mock
    private SearchObjectHandler objHandler;

    @Mock
    private LinksHandler linksHandler;

    @InjectMocks
    private SpiderLeg leg;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.linksHandler = mock(LinksHandler.class);
        this.objHandler = mock(SearchObjectHandler.class);
        this.leg = new SpiderLeg(this.linksHandler,this.objHandler);
    }

    @Test
    public void testCrawlAndGatherVerifyHandlersCorrectness() throws InvalidSiteException, InvalidCategoryException {
        Document htmlDoc = Jsoup.parse("<html></html>");
        this.leg.getObjectAndLinks(htmlDoc);
        verify(this.linksHandler, times(1)).getValidLinks(htmlDoc);
        verify(this.objHandler, times(1)).getSearchObjects(htmlDoc);
    }
}