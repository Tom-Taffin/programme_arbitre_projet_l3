package l3s6.projet.star.referee.players;

import l3s6.projet.star.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayersManagerTest {
    private PlayersManager playersManager;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void init(){
        this.playersManager = new PlayersManager();
        this.player1 = new Player("A", 8);
        this.player2 = new Player("B", 8);
        this.player3 = new Player("C", 8);

        this.playersManager.addPlayer(this.player1);
        this.playersManager.addPlayer(this.player2);
        this.playersManager.addPlayer(this.player3);

        this.playersManager.setStartingPlayer(this.player1);
    }

    @Test
    public void testChangeCurrentPlayer(){
        // ToDo: A décommenter quand la branche feat/GameMethods sera merge

        //assertEquals(this.player1, this.playersManager.getCurrentPlayer());

        //this.playersManager.changeCurrentPlayer();

        //assertEquals(this.player2, this.playersManager.getCurrentPlayer());

        //this.playersManager.changeCurrentPlayer();

        //assertEquals(this.player3, this.playersManager.getCurrentPlayer());

        //this.playersManager.changeCurrentPlayer();

        assertEquals(this.player1, this.playersManager.getCurrentPlayer());
    }
}
