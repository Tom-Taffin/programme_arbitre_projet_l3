package l3s6.projet.star.referee.score;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import l3s6.projet.star.game.meeple.NoMeepleException;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.NoAbbeyException;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.referee.RefereeView;
import l3s6.projet.star.referee.board.BoardManager;

public class ScoreManager {

    private BoardManager boardManager;

    public ScoreManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * After the tile is placed,
     * If a zone is finished, updates players scores and gives back meeples.
     * Sends meeple returned with COLLECTS command.
     * Otherwise, does nothing.
     */
    public Map<Player,Integer> calculatePointsEarned(Tile tile, Coordinates coordinates, RefereeView refereeView){
        Map<Player, Integer> pointsEarned = new HashMap<>();
        for(Zone zone : tile.getDistinctZone()){
            Set<Zone> zones = zone.getAllBoardConnectingZones();
            if(this.isClosed(zones)){
                Map<Player,Integer> nbMeeples = this.giveBackMeeples(zones, refereeView);
                if(!nbMeeples.isEmpty()){
                    Set<Player> players = this.getPlayerMajority(nbMeeples);
                    int points = this.pointsCalculationWhenCLosed(zones);
                    this.updateScore(pointsEarned, players, points);
                }
            }
        }
        this.calculateAbbeyPoints(coordinates, refereeView, pointsEarned);
        for(Coordinates coord : coordinates.getAdjacentAndCornerCoordinates()){
            this.calculateAbbeyPoints(coord, refereeView, pointsEarned);
        }
        return pointsEarned;
    }

    /**
     * After the end of the game,
     * For all the zones with meeple, updates players scores and gives back meeples.
     * Sends meeple returned with COLLECTS command.
     */
    public Map<Player, Integer> calculateEndGamePoints(RefereeView refereeView) {
        Map<Player, Integer> pointsEarned = new HashMap<>();
        for(Zone zone : this.boardManager.getZonesWithMeeple()){
            if(zone.hasMeeple()){
                Set<Zone> zones = zone.getAllBoardConnectingZones();
                Map<Player,Integer> nbMeeples = this.giveBackMeeples(zones, refereeView);
                Set<Player> players = this.getPlayerMajority(nbMeeples);
                int points = this.pointsCalculationEndGame(zones);
                this.updateScore(pointsEarned, players, points);
            }
        }

        calculateEndGameAbbeyPoints(refereeView, pointsEarned);
        return pointsEarned;
    }

    private void calculateEndGameAbbeyPoints(RefereeView refereeView, Map<Player, Integer> pointsEarned) {
        for(Tile tile : this.boardManager.getAbbeyTilesWithMeeple()){
            try {
                Meeple meeple = tile.getAbbeyMeeple();
                int point = 1 + this.boardManager.countSurroundingTiles(meeple.getCoordinates());
                refereeView.send("COLLECTS", meeple.getPlayer().getID(), "regular", meeple.getCoordinates().getX(), meeple.getCoordinates().getY());
                this.updateScore(pointsEarned, Set.of(meeple.getPlayer()), point);
                tile.giveBackAbbeyMeeple();
            } catch (NoAbbeyException | NoMeepleException | InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * check if the board zone made up of these zones is closed
     */
    private boolean isClosed(Set<Zone> zones) {
        for(Zone zone : zones){
            if(!zone.hasAdjacentZone()){
                return false;
            }
        }
        return true;
    }

    /**
     * Give back all the meeples
     * Sends meeple returned with COLLECTS command.
     * @return a map of player and his number of meeple in the zones
     */
    private Map<Player, Integer> giveBackMeeples(Set<Zone> zones, RefereeView refereeView) {
        Map<Player, Integer> res = new HashMap<>();
        for(Zone zone : zones){
            if(zone.hasMeeple()){
                Meeple meeple = zone.getMeeple();
                Player player = meeple.getPlayer();
                try {
                    refereeView.send("COLLECTS", player.getID(), "regular", meeple.getCoordinates().getX(), meeple.getCoordinates().getY());
                } catch (InvalidArgumentNumberException e) {
                    throw new RuntimeException(e);
                }
                if(res.containsKey(player)){
                    res.put(player, res.get(player)+1);
                }
                else {
                    res.put(player, 1);
                }
                try {
                    zone.giveBackMeeple();
                } catch (NoMeepleException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return res;
    }

    /**
     * @return a set of players with majority
     */
    private Set<Player> getPlayerMajority(Map<Player,Integer> nbMeeples) {
        int max = Collections.max(nbMeeples.values());
        Set<Player> res = new HashSet<>();
        for(Player player : nbMeeples.keySet()){
            if(nbMeeples.get(player) == max){
                res.add(player);
            }
        }
        return res;
    }

    /**
     * @return the number of point earned when the zones are closed
     */
    private int pointsCalculationWhenCLosed(Set<Zone> zones) {
        int points = this.pointsCalculationEndGame(zones);
        Topology topology = null;
        for(Zone zone : zones){
            topology = zone.getTopology();
            break;
        }
        if (topology == Topology.CITY) {
            return points * 2;
        }
        return points;
    }

    /**
     * @return the number of point earned at the end of the game for the zones
     */
    private int pointsCalculationEndGame(Set<Zone> zones) {
        Set<Zone> distinctZones = this.getDistinctZones(zones);
        Topology topology = null;
        for(Zone zone : zones){
            topology = zone.getTopology();
            break;
        }
        if(topology == Topology.CITY){
            int nbPoints = 0;
            for(Zone zone : distinctZones){
                if(zone.hasShield()){
                    nbPoints += 2;
                }
                else {
                    nbPoints += 1;
                }
            }
            return nbPoints;
        }
        return distinctZones.size();
    }

    /**
     * @return one zone per tile for all the tiles covered by zones.
     */
    private Set<Zone> getDistinctZones(Set<Zone> zones) {
        Set<Zone> distinctZones = new HashSet<>(zones);
        for(Zone zone : zones){
            if(distinctZones.contains(zone)){
                distinctZones.removeAll(zone.getConnectingZones());
            }
        }
        return distinctZones;
    }

    /**
     * add points to all the players and update pointsEarned
     */
    private void updateScore(Map<Player, Integer> pointsEarned, Set<Player> players, int points) {
        for(Player player : players){
            player.addPoints(points);
            if(pointsEarned.containsKey(player)){
                pointsEarned.put(player, pointsEarned.get(player) + points);
            }
            else {
                pointsEarned.put(player,points);
            }
        }
    }

    private void calculateAbbeyPoints(Coordinates coordinates, RefereeView refereeView, Map<Player,Integer> pointsEarned) {
        if(this.boardManager.hasTile(coordinates)){
            Tile tile = this.boardManager.getTileAt(coordinates);
            if(tile.hasAbbey() && tile.hasMeepleOnAbbey()){
                if(this.boardManager.isSurrounded(coordinates)){
                    try {
                        Meeple meeple = tile.getAbbeyMeeple();
                        Player player = meeple.getPlayer();
                        refereeView.send("COLLECTS", player.getID(), "regular", meeple.getCoordinates().getX(), meeple.getCoordinates().getY());
                        tile.giveBackAbbeyMeeple();
                        this.updateScore(pointsEarned, Set.of(player), 9);
                    } catch (NoMeepleException | NoAbbeyException | InvalidArgumentNumberException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
