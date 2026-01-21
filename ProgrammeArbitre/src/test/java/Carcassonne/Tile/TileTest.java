package Carcassonne.Tile;

import Carcassonne.Edge.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void testConnectTwoCompatibleTilesNoRoads(){
        Tile tile1 = new Tile(new EdgeNoRoad(Zone.CITY), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.FIELD));
        Tile tile2 = new Tile(new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.CITY), new EdgeNoRoad(Zone.FIELD));

        assertTrue(tile1.canConnectTo(tile2, Direction.TOP));
    }

    @Test
    public void testConnectTwoIncompatibleTilesNoRoads(){
        Tile tile1 = new Tile(new EdgeNoRoad(Zone.CITY), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.FIELD));
        Tile tile2 = new Tile(new EdgeNoRoad(Zone.CITY), new EdgeNoRoad(Zone.CITY), new EdgeNoRoad(Zone.FIELD), new EdgeNoRoad(Zone.CITY));

        assertFalse(tile1.canConnectTo(tile2, Direction.TOP));
    }
}
