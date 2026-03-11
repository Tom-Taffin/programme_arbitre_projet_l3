import l3s6.projet.star.game.board.Board;
import org.json.simple.parser.ParseException;
import player.Player;
import tile.Deck;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player playingPlayer;
    private Deck deck;
    private Board board;

    public Game(String path) throws IOException, ParseException {
        initializePlayers();
        this.board = new Board();
        this.deck = new Deck(path);
        this.playingPlayer = players.get(0);
    }

    private void initializePlayers(){
        //ToDo: Attendre la connection de tout les joueurs et les mettre dans players
    }
}
