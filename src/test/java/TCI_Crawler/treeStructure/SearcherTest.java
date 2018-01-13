package TCI_Crawler.treeStructure;

import TCI_Crawler.crawler.SpiderLegConnection;
import TCI_Crawler.exceptions.InvalidCategoryException;
import TCI_Crawler.exceptions.InvalidSiteException;
import TCI_Crawler.searchObjects.Book;
import TCI_Crawler.searchObjects.SearchObjectBase;
import TCI_Crawler.searchObjects.SearchObjectWithLinks;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
@RunWith(JUnitParamsRunner.class)
public class SearcherTest {
    private Searcher searcher;
    private TreeSet<SearchObjectBase> treeSet;
    @Mock
    private SpiderLegConnection spiderLegConnection;

    public static Object[] depthFirstSearchVariables(){
       SearchObjectBase searchObjectBase = new Book("George","none",2912, "Ultra Space",new String[]{"Jack","Daniels"},"245524rw","Hopkins");
        return (new Object[][]{
                {3, 5, null,searchObjectBase, 4 },
                {1, 1, "George",searchObjectBase, 0},
                {3, 5, null, null, 0}
        });
    }
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

    /**
     * The Tree looks like this
     *                       (Node 0
     *                      /       \
     *                  (Node 1)  (Node 2)
     *                    / \
     *             (Node 3) (Node 4 Lucky Data)
     */
    @Test
    @Parameters(method = "depthFirstSearchVariables")
    public void depthFirstSearch(int searchDepth, int pagesExplored, String searchTerm, SearchObjectBase wantedSearchObject, int nodeNumberToAttach){
        //make the mock objects
        ArrayList<SearchObjectBase> mockedSearchObjects = new ArrayList();
        for (int i = 0; i < 5; i++) {
            mockedSearchObjects.add(new Book("Z"+ RandomStringUtils.random(33),
                    RandomStringUtils.random(33),
                    2912,
                    RandomStringUtils.random(33),
                    new String[]{RandomStringUtils.random(33),
                            RandomStringUtils.random(33)},
                    RandomStringUtils.random(33),
                    RandomStringUtils.random(33)));
        }
        //make the tree
        Node[] nodes = new Node[5];
        for (int i = 0; i < 5; i++) {
            nodes[i] = new Node<>(mockedSearchObjects.get(i));
        }
        nodes[nodeNumberToAttach] = new Node<>(wantedSearchObject);
        nodes[0].setChildren((Arrays.asList((Node[]) Arrays.copyOfRange(nodes, 1, 3))));

        nodes[1].setChildren((Arrays.asList((Node[]) Arrays.copyOfRange(nodes, 3, 5))));
        nodes[0].setWeight(0);
        searcher = Mockito.spy(new Searcher(treeSet));
        searcher.rootNode = nodes[0];
        searcher.setTitleToSearchFor(searchTerm);
        searcher.depthFirstSearch();
        assertEquals(searchDepth, searcher.getDepth());
        assertEquals(pagesExplored, searcher.getPagesExplored());
        assertTrue(treeSet.contains(wantedSearchObject));

    }
}
