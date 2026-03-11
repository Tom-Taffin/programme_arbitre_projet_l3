import l3s6.projet.star.game.board.Board;
import org.json.simple.parser.ParseException;
import player.Player;
import tile.Deck;

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

    public void playTurn(Player player){
        // Prendre tuile
        // Vérifier qu'elle peut être placée sur le plateau
        // OFFERS la tuile au joueur

        // Voir si les coordonnées données par le joueur sont correctes
        // Update le plateau si c'est bon

        // Voir si les coordonées pour le meeple données par le joueur sont correctes
        // Update le plateau si c'est bon
        // Voir si une zone se finit
        // Update les scores et rendre les meeples si c'est le cas
    }
}