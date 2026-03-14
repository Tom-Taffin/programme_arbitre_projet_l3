package l3s6.projet.star.referee.players;

import java.util.ArrayList;

import l3s6.projet.star.game.player.Player;

public class PlayersManager {

    private ArrayList<Player> players = new ArrayList<>();
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

    public void setStartingPlayer(Player player){
        this.currentPlayer = player;
    }

    /**
     * Returns the winning player i.e. the player with most points.
     * If there is only one player left, he is returned.
     */
    public Player winner() {
        return currentPlayer;
    }

}
