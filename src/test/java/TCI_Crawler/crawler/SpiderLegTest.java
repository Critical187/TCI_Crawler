package TCI_Crawler.crawler;

import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.handlers.LinksHandler;
import TCI_Crawler.handlers.SearchObjectHandler;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

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
    }

    @Test(expected = InvalidSiteException.class)
    public void testCrawlAndGather_ThrowsInvalidSiteException() throws InvalidSiteException, InvalidCategoryException {
        objWithLinks = leg.crawlAndGather("https://kjeqwqew.com");
    }
}