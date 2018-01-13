package TCI_Crawler.treeStructure;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitParamsRunner.class)
public class NodeTest {
    private Node<String>[] nodes;

    public static Object[] comparableNodes() {
        Node<String> node1 = new Node<>("AA");
        Node<String> node2 = new Node<>(null);
        Node<String> node3 = new Node<>("BB");
        Node<String> node4 = new Node<>(null);

        int valueBetweenNode2AndNode4 = (node2.hashCode()-node4.hashCode()<0)?1:-1;
        int valueBetweenNode4AndNode2 = -valueBetweenNode2AndNode4;
        return (new Object[][]{
                {node1, 0, node1},
                {node1, -1, node2},
                {node1, -1, node3},
                {node2, 1, node1},
                {node2, 0, node2},
                {node2, valueBetweenNode2AndNode4, node4},
                {node4, valueBetweenNode4AndNode2, node2},
                {node3, -1, node4}
        });
    }


    @Before
    public void setUp() {
        nodes = new Node[5];
        for (int i = 0; i < 5; i++) {
            nodes[i] = new Node<>("Node " + i);
        }
        nodes[0].setChildren((Arrays.asList((Node[]) Arrays.copyOfRange(nodes, 1, 2))));

        nodes[1].setChildren((Arrays.asList((Node[]) Arrays.copyOfRange(nodes, 2, 4))));
    }

    @Test
    public void RemoveChildNode() {
        nodes[0].removeChild(nodes[1]);
        assertFalse(nodes[0].getChildren().contains(nodes[1]));
    }

    @Test
    @Parameters(method = "comparableNodes")
    public void nodeIsComparable(Node node1, int compareValue, Node node2) {
        assertEquals(compareValue, node1.compareTo(node2));
    }
    @Test
    public void addChildNode(){
        Node addedNode = new Node("I Have Been Added");
        nodes[0].addChild(addedNode);
        assertTrue(nodes[0].getChildren().contains(addedNode));
    }
    @Test
    public void setWeightSetsChildrenWeight(){
        nodes[0].setWeight(0);
        assertEquals(1, nodes[1].getWeight());
        assertEquals(2, nodes[3].getWeight());
    }
    @Test
    public void visitNode(){
        nodes[0].setVisited(true);
        assertTrue(nodes[0].isVisited());
    }

    @Test
    public void nodeContainsData(){
        assertEquals(nodes[0].getData(),"Node 0");
    }
    @Test
    public void getParentFromChildNode(){
        Node buenosNodches = nodes[0];
        Node childOfbuenosNodches = (Node) buenosNodches.getChildren().get(0);
        assertEquals(buenosNodches, childOfbuenosNodches.getParent() );
    }

}
