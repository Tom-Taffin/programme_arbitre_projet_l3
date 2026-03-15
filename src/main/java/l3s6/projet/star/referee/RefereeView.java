package l3s6.projet.star.referee;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Orientation;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.AdminView;
import l3s6.projet.star.referee.board.ImpossibleBoardMove;
import l3s6.projet.star.referee.deck.EmptyDeckException;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Objects;

public class RefereeView extends AdminView {
    private final Game game;
    private final int MAX_NUMBER_OF_BLAMES = 5;

    private boolean isWaitingForPlaceCommandFromPlayer;
    private Tile drawedTile;

    public RefereeView(String ipAddress, int port, String id, String path) throws URISyntaxException, InterruptedException, IOException, ParseException {
        super(ipAddress, port, id);
        this.game = new Game(path);

        this.isWaitingForPlaceCommandFromPlayer = false;
    }

    /**
     * Initializes player when it connects.
     * Adds him to the game.
     */
    private void initializePlayer(String ID){
        this.game.addPlayer(new Player(ID, this.game.getNbMeeplesPerPlayer()));
    }

    /**
     * Starts the game and gives a certain number of meeples to each player.
     * Randomly makes an order for the players then starts a turn by drawing a tile.
     */
    public void startGame() throws InvalidArgumentNumberException, WrongTileSyntaxException{
        try {
            send("STARTS");
            for(Player player: this.game.getPlayers()){
                send("COLLECTS", player.getID(), this.game.getNbMeeplesPerPlayer());
            }
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        Collections.shuffle(this.game.getPlayers());
        this.game.setStartingPlayer(this.game.getPlayers().get(0));
        offerTile();
    }

    /**
     * Beginning of a turn: This method draws tile from deck and offers it to the current player.
     * If there are no more tiles the game ends.
     * If there is only one player he is declared a winner.
     */
    private void offerTile() throws InvalidArgumentNumberException, WrongTileSyntaxException{
        if(this.game.getPlayers().size() <= 1){
            endsGame();
        }
        else{
            try {
                Tile tile = game.drawTile();
                this.drawedTile = tile;
                send("OFFERS", game.getCurrentPlayer().getID(), tile.toString());
                isWaitingForPlaceCommandFromPlayer = true;
            } catch (EmptyDeckException e) {
                endsGame();
            }
        }
    }

    /**
     * Checks if the received tile placement is correct, then updates the board and informs other players.
     * Blames the player otherwise.
     * Must be called when waiting for PLACES command from current player
     * Ensures it is correctly used and the right person is calling it.
     */
    @Override
    public void updateOnPlace(String id, String player, String orientation, int x, int y) {
        if(!this.isWaitingForPlaceCommandFromPlayer){
            return;
        }

        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }

        if (!Objects.equals(id, player)){
            //BLamer le joueur
            return;
        }
        try {
            this.drawedTile.setOrientation(Orientation.valueOf(orientation));
            this.game.placeTile(this.drawedTile, new Coordinates(x, y));
            send("PLACES", player, orientation, x, y);
            this.isWaitingForPlaceCommandFromPlayer = false;
            countPoints(this.drawedTile);
        } catch (IllegalArgumentException | NullPointerException e) {
            //Blame le joueur
        } catch (ImpossibleBoardMove e) {
            // Blamer le joueur
        } catch (InvalidArgumentNumberException e) {
            // Blame le joueur
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the received tile and meeple placement are correct, then updates the board and informs other players.
     * Blames the player otherwise.
     * Must be called when waiting for PLACES command from current player
     * Ensures it is correctly used and the right person is calling it.
     */
    @Override
    public void updateOnPlaceWithMeeple(String id, String player, String orientation, int x, int y, String meeple_type, String meeple_position) {
        if(!this.isWaitingForPlaceCommandFromPlayer){
            return;
        }
        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }
        if (!Objects.equals(id, player)){
            //BLamer le joueur
            return;
        }
        try {
            this.drawedTile.setOrientation(Orientation.valueOf(orientation));
            if(this.game.checkIfTileCanBePlaced(drawedTile, new Coordinates(x, y))){
                this.game.placeMeeple(this.drawedTile, meeple_type, meeple_position);
                this.game.placeTile(this.drawedTile, new Coordinates(x, y));
                send("PLACES", player, orientation, x, y, meeple_type, meeple_position);
                this.isWaitingForPlaceCommandFromPlayer = false;
                countPoints(this.drawedTile);
            }
            else {
                //Blame le joueur
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            //Blame le joueur
        } catch (ImpossibleBoardMove e) {
            // Blamer le joueur
        } catch (InvalidArgumentNumberException e) {
            // Blame le joueur
            throw new RuntimeException(e);
        } catch (ImpossibleMeepleMoveException e) {
            // Blame le joueur
        }
    }

    /**
     * Counts points for each player when a zone is finished.
     * If no zone is finished, nothing happens.
     */
    private void countPoints(Tile tile){

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
     * Blames the player, gives him a reason. If the player exceeds max number of blames, he is expelled.
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
}
