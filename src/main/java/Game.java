import l3s6.projet.star.game.board.Board;
import player.Player;
import tile.Deck;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players;
    private Player playingPlayer;
    private Deck deck;
    private Board board;

    public Game() {
        this.players = new ArrayList<>();
    }
}
