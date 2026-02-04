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
        /**
        Board board = new Board();

        board.putTileAt(new Tile(new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD))), new Coordinates(0, 0));

        assertTrue(board.hasTile(new Coordinates(0, 0)));

        assertThrows(ImpossibleBoardMove.class, () -> {BoardMove.placeTile(board, new Tile(new EdgeNoRoad(new Zone(Topology.CITY)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD)), new EdgeNoRoad(new Zone(Topology.FIELD))),
                                                        new Coordinates(0, 0).downCoordinates()); });
*/
    }
}
