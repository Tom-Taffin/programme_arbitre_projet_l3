package l3s6.projet.star.referee.players;

public class PlayersManagerMock extends PlayersManager {

    /**
     * Removes randomness from the method.
     */
    @Override
    public void setStartingPlayer(){
        this.currentPlayer = this.getPlayers().get(0);
    }
}
