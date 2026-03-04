package board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.EdgeNoRoad;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.tile.Tile;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import tile.Deck;
import tile.EmptyDeckException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OfferTileTest {/**
    @Test
    public void testCheckIfTileCanBePlacedOnEmptyBoard() {
        Tile tile = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Board board = new Board();

        assertTrue(offerTile.checkIfTileCanBePlaced(tile, board));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertTrue(offerTile.checkIfTileCanBePlaced(tile2, board));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlacedByRotating() {
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertTrue(offerTile.checkIfTileCanBePlaced(tile2, board));
    }

    @Test
    public void testCheckIfIncompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.CITY)));
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);

        board.putTileAt(tile1, coordinates);

        assertFalse(offerTile.checkIfTileCanBePlaced(tile2, board));
    }*/
}
