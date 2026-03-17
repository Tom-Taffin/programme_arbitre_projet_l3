package l3s6.projet.star.referee.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import l3s6.projet.star.game.player.Player;

public class PlayersManager {

    private final ArrayList<Player> players = new ArrayList<>();
    protected Player currentPlayer;

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
     * Returns the winning players.
     * If there is only one player left, he is returned.
     */
    public List<String> winnersID() {
        int max = -1;
        List<String> winners = new ArrayList<>();
        for(Player player : this.players){
            if(player.getScore()>max){
                winners.clear();
                winners.add(player.getID());
                max = player.getScore();
            }
            else if(player.getScore()==max){
                winners.add(player.getID());
            }
        }
        return winners;
    }

    public void changeCurrentPlayer(){
        this.currentPlayer = this.players.get((this.players.indexOf(this.currentPlayer) + 1)%this.players.size());
    }

    /**
     * remove player and change player if it is the current player
     */
    public void removePlayer(Player player){
        if (player.equals(this.currentPlayer)){
            changeCurrentPlayer();
        }

        players.remove(player);
    }

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
