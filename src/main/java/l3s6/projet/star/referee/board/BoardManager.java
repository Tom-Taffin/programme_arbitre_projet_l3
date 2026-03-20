package l3s6.projet.star.referee.board;

import java.util.HashSet;
import java.util.Set;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.NoMeepleException;
import l3s6.projet.star.game.edge.WrongTopologyException;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.AlreadyHaveMeepleException;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Orientation;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.referee.RefereeView;

public class BoardManager {

    private Board board = new Board();

    public Board getBoard() {
        return board;
    }

    /**
     * Places the given tile at the given coordinates on the board.
     * @throws InvalidTileMoveException if the tile can't be placed.
     */
    public void placeTile(Tile tile, Coordinates coordinates) throws InvalidTileMoveException{
        if (!this.checkIfTileCanBePlaced(tile, coordinates)){
            throw new InvalidTileMoveException("Tile not compatible!");
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
        Orientation orientation = tile.getOrientation();
        for(Coordinates coord : this.board.getOutsideFrontierTiles()){
            for (int i=0; i < 4; i++){
                if(this.checkIfTileCanBePlaced(tile, coord)){
                    tile.setOrientation(orientation);
                    return true;
                }
                tile.rotateRight();
            }
        }
        tile.setOrientation(orientation);
        return false;
    }

    /**
     * Places a meeple on the tile.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeMeeple(Tile tile, Coordinates coordinates, String type, String position, Player player) throws InvalidMeepleMoveException, InvalidMeeplePositionException {
        if (!player.hasMeeples()){
            throw new InvalidMeepleMoveException("Player doesn't have any meeple.");
        }
        if (!type.equals("regular")){
            throw new InvalidMeepleMoveException("Meeple Type is not regular");
        }

        int index;
        try {
            index = Integer.parseInt(position.substring(1));
        } catch (Exception e) {
            throw new InvalidMeeplePositionException("Wrong meeple position syntax");
        }

        Zone zone;
        try {
            zone = tile.getZoneAt(Direction.fromChar(position.charAt(0)), index);
        } catch (WrongTileSyntaxException e) {
            throw new InvalidMeeplePositionException("Wrong meeple position syntax");
        }

        if (this.hasMeepleOnBoardZone(zone)){
            throw new InvalidMeepleMoveException("There is already meeple on the board zone");
        }

        try {
            zone.setMeeple(new Meeple(player, coordinates));
        } catch (WrongTopologyException e) {
            throw new InvalidMeepleMoveException("Meeple can't be placed on this topology");
        } catch (AlreadyHaveMeepleException e) {
            throw new InvalidMeepleMoveException("There is already meeple on the board zone");
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

    /**
     * Removes meeples from a given player and send COLLECT.
     */
    public void removeMeeplesFrom(Player player, RefereeView refereeView){
        for(Zone zone : this.getZonesWithMeeple()){
            Meeple meeple = zone.getMeeple();
            if(meeple.getPlayer() == player){
                try {
                    refereeView.send("COLLECT", player.getID(), meeple.getCoordinates().getX(), meeple.getCoordinates().getY());
                    zone.giveBackMeeple();
                } catch (InvalidArgumentNumberException | NoMeepleException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @return all the zones with a meeple
     */
    public Set<Zone> getZonesWithMeeple(){
        Set<Zone> zones = new HashSet<>();
        for(Tile tile : this.board.getTiles()){
            for(Direction direction : Direction.values()){
                for(Zone zone : tile.getEdge(direction).getZones()){
                    if(zone.hasMeeple()){
                        zones.add(zone);
                    }
                }
            }
        }
        return zones;
    }

    public void removeTile(Coordinates coordinates) {
        this.board.removeTileAt(coordinates);
    }
}
