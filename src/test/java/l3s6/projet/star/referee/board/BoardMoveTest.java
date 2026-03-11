package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Edge;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardMoveTest {
    @Test
    public void testBoardMoveUpperTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertFalse(BoardMove.checkIfTileCanBePlaced(board, tile2, origin.upCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, origin.upCoordinates()); });
    }

    @Test
    public void testBoardMoveLeftTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertFalse(BoardMove.checkIfTileCanBePlaced(board, tile2, origin.leftCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, origin.leftCoordinates()); });
    }

    @Test
    public void testBoardMoveRightTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.CITY));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertFalse(BoardMove.checkIfTileCanBePlaced(board, tile2, origin.rightCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, origin.rightCoordinates()); });
    }

    @Test
    public void testBoardMoveLowerTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.CITY), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertFalse(BoardMove.checkIfTileCanBePlaced(board, tile2, origin.downCoordinates()));
        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, origin.downCoordinates()); });
    }

    @Test
    public void testBoardMoveLowerUpperTileCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertTrue(BoardMove.checkIfTileCanBePlaced(board, tile2, origin.upCoordinates()));

        BoardMove.placeTile(board, tile2, origin.upCoordinates());

        assertEquals(tile2, board.getTileAt(origin.upCoordinates()));
    }

    @Test
    public void testBoardMoveAlreadyHasATileAtCoordinates() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Tile tile2 = new Tile(new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD), new Edge(Topology.FIELD));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertFalse(BoardMove.checkIfTileCanBePlaced(board, tile2, origin));

        BoardMove.placeTile(board, tile2, origin.upCoordinates());

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, origin); });
    }
}
