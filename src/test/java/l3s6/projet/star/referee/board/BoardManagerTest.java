package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.referee.RefereeView;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardManagerTest {
    @Test
    public void testPlaceTileUpperTileNotCompatible() throws InvalidTileMoveException {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.upCoordinates()));
        assertThrows(InvalidTileMoveException.class, () -> {boardManager.placeTile(tile2, origin.upCoordinates()); });
    }

    @Test
    public void testPlaceTileLeftTileNotCompatible() throws InvalidTileMoveException {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.leftCoordinates()));
        assertThrows(InvalidTileMoveException.class, () -> {boardManager.placeTile(tile2, origin.leftCoordinates()); });
    }

    @Test
    public void testPlaceTileRightTileNotCompatible() throws InvalidTileMoveException {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.rightCoordinates()));
        assertThrows(InvalidTileMoveException.class, () -> {boardManager.placeTile(tile2, origin.rightCoordinates()); });
    }

    @Test
    public void testPlaceTileLowerTileNotCompatible() throws InvalidTileMoveException {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin.downCoordinates()));
        assertThrows(InvalidTileMoveException.class, () -> {boardManager.placeTile(tile2, origin.downCoordinates()); });
    }

    @Test
    public void testPlaceTileLowerUpperTileCompatible() throws InvalidTileMoveException {
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
    public void testPlaceTileAlreadyHasATileAtCoordinates() throws InvalidTileMoveException {
        BoardManager boardManager = new BoardManager();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        boardManager.getBoard().putTileAt(tile1, origin);

        assertFalse(boardManager.checkIfTileCanBePlaced(tile2, origin));

        boardManager.placeTile(tile2, origin.upCoordinates());

        assertThrows(InvalidTileMoveException.class, () -> {boardManager.placeTile(tile2, origin); });
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

    @Test
    public void testBoardZoneDoesntHaveMeeple() throws InvalidTileMoveException, InvalidMeeplePositionException, InvalidMeepleMoveException, WrongTileSyntaxException {
        BoardManager boardManager = new BoardManager();
        TileBuilder tileBuilder = new TileBuilder();
        Player player1 = new Player("A", 8);
        Tile tile1 = tileBuilder.build("Nf0-c1-c1-c1");
        Tile tile2 = tileBuilder.build("Nc0-c0-c0-c0");
        Coordinates origin = new Coordinates(0, 0);
        Tile originTile = boardManager.getBoard().getTileAt(origin);

        boardManager.placeTile(tile1, origin.downCoordinates());
        boardManager.placeTile(tile2, origin.downCoordinates().downCoordinates());
        boardManager.placeMeeple(originTile, origin, "regular", "T0", player1);

        assertFalse(boardManager.hasMeepleOnBoardZone(tile1.getZoneAt(Direction.RIGHT, 0)));
    }

    @Test
    public void testHasBoardZoneDoesntHaveMeeple() throws InvalidTileMoveException, InvalidMeeplePositionException, InvalidMeepleMoveException, WrongTileSyntaxException {
        BoardManager boardManager = new BoardManager();
        TileBuilder tileBuilder = new TileBuilder();
        Player player1 = new Player("A", 8);
        Tile tile1 = tileBuilder.build("Nf0-c1-c1-c1");
        Tile tile2 = tileBuilder.build("Nc0-c0-c0-c0");
        Coordinates origin = new Coordinates(0, 0);
        Tile originTile = boardManager.getBoard().getTileAt(origin);

        boardManager.placeTile(tile1, origin.downCoordinates());
        boardManager.placeTile(tile2, origin.downCoordinates().downCoordinates());
        boardManager.placeMeeple(tile1, origin.downCoordinates(), "regular", "B0", player1);

        assertEquals(7, player1.getNbMeeples());
        assertFalse(boardManager.hasMeepleOnBoardZone(originTile.getZoneAt(Direction.TOP, 0)));
        assertTrue(boardManager.hasMeepleOnBoardZone(tile1.getZoneAt(Direction.RIGHT, 0)));
        assertTrue(boardManager.hasMeepleOnBoardZone(tile2.getZoneAt(Direction.TOP, 0)));
    }

    @Test
    public void testPlaceMeepleInvalidMove() throws InvalidMeeplePositionException, InvalidMeepleMoveException {
        BoardManager boardManager = new BoardManager();
        Player player1 = new Player("A", 8);
        Coordinates origin = new Coordinates(0, 0);
        Tile originTile = boardManager.getBoard().getTileAt(origin);

        assertThrows(InvalidMeepleMoveException.class, ()-> boardManager.placeMeeple(originTile, origin, "regular", "B0", player1));
        assertThrows(InvalidMeepleMoveException.class, ()-> boardManager.placeMeeple(originTile, origin, "not-regular", "T0", player1));
        assertThrows(InvalidMeeplePositionException.class, ()-> boardManager.placeMeeple(originTile, origin, "regular", "A0", player1));

        boardManager.placeMeeple(originTile, origin, "regular", "T0", player1);

        assertThrows(InvalidMeepleMoveException.class, ()-> boardManager.placeMeeple(originTile, origin, "regular", "T0", player1));
    }

    /**
    @Test
    public void testRemoveMeeplesFrom() throws WrongTileSyntaxException, InvalidMeeplePositionException, InvalidMeepleMoveException, URISyntaxException, IOException, ParseException, InterruptedException {
        BoardManager boardManager = new BoardManager();
        TileBuilder tileBuilder = new TileBuilder();
        Player player1 = new Player("A", 8);
        Player player2 = new Player("B", 8);
        Tile tile1 = tileBuilder.build("Nf0-c1-c1-c1");
        Tile tile2 = tileBuilder.build("Nc0-c0-c0-c0");
        Coordinates origin = new Coordinates(0, 0);
        Tile originTile = boardManager.getBoard().getTileAt(origin);

        boardManager.placeMeeple(originTile, origin, "regular", "T0", player1);
        boardManager.placeMeeple(tile1, origin.downCoordinates(), "regular", "L0", player2);

        boardManager.removeMeeplesFrom(player1, new RefereeView("0", 0, "A", "", 5));

        assertFalse(boardManager.hasMeepleOnBoardZone(originTile.getZoneAt(Direction.TOP, 0)));
        assertTrue(boardManager.hasMeepleOnBoardZone(tile1.getZoneAt(Direction.BOTTOM, 0)));
        assertEquals(8, player1.getNbMeeples());
        assertEquals(7, player1.getNbMeeples());
    }
    */
}
