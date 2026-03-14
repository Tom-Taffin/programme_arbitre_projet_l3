package l3s6.projet.star.referee.board;

import java.util.Set;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;

public class BoardManager {

    private Board board = new Board();

    public Board getBoard() {
        return board;
    }

    /**
     * Places the given tile at the given coordinates on the board.
     * @throws ImpossibleBoardMove if the tile can't be placed.
     */
    public void placeTile(Tile tile, Coordinates coordinates) throws ImpossibleBoardMove{
        if (!this.checkIfTileCanBePlaced(tile, coordinates)){
            throw new ImpossibleBoardMove("Tile not compatible!");
        }

        this.board.putTileAt(tile, coordinates);
    }

    /**
     * @return True if the given tile can be placed on the board at the given coordinates. Checks if neighboring tiles are compatible.
     */
    public boolean checkIfTileCanBePlaced(Tile tile, Coordinates coordinates){
        if (this.board.hasTile(coordinates)){
            return false;
        }

        for(Direction direction : Direction.values()){
            if (board.hasTile(coordinates.getAdjacent(direction))){
                Tile adjacentTile = board.getTileAt(coordinates.getAdjacent(direction));
                if (!tile.isCompatibleWith(adjacentTile, direction)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if the given tile can be placed on the board. Checks tile with all neighboring empty tiles on the board, with all orientations.
     */
    public boolean hasValidPosition(Tile tile){
        for(Coordinates coord : this.board.getOutsideFrontierTiles()){
            for (int i=0; i < 4; i++){
                if(this.checkIfTileCanBePlaced(tile, coord)){
                    return true;
                }
                tile.rotateRight();
            }
        }
        return false;
    }

    public boolean hasMeepleOnBoardZone(Zone zone) {
        Set<Zone> zones = zone.getAllBoardConnectingZones();
        for(Zone z : zones){
            if (z.hasMeeple()){
                return true;
            }
        }
        return false;
    }
}
