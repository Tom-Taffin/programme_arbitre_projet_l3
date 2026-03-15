package l3s6.projet.star.referee.board;

import java.util.Set;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.WrongTopologyException;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
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

    /**
     * Places a meeple on the tile.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeMeeple(Tile tile, String type, String position, Player player) throws ImpossibleMeepleMoveException {
        if (player.hasMeeples()){
            throw new ImpossibleMeepleMoveException("Player doesn't have any meeple.");
        }
        if (!type.equals("regular")){
            throw new ImpossibleMeepleMoveException("Meeple Type is not regular");
        }

        int index;
        try {
            index = Integer.parseInt(position.substring(1));
        } catch (Exception e) {
            throw new ImpossibleMeepleMoveException("Wrong meeple position syntax");
        }

        Zone zone = tile.getZoneAt(this.parseDirection(position.charAt(0)), index);

        if (this.hasMeepleOnBoardZone(zone)){
            throw new ImpossibleMeepleMoveException("There is already meeple on the board zone");
        }

        try {
            zone.setMeeple(new Meeple(player));
        } catch (WrongTopologyException e) {
            throw new ImpossibleMeepleMoveException("Meeple can't be placed on this topology");
        }
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

    private Direction parseDirection(char direction) throws ImpossibleMeepleMoveException {
        switch (direction) {
            case 'T':
                return Direction.TOP;
            case 'R':
                return Direction.RIGHT;
            case 'B':
                return Direction.BOTTOM;
            case 'L':
                return Direction.LEFT;
            default:
                throw new ImpossibleMeepleMoveException("Wrong meeple position syntax");
        }
    }
}
