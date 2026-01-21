package Carcassonne.Edge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {
    @Test
    public void testEdgeNoRoadConnectEdgeNoRoadSameZone(){
        Edge noRoadEdge1 = new EdgeNoRoad(Zone.FIELD);
        Edge noRoadEdge2 = new EdgeNoRoad(Zone.FIELD);

        assertTrue(noRoadEdge1.canConnectTo(noRoadEdge2));
    }

    @Test
    public void testEdgeNoRoadConnectEdgeNoRoadDifferentZone(){
        Edge noRoadEdge1 = new EdgeNoRoad(Zone.FIELD);
        Edge noRoadEdge2 = new EdgeNoRoad(Zone.CITY);

        assertFalse(noRoadEdge1.canConnectTo(noRoadEdge2));
    }
}
