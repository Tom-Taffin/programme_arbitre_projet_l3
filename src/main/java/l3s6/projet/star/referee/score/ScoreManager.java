package l3s6.projet.star.referee.score;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import l3s6.projet.star.game.edge.NoMeepleException;
import l3s6.projet.star.game.edge.Topology;
import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.referee.RefereeView;

public class ScoreManager {

    /**
     * After the tile is placed,
     * If a zone is finished, updates players scores and gives back meeples.
     * Sends meeple returned with COLLECT command.
     * Otherwise, does nothing.
     */
    public Map<Player,Integer> calculatePointsEarned(Tile tile, RefereeView refereeView){
        Map<Player, Integer> pointsEarned = new HashMap<>();
        for(Zone zone : tile.getDistinctZone()){
            Set<Zone> zones = zone.getAllBoardConnectingZones();
            if(this.isClosed(zones)){
                Map<Player,Integer> nbMeeples = this.giveBackMeeples(zones, refereeView);
                if(!nbMeeples.isEmpty()){
                    Set<Player> players = this.getPlayerMajority(nbMeeples);
                    int points = this.pointsCalculationWhenCLosed(zones);
                    for(Player player : players){
                        player.addPoints(points);
                        if(pointsEarned.containsKey(player)){
                            pointsEarned.put(player,points);
                        }
                        else {
                            pointsEarned.put(player, pointsEarned.get(player) + points);
                        }
                    }
                }
            }
        }
        return pointsEarned;
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
     * Sends meeple returned with COLLECT command.
     * @return a map of player and his number of meeple in the zones
     */
    private Map<Player, Integer> giveBackMeeples(Set<Zone> zones, RefereeView refereeView) {
        Map<Player, Integer> res = new HashMap<>();
        for(Zone zone : zones){
            if(zone.hasMeeple()){
                Meeple meeple = zone.getMeeple();
                Player player = meeple.getPlayer();
                try {
                    refereeView.send("COLLECT", player.getID(), meeple.getCoordinates().getX(), meeple.getCoordinates().getY());
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
        Set<Zone> distinctZones = this.getDistinctZones(zones);
        Topology topology = Topology.FIELD;
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
            return nbPoints *2;
        }
        return distinctZones.size();
    }

    /**
     * @return one zone per tile for all the tiles covered by zones.
     */
    private Set<Zone> getDistinctZones(Set<Zone> zones) {
        Set<Zone> distinctZones = zones;
        for(Zone zone : zones){
            if(distinctZones.contains(zone)){
                distinctZones.removeAll(zone.getConnectingZones());
            }
        }
        return distinctZones;
    }

    public Map<Player, Integer> calculateEndGamePoints(Set<Zone> zonesWithMeeple) {
        return null;
    }
}
