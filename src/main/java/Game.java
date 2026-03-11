import board.OfferTile;
import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.parser.ParseException;
import player.Player;
import tile.Deck;
import tile.EmptyDeckException;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Deck deck;
    private Board board;

    public Game(String path) throws IOException, ParseException {
        initializePlayers();
        this.board = new Board();
        this.deck = new Deck(path);
        this.currentPlayer = players.get(0);
    }

    private void initializePlayers(){
        //ToDo: Attendre la connection de tout les joueurs et les mettre dans players
    }

    public void playGame() throws WrongTileSyntaxException {
        while(!this.deck.isEmpty()){
            try {
                playTurn();
                currentPlayer = players.get((players.indexOf(currentPlayer) + 1)% players.size());
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    private void playTurn() throws EmptyDeckException, WrongTileSyntaxException {
        Tile tile = new TileBuilder().build(this.deck.drawTile());

        while (!OfferTile.checkIfTileCanBePlaced(tile, this.board)){
            tile = new TileBuilder().build(this.deck.drawTile()); // ToDo: Gérer le cas où le deck est vide quand on repioche
        }

        //OfferTile.offerTile(tile, player, new AdminClient()); ToDo: Communiquer correctement avec le joueur

        // Voir si les coordonnées données par le joueur sont correctes
        // Update le plateau si c'est bon

        // Voir si les coordonées pour le meeple données par le joueur sont correctes
        // Update le plateau si c'est bon
        // Voir si une zone se finit
        // Update les scores et rendre les meeples si c'est le cas
    }
}
