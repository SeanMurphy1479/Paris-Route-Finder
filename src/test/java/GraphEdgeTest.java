import com.example.city_of_paris_router_finder_ca2.Models.GraphEdge;
import com.example.city_of_paris_router_finder_ca2.Models.GraphNodeAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphEdgeTest {

    GraphNodeAL node1;
    GraphNodeAL node2;
    GraphEdge edge;

    @BeforeEach
    void setup(){
        node1 = new GraphNodeAL("test",100,100);
        node2 = new GraphNodeAL("birb", 1000,1000);
        edge = new GraphEdge(node1,100);
    }

    @Test
    void setCostCorrect(){
        edge.setCost(20);
        assertEquals(20, edge.getCost());
    }

    @Test
    void setCostIncorrect(){
        edge.setCost(-100);
        assertEquals(0,edge.getCost());
    }

    @Test
    void setDest(){
        edge.setDestinationNode(node2);
        assertEquals(node2, edge.getDestinationNode());
    }
}
