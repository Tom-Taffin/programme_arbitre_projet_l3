package l3s6.projet.star.referee.players;

import l3s6.projet.star.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayersManagerTest {
    private PlayersManager playersManager;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void init(){
        this.playersManager = new PlayersManagerMock();
        this.player1 = new Player("A", 8);
        this.player2 = new Player("B", 8);
        this.player3 = new Player("C", 8);

        this.playersManager.addPlayer(this.player1);
        this.playersManager.addPlayer(this.player2);
        this.playersManager.addPlayer(this.player3);

        this.playersManager.setStartingPlayer();
    }

    @Test
    public void testChangeCurrentPlayer(){
        assertEquals(this.player1, this.playersManager.getCurrentPlayer());
        this.playersManager.changeCurrentPlayer();
        assertEquals(this.player2, this.playersManager.getCurrentPlayer());
        this.playersManager.changeCurrentPlayer();
        assertEquals(this.player3, this.playersManager.getCurrentPlayer());
        this.playersManager.changeCurrentPlayer();
        assertEquals(this.player1, this.playersManager.getCurrentPlayer());
    }

    @Test
    public void testWinnerIDSingleWinner(){
        this.player1.addPoints(10);
        this.player2.addPoints(5);
        this.player3.addPoints(20);

        List<String> winners = this.playersManager.winnersID();

        assertFalse(winners.contains(this.player1.getID()));
        assertFalse(winners.contains(this.player2.getID()));
        assertTrue(winners.contains(this.player3.getID()));
    }

    @Test
    public void testWinnerIDMultipleWinners(){
        this.player1.addPoints(10);
        this.player2.addPoints(20);
        this.player3.addPoints(20);

        List<String> winners = this.playersManager.winnersID();

        assertFalse(winners.contains(this.player1.getID()));
        assertTrue(winners.contains(this.player2.getID()));
        assertTrue(winners.contains(this.player3.getID()));
    }

    @Test
    public void testWinnerIDSinglePlayer(){
        this.playersManager.removePlayer(this.player1);
        this.playersManager.removePlayer(this.player2);

        List<String> winners = this.playersManager.winnersID();

        assertFalse(winners.contains(this.player1.getID()));
        assertFalse(winners.contains(this.player2.getID()));
        assertTrue(winners.contains(this.player3.getID()));
    }

    @Test
    public void testRemoveNonExistentPlayer(){
        List<Player> players = this.playersManager.getPlayers();

        this.playersManager.removePlayer(new Player("D", 8));
        assertEquals(players, this.playersManager.getPlayers());

        this.playersManager.removePlayer(new Player("A", 8));
        assertEquals(players, this.playersManager.getPlayers());

        assertEquals(this.player1, this.playersManager.getCurrentPlayer());
    }

    @Test
    public void testRemoveNotCurrentPlayer(){
        List<Player> players = new ArrayList<>();
        players.add(this.player1);
        players.add(this.player3);

        this.playersManager.removePlayer(this.player2);
        assertEquals(players, this.playersManager.getPlayers());

        assertEquals(this.player1, this.playersManager.getCurrentPlayer());
    }

    @Test
    public void testRemoveCurrentPlayer(){
        List<Player> players = new ArrayList<>();
        players.add(this.player2);
        players.add(this.player3);

        this.playersManager.removePlayer(this.player1);
        assertEquals(players, this.playersManager.getPlayers());

        assertEquals(this.player2, this.playersManager.getCurrentPlayer());
    }

    @Test
    public void testPlayerExists(){
        assertTrue(this.playersManager.playerExists("A"));
        assertFalse(this.playersManager.playerExists("D"));

        assertTrue(this.playersManager.playerExists(this.player1));
        assertFalse(this.playersManager.playerExists(new Player("A", 8)));
    }
}
    