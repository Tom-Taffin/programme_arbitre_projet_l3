package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardManagerTest {
    @Test
    public void testPlaceTileUpperTileNotCompatible() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.upCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {boardManager.placeTile(tile2, origin.upCoordinates()); });
    }

    @Test
    public void testPlaceTileLeftTileNotCompatible() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.leftCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {boardManager.placeTile(tile2, origin.leftCoordinates()); });
    }

    @Test
    public void testPlaceTileRightTileNotCompatible() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.rightCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {boardManager.placeTile(tile2, origin.rightCoordinates()); });
    }

    @Test
    public void testPlaceTileLowerTileNotCompatible() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.downCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {boardManager.placeTile(tile2, origin.downCoordinates()); });
    }

    @Test
    public void testPlaceTileLowerUpperTileCompatible() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertTrue(boardManager.checkIfTileCanBePlaced(tile2, origin.upCoordinates()));

        boardManager.placeTile(tile2, origin.upCoordinates());

        assertEquals(tile2, boardManager.getBoard().getTileAt(origin.upCoordinates()));
    }

    @Test
    public void testPlaceTileAlreadyHasATileAtCoordinates() throws ImpossibleBoardMove {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin));

        boardManager.placeTile(tile2, origin.upCoordinates());

        assertThrows(ImpossibleBoardMove.class, () -> {boardManager.placeTile(tile2, origin); });
    }

     @Test
    public void testCheckIfTileCanBePlacedOnEmptyBoard() {
        Tile tile = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        BoardManager boardManager = new BoardManager();

        assertTrue(boardManager.hasValidPosition(tile));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.CITY));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.CITY));
        BoardManager boardManager = new BoardManager();
        Coordinates coordinates = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, coordinates);

        assertTrue(boardManager.hasValidPosition(tile2));
    }

    @Test
    public void testCheckIfCompatibleTileCanBePlacedByRotating() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY));
        BoardManager boardManager = new BoardManager();
        Coordinates coordinates = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, coordinates);

        assertTrue(boardManager.hasValidPosition(tile2));
    }

    @Test
    public void testCheckIfIncompatibleTileCanBePlaced() {
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY), new Edge(Topology.CITY));
        BoardManager boardManager = new BoardManager();
        Coordinates coordinates = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, coordinates);

        assertFalse(boardManager.hasValidPosition(tile2));
    }
}
