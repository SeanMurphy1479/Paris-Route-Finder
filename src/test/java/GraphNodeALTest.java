import com.example.city_of_paris_router_finder_ca2.Models.GraphNodeAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphNodeALTest {

    GraphNodeAL<Integer> node1;
    GraphNodeAL<Integer> node2;
    GraphNodeAL<Integer> node3;

    @BeforeEach
    void setup(){
        node1 = new GraphNodeAL<>(1, "Name", 0, 0);
        node2 = new GraphNodeAL<>(1, "Eiffel Tower", 0, 0);
        node3 = new GraphNodeAL<>(1, "Arch", 0, 0);
    }



    @Test
    public void testSetName() {
        node1.setName("long name that isnt able to be truncated it cant be more than 30 characters");
        assertEquals("Name", node1.getName());
    }

    @Test
    public void testSetX() {
        //cant be -5 so it tests
        node2.setX(-5);
        assertEquals(0, node2.getX());
    }

    @Test
    public void testSetY() {
        //same as the one before it cannot be -5, so it tests
        node3.setY(-5);
        assertEquals(0, node3.getY());
    }

}
