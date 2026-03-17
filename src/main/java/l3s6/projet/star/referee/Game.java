package l3s6.projet.star.referee;

import l3s6.projet.star.referee.board.BoardManager;
import l3s6.projet.star.referee.board.InvalidTileMoveException;
import l3s6.projet.star.referee.board.InvalidMeepleMoveException;
import l3s6.projet.star.referee.board.InvalidMeeplePositionException;
import l3s6.projet.star.referee.deck.Deck;
import l3s6.projet.star.referee.deck.EmptyDeckException;
import l3s6.projet.star.referee.players.NonExistantPlayerException;
import l3s6.projet.star.referee.players.PlayersManager;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.referee.score.ScoreManager;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Game {
    private PlayersManager playersManager;
    private Deck deck;
    private BoardManager boardManager;
    private Tile lastDrawnTile;
    private ScoreManager scoreManager;

    public Game(String path) throws IOException, ParseException {
        this.boardManager = new BoardManager();
        this.deck = new Deck(path);
        this.playersManager = new PlayersManager();
    }

    /**
     * Draws tile from the deck, if it can't be placed, draws another one.
     * Throws exception if deck is empty.
     */
    public Tile drawTile() throws EmptyDeckException, WrongTileSyntaxException {
        Tile tile = new TileBuilder().build(this.deck.drawTile());

        while (!this.boardManager.hasValidPosition(tile)){
            tile = new TileBuilder().build(this.deck.drawTile());
        }

        lastDrawnTile = tile;
        return tile;
    }

    /**
     * Removes provided player from the game.
     * and change current player if the deleted player was the current player
     * Removes all his meeples.
     * @param refereeView 
     */
    public void removePlayer(Player player, RefereeView refereeView){
        this.boardManager.removeMeeplesFrom(player, refereeView);
        this.playersManager.removePlayer(player);
    }

    public Tile getLastDrawnTile() {
        return lastDrawnTile;
    }

    public ArrayList<Player> getPlayers() {
        return this.playersManager.getPlayers();
    }

    public Player getCurrentPlayer() {
        return playersManager.getCurrentPlayer();
    }

    public void addPlayer(Player player){
        this.playersManager.addPlayer(player);
    }
    
    public void setStartingPlayer(){
        this.playersManager.setStartingPlayer();
    }

    public List<String> winnersID() {
        return playersManager.winnersID();
    }

    public boolean playerExists(Player player){
        return this.playersManager.playerExists(player);
    }

    public boolean playerExists(String ID){
        return this.playersManager.playerExists(ID);
    }

    public void changeCurrentPlayer(){
        this.playersManager.changeCurrentPlayer();
    }

    public Player findPlayerFromId(String ID) throws NonExistantPlayerException {
        return this.playersManager.findPlayerFromId(ID);
    }

    public boolean checkIfTileCanBePlaced(Tile tile, Coordinates coordinates){
        return this.boardManager.checkIfTileCanBePlaced(tile, coordinates);
    }

    public void placeTile(Tile tile, Coordinates coordinates) throws InvalidTileMoveException {
        this.boardManager.placeTile(tile, coordinates);
    }

    public void placeMeeple(Tile tile, Coordinates coordinates, String type, String position) throws InvalidMeepleMoveException, InvalidMeeplePositionException{
        this.boardManager.placeMeeple(tile, coordinates, type, position, this.playersManager.getCurrentPlayer());
    }

    public Map<Player,Integer> calculatePointsEarned(RefereeView refereeView){
        return this.scoreManager.calculatePointsEarned(this.lastDrawnTile, refereeView);
    }

    public Map<Player, Integer> calculateEndGamePoints(RefereeView refereeView) {
        return this.scoreManager.calculateEndGamePoints(this.boardManager.getZonesWithMeeple(), refereeView);
    }
}
