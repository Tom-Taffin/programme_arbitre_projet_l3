package l3s6.projet.star.referee;

import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.game.tile.Orientation;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.interaction.network.AdminClient;
import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.AdminView;
import l3s6.projet.star.referee.board.InvalidTileMoveException;
import l3s6.projet.star.referee.board.InvalidMeepleMoveException;
import l3s6.projet.star.referee.board.InvalidMeeplePositionException;
import l3s6.projet.star.referee.deck.EmptyDeckException;
import l3s6.projet.star.referee.players.NonExistantPlayerException;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class RefereeView extends AdminView<AdminClient> {
    private final Game game;
    private final int MAX_NUMBER_OF_BLAMES = 5;
    private static final int NB_MEEPLES_PER_PLAYER = 7;
    private static final int TIMEOUT_SECONDS = 120;

    private final ScheduledExecutorService timerExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentTimer;

    private boolean isWaitingForPlaceCommand;
    private boolean isWaitingForPlayCommand;
    private int nbPlayers;

    public RefereeView(String ipAddress, int port, String id, int nbPlayers) throws URISyntaxException, InterruptedException, IOException, ParseException {
        super(ipAddress, port, id);
        this.game = new Game();
        this.nbPlayers = nbPlayers;

        try {
            send("ELECTS", "referee", id);
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        this.isWaitingForPlaceCommand = false;
        this.isWaitingForPlayCommand = true;
    }

    /**
     * wait player send PLAY command and add player
     * start game when all the players are added
     */
    public void updateOnPlay(String id){
        if(!this.isWaitingForPlayCommand){
            try {
                send("EXPELS", id);
            } catch (InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.game.playerExists(id)){
            return;
        }
        this.game.addPlayer(new Player(id, NB_MEEPLES_PER_PLAYER));
        try {
            send("ELECTS", "player", id);
        } catch (InvalidArgumentNumberException e) {
            throw new RuntimeException(e);
        }

        if (this.game.getPlayers().size() == this.nbPlayers){
            this.isWaitingForPlayCommand = false;
            startGame();
        }
    }

    /**
     * Starts the game, send the max number of blames and gives a certain number of meeples to each player.
     * Randomly makes an order for the players then starts a turn by offer a tile.
     */
    public void startGame(){
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
                isWaitingForPlaceCommand = true;
                startTimer(TIMEOUT_SECONDS);
                send("OFFERS", game.getCurrentPlayer().getID(), tile.toString());

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
        if(this.IdIsValid(id, idPrime)){
            try {
                Tile drawnTile = this.game.getLastDrawnTile();
                drawnTile.setOrientation(Orientation.valueOf(orientation));
                this.game.placeTile(drawnTile, new Coordinates(x, y));
                send("PLACES", id, orientation, x, y);
                this.isWaitingForPlaceCommand = false;
                this.calculatePointsEarned();
            } catch (InvalidTileMoveException e) {
                blame(id, "illegal-tile-move");
            } catch (InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e){
                blame(id, "illegal-orientation-syntax");
            }
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
        if(this.IdIsValid(id, idPrime)){
            try {
                Tile drawnTile = this.game.getLastDrawnTile();

                drawnTile.setOrientation(Orientation.valueOf(orientation));
                Coordinates coordinates = new Coordinates(x,y);
                if(this.game.checkIfTileCanBePlaced(drawnTile, coordinates)){
                    this.game.placeMeeple(drawnTile, coordinates, meeple_type, meeple_position);
                    this.game.placeTile(drawnTile, coordinates);

                    send("PLACES", id, orientation, x, y, meeple_type, meeple_position);

                    this.isWaitingForPlaceCommand = false;
                    this.calculatePointsEarned();
                }
                else {
                    blame(id, "illegal-tile-move");
                }
            } catch (IllegalArgumentException  e) {
                blame(id, "illegal-orientation");
            } catch (InvalidTileMoveException e) {
                blame(id, "illegal-tile-move");
            } catch (InvalidArgumentNumberException e) {
                throw new RuntimeException(e);
            } catch (InvalidMeepleMoveException e) {
                blame(id, "illegal-meeple-move");
            } catch (InvalidMeeplePositionException e) {
                blame(id, "illegal-meeple-position-syntax");
            }
        }
    }

    /**
     * @return true if the command is expected, correct and send by the current player
     */
    private Boolean IdIsValid(String id, String idPrime){
        if(!this.isWaitingForPlaceCommand){
            return false;
        }

        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return false;
        }

        if(!this.game.playerExists(idPrime)){
            return false;
        }

        if (id != game.getCurrentPlayer().getID()){
            blame(id, "illegal-turn");
            return false;
        }

        cancelTimer();

        if (!id.equals(idPrime)){
            blame(id, "illegal-id");
            return false;
        }
        return true;
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
            Map<Player,Integer> pointEarned = this.game.calculateEndGamePoints(this);
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

        if(player == game.getCurrentPlayer()){
            startTimer(TIMEOUT_SECONDS);
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
            if(this.game.getCurrentPlayer() == player){
                this.isWaitingForPlaceCommand = false;
                this.game.removePlayer(player, this);
                offerTile();
            } else {
                this.game.removePlayer(player, this);
            }
        }
    }

    /**
     * Start a timer to blame the player in case of a timeout
     */
    private void startTimer(int seconds){
        currentTimer = timerExecutor.schedule(() -> {
            blame(this.game.getCurrentPlayer().getID(), "timeout");
        }, seconds, TimeUnit.SECONDS);
    }

    private void cancelTimer() {
        if (currentTimer != null) {
            currentTimer.cancel(false);
            currentTimer = null;
        }
    }

    public static void main(String[] args) throws InvalidArgumentNumberException {
        if (args.length != 4){
            throw new InvalidArgumentNumberException("Usage : <IPAddress> <Port> <id> <nbPlayers>");
        }
        String IPAddress = args[0];
        int port = Integer.parseInt(args[1]);
        String id = args[2];
        int nbPlayers = Integer.parseInt(args[3]);
        try {
            new RefereeView(IPAddress, port, id, nbPlayers);
        } catch (URISyntaxException | InterruptedException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
