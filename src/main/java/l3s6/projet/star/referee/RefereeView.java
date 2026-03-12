package l3s6.projet.star.referee;

import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.AdminView;
import l3s6.projet.star.referee.player.Player;
import l3s6.projet.star.referee.tile.EmptyDeckException;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class RefereeView extends AdminView {
    private Game game;
    private final int MAX_NUMBER_OF_BLAMES = 5;

    public RefereeView(String ipAddress, int port, String id, String path) throws URISyntaxException, InterruptedException, IOException, ParseException {
        super(ipAddress, port, id);
        this.game = new Game(path);
    }

    /**
     * Starts the game. Sends the appropriate starting message to everybody.
     */
    public void startGame(){
        try {
            send("STARTS");
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        offerTile();
    }

    /**
     * Draws tile from deck and offers it to the player. If there are no more cards the game is finished.
     */
    private void offerTile(){
        try {
            Tile tile = game.drawTile();
            send("OFFERS", game.getCurrentPlayer().getID(), tile.toString());
        } catch (EmptyDeckException | WrongTileSyntaxException e) {
            endsGame();
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ends game, sends an appropriate message to everybody.
     */
    private void endsGame(){
        try {
            send("ENDS", game.winner().getID());
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Blames the player with a reason. If the player exceeds max number of blames, he is expelled.
     */
    public void blame(Player player, String reason){
        player.blame();
        try {
            send("BLAMES", player.getID(), reason);
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
        if (player.getNumberOfBlames() > MAX_NUMBER_OF_BLAMES){
            try {
                send("EXPELS", player.getID());
            } catch (InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateOnPlace(String id, String idPrime, String tile, int x, int y) {
        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }

        if (id != idPrime){
            //BLamer le joueur
        }
    }
}
