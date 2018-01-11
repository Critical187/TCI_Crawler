package TCI_Crawler.treeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import javax.ws.rs.container.PreMatching;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class SearcherTest {
    private Searcher searcher;
    private TreeSet<SearchObjectBase> treeSet;
    @Mock
    private SpiderLegConnection spiderLegConnection;
    @Mock
    private SearchObjectBase searchObjectBase;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        treeSet = new TreeSet<>();
        searcher = new Searcher(treeSet);
    }

    /**
     * The Tree should looks like this
     *              (RootNode)
     *              /       \
     *   (FirstLevelNode) (EmptyFirstLevelNode)
     *          /
     *(SecondLevelNode)
     * @throws InvalidCategoryException
     * @throws InvalidSiteException
     */
    @Test
    public void CreateANodeTree() throws InvalidCategoryException, InvalidSiteException {
        ArrayList<SearchObjectBase> mockedSearchObjects = new ArrayList();
        for (int i = 0; i < 3; i++) {
           mockedSearchObjects.add(mock(SearchObjectBase.class));
        }
       searcher = Mockito.spy(new Searcher(treeSet));
       doReturn(spiderLegConnection).when(searcher).createSpiderLegConnection();
       when(spiderLegConnection.crawlAndGather("Kebab"))
               .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(0),new ArrayList<>(Arrays.asList(new String[]{"Kebeb","Kebob"}))));
       when(spiderLegConnection.crawlAndGather("Kebeb"))
                .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(1),new ArrayList<String>(Arrays.asList(new String[]{"Kebub"}))));
        when(spiderLegConnection.crawlAndGather("Kebub"))
                .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(2),new ArrayList<String>(Arrays.asList(new String[]{}))));
        when(spiderLegConnection.crawlAndGather("Kebob"))
                .thenReturn(new SearchObjectWithLinks(null,new ArrayList<String>(Arrays.asList(new String[]{"Kebob","kebab"}))));

        searcher.makeNode(null, "Kebab");

        Node<SearchObjectBase> rootNode = searcher.rootNode;
        Node<SearchObjectBase> KebebNode = rootNode.getChildren().get(0);
        Node<SearchObjectBase> KebubNode = KebebNode.getChildren().get(0);
        Node<SearchObjectBase> KebobNode = rootNode.getChildren().get(1);
        //check if RootNode has the mocked object
        assertEquals(rootNode.getData(),mockedSearchObjects.get(0));
        //check if RootNode has children so that it actually has a tree
        assertTrue(rootNode.getChildren().size()>0);
        //check if the first level child holds the mocked object
        assertEquals(KebebNode.getData(),mockedSearchObjects.get(1));
        //check if the first level child has children
        assertTrue(KebebNode.getChildren().size()>0);
        //check if the second level child has the object
        assertEquals(KebubNode.getData(),mockedSearchObjects.get(2));
        //check if the other first level child holds no object
        assertEquals(KebobNode.getData(),null);

    }

}
