package l3s6.projet.star.referee;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Orientation;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.AdminView;
import l3s6.projet.star.referee.board.ImpossibleBoardMoveException;
import l3s6.projet.star.referee.board.ImpossibleMeepleMoveException;
import l3s6.projet.star.referee.deck.EmptyDeckException;
import l3s6.projet.star.referee.players.NonExistantPlayerException;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class RefereeView extends AdminView {
    private final Game game;
    private final int MAX_NUMBER_OF_BLAMES = 5;
    private static final int NB_MEEPLES_PER_PLAYER = 7;

    private boolean isWaitingForPlaceCommandFromPlayer;

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
        if (this.game.playerExists(ID)){
            return;
        }
        this.game.addPlayer(new Player(ID, NB_MEEPLES_PER_PLAYER));
    }

    /**
     * Starts the game and gives a certain number of meeples to each player.
     * Randomly makes an order for the players then starts a turn by drawing a tile.
     */
    public void startGame() throws InvalidArgumentNumberException, WrongTileSyntaxException{
        try {
            send("STARTS");
            send("BLAMES", MAX_NUMBER_OF_BLAMES);
            for(Player player: this.game.getPlayers()){
                send("COLLECTS", player.getID(), NB_MEEPLES_PER_PLAYER);
            }
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
        this.game.setStartingPlayer();
        offerTile();
    }

    /**
     * Beginning of a turn: This method draws tile from deck and offers it to the current player.
     * If there are no more tiles the game ends.
     * If there is only one player he is declared a winner.
     */
    private void offerTile(){
        if(this.game.getPlayers().size() <= 1){
            endsGame();
        }
        else{
            try {
                Tile tile = game.drawTile();
                send("OFFERS", game.getCurrentPlayer().getID(), tile.toString());
                isWaitingForPlaceCommandFromPlayer = true;
            } catch (EmptyDeckException e) {
                endsGame();
            } catch (InvalidArgumentNumberException | WrongTileSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Case where the player places the tile without a meeple.
     * Checks if the received tile placement is correct, then updates the board and informs other players about the move.
     * Blames the player otherwise.
     * Must be called when waiting for PLACES command from current player.
     * Ensures it is correctly used and the right person is calling it.
     */
    @Override
    public void updateOnPlace(String id, String idPrime, String orientation, int x, int y) {
        if(!this.isWaitingForPlaceCommandFromPlayer){
            return;
        }

        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }

        if(!this.game.playerExists(idPrime)){
            return;
        }

        if (!id.equals(idPrime)){
            blame(id, "illegal-id");
            return;
        }
        try {
            Tile drawnTile = this.game.getLastDrawnTile();
            drawnTile.setOrientation(Orientation.valueOf(orientation));
            this.game.placeTile(drawnTile, new Coordinates(x, y));
            send("PLACES", id, orientation, x, y);
            this.isWaitingForPlaceCommandFromPlayer = false;
            this.calculatePointsEarned();
        } catch (ImpossibleBoardMoveException e) {
            blame(id, "illegal-move");
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Case where the player places the tile and a meeple.
     * Checks if the received tile and meeple placement are correct, then updates the board and informs other players about the moves.
     * Blames the player otherwise.
     * Must be called when waiting for PLACES command from current player
     * Ensures it is correctly used and the right person is calling it.
     */
    @Override
    public void updateOnPlaceWithMeeple(String id, String idPrime, String orientation, int x, int y, String meeple_type, String meeple_position) {
        if(!this.isWaitingForPlaceCommandFromPlayer){
            return;
        }

        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }

        if(!this.game.playerExists(idPrime)){
            return;
        }

        if (!id.equals(idPrime)){
            blame(id, "illegal-id");
            return;
        }

        try {
            Tile drawnTile = this.game.getLastDrawnTile();

            drawnTile.setOrientation(Orientation.valueOf(orientation));
            Coordinates coordinates = new Coordinates(x,y);
            if(this.game.checkIfTileCanBePlaced(drawnTile, coordinates)){
                this.game.placeMeeple(drawnTile, coordinates, meeple_type, meeple_position);
                this.game.placeTile(drawnTile, coordinates);

                send("PLACES", id, orientation, x, y, meeple_type, meeple_position);

                this.isWaitingForPlaceCommandFromPlayer = false;
                this.calculatePointsEarned();
            }
            else {
                blame(id, "illegal-move");
            }
        } catch (ImpossibleBoardMoveException | ImpossibleMeepleMoveException e) {
            blame(id, "illegal-move");
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Counts points for each player when a zone is finished.
     * Sends meeple returned with COLLECT command.
     * Sends the amount of points each player won with SCORES command.
     * If no zone is finished, nothing happens.
     */
    private void calculatePointsEarned(){
        Map<Player,Integer> pointEarned = this.game.calculatePointsEarned(this);
        for (Player player : pointEarned.keySet()){
            try {
                send("SCORES", player.getID(), pointEarned.get(player));
            } catch (InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            }
        }
        this.game.changeCurrentPlayer();
        offerTile();
    }

    /**
     * Determines who the winner is and sends a ENDS command.
     */
    private void endsGame(){
        try {
            Map<Player,Integer> pointEarned = this.game.calculateEndGamePoints();
            for (Player player : pointEarned.keySet()){
                send("SCORES", player.getID(), pointEarned.get(player));
            }
            send("ENDS", game.winnersID());
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Blames the player and sends him the reason. If the player exceeds max number of blames, he is expelled.
     */
    private void blame(String ID, String reason){
        Player player;

        try {
            player = this.game.findPlayerFromId(ID);
        } catch (NonExistantPlayerException e) {
            return;
        }

        player.blame();

        try {
            send("BLAMES", player.getID(), reason);
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        if (player.getNumberOfBlames() >= MAX_NUMBER_OF_BLAMES){
            expel(player);
        }
    }

    /**
     * Expels given player from the game.
     */
    private void expel(Player player){
        try {
            send("EXPELS", player.getID());
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        if(this.game.playerExists(player)){
            this.isWaitingForPlaceCommandFromPlayer = false;
            this.game.changeCurrentPlayer();
            offerTile();
            this.game.removePlayer(player);
        }
    }
}
