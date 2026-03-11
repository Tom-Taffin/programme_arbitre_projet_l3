import board.BoardMove;
import board.OfferTile;
import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.network.AdminClient;
import org.json.simple.parser.ParseException;
import player.Player;
import tile.Deck;
import tile.EmptyDeckException;

import java.io.IOException;

public class Turn {
    private Deck deck;
    private Board board;

    public Turn(String deckPath) throws IOException, ParseException {
        this.deck = new Deck(deckPath);
        this.board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public void playTurn(Player player) throws EmptyDeckException, WrongTileSyntaxException {
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