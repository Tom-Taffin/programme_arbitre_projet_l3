package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OfferTileTest {
    @Test
    public void testCheckIfTileCanBePlacedOnEmptyBoard() {
        Tile tile = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Board board = new Board();

        assertTrue(OfferTile.checkIfTileCanBePlaced(tile, board));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.CITY));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.CITY));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertTrue(OfferTile.checkIfTileCanBePlaced(tile2, board));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlacedByRotating() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertTrue(OfferTile.checkIfTileCanBePlaced(tile2, board));
    }

    @Test
    public void testCheckIfIncompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertFalse(OfferTile.checkIfTileCanBePlaced(tile2, board));
    }
}
