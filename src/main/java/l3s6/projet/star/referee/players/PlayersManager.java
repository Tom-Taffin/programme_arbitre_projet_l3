package l3s6.projet.star.referee.players;

import java.util.ArrayList;
import java.util.Collections;

import l3s6.projet.star.game.player.Player;

public class PlayersManager {

    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Set randomly the order of the players
     */
    public void setStartingPlayer(){
        Collections.shuffle(this.players);
        this.currentPlayer = this.players.get(0);
    }

    /**
     * Returns the winning player i.e. the player with most points.
     * If there is only one player left, he is returned.
     */
    public Player winner() {
        return currentPlayer;
    }

    public void changeCurrentPlayer(){
        this.currentPlayer = this.players.get((this.players.indexOf(this.currentPlayer) + 1)%this.players.size());
    }

    public void removePlayer(Player player){
        if (player.equals(this.currentPlayer)){
            changeCurrentPlayer();
        }

        players.remove(player);

    /**
     * Returns true if the provided player exists in this game.
     * */
    public boolean playerExists(Player player){
        return this.players.contains(player);
    }

    /**
     * Returns true if a player with the provided ID exists in this game.
     * */
    public boolean playerExists(String ID){
        for(Player player: this.getPlayers()){
            if (player.getID().equals(ID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the player with the provided ID from the game.
     * If this player doesn't exist, throws an Exception.
     */
    public Player findPlayerFromId(String ID) throws NonExistantPlayerException {
        for(Player player: this.players){
            if (player.getID().equals(ID)){
                return player;
            }
        }
        throw new NonExistantPlayerException("This player does not exist.");
    }

}
