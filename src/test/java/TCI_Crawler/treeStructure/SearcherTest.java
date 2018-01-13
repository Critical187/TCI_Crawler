package TCI_Crawler.treeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;;
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
     *                       (RootNode "Root")
     *                         /       \
     *       (FirstLevelNode "a1") (EmptyFirstLevelNode "a2")
     *          /
     *(SecondLevelNode "b1")
     * @throws InvalidCategoryException
     * @throws InvalidSiteException
     */
    @Test
    public void createANodeTree() throws InvalidCategoryException, InvalidSiteException {
        ArrayList<SearchObjectBase> mockedSearchObjects = new ArrayList();
        for (int i = 0; i < 3; i++) {
           mockedSearchObjects.add(mock(SearchObjectBase.class));
        }
       searcher = Mockito.spy(new Searcher(treeSet));
       doReturn(spiderLegConnection).when(searcher).createSpiderLegConnection();

       when(spiderLegConnection.crawlAndGather("Root"))
               .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(0),new ArrayList<>(Arrays.asList(new String[]{"a1","a2",}))));
       when(spiderLegConnection.crawlAndGather("a1"))
                .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(1),new ArrayList<String>(Arrays.asList(new String[]{"b1"}))));
        when(spiderLegConnection.crawlAndGather("b1"))
                .thenReturn(new SearchObjectWithLinks(mockedSearchObjects.get(2),new ArrayList<String>(Arrays.asList(new String[]{}))));
        when(spiderLegConnection.crawlAndGather("a2"))
                .thenReturn(new SearchObjectWithLinks(null,new ArrayList<String>(Arrays.asList(new String[]{"a1","root"}))));
        when(spiderLegConnection.crawlAndGather("zzz"))
                .thenReturn(new SearchObjectWithLinks(null,new ArrayList<String>(Arrays.asList(new String[]{"ccc"}))));
        when(spiderLegConnection.crawlAndGather("zzz"))
                .thenReturn(new SearchObjectWithLinks(null,new ArrayList<String>(Arrays.asList(new String[]{"a2"}))));
        searcher.makeNode(null, "Root");

        Node<SearchObjectBase> rootNode = searcher.rootNode;
        Node<SearchObjectBase> a1Node = rootNode.getChildren().get(0);
        Node<SearchObjectBase> b1Node = a1Node.getChildren().get(0);
        Node<SearchObjectBase> a2Node = rootNode.getChildren().get(1);
        //check if RootNode has the mocked object
        assertEquals(rootNode.getData(),mockedSearchObjects.get(0));
        //check if RootNode has children so that it actually has a tree
        assertTrue(rootNode.getChildren().size()>0);
        //check if the first level child holds the mocked object
        assertEquals(a1Node.getData(),mockedSearchObjects.get(1));
        //check if the first level child has children
        assertTrue(a1Node.getChildren().size()>0);
        //check if the second level child has the object
        assertEquals(b1Node.getData(),mockedSearchObjects.get(2));
        //check if the other first level child holds no object
        assertEquals(a2Node.getData(),null);

    }
}
