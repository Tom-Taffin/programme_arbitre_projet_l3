package board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.EdgeNoRoad;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardMoveTest {
    @Test
    public void testBoardMoveUpperTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, new Coordinates(0, 0).upCoordinates()); });
    }

    @Test
    public void testBoardMoveLeftTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, new Coordinates(0, 0).leftCoordinates()); });
    }

    @Test
    public void testBoardMoveRightTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.CITY)));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, new Coordinates(0, 0).rightCoordinates()); });
    }

    @Test
    public void testBoardMoveLowerTileNotCompatible() throws ImpossibleBoardMove {
        Board board = new Board();
        Tile tile1 = new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Tile tile2 = new Tile(new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)));
        Coordinates origin = new Coordinates(0, 0);

        board.putTileAt(tile1, origin);

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, tile2, new Coordinates(0, 0).downCoordinates()); });
    }
}
