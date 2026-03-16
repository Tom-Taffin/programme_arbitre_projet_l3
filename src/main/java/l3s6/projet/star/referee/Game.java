package l3s6.projet.star.referee;

import l3s6.projet.star.referee.board.BoardManager;
import l3s6.projet.star.referee.board.ImpossibleBoardMoveException;
import l3s6.projet.star.referee.board.ImpossibleMeepleMoveException;
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
     */
    public void removePlayer(Player player){
        this.boardManager.removeMeeplesFrom(player);
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

    public Player winner() {
        return playersManager.winner();
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

    public void placeTile(Tile tile, Coordinates coordinates) throws ImpossibleBoardMoveException {
        this.boardManager.placeTile(tile, coordinates);
    }

    public void placeMeeple(Tile tile, String type, String position) throws ImpossibleMeepleMoveException{
        this.boardManager.placeMeeple(tile, type, position, this.playersManager.getCurrentPlayer());
    }

    public boolean checkIfTileFinishesZone(Tile tile){
        return this.boardManager.checkIfTileFinishesZone(tile);
    }

    public Map<Player,Integer> calculatePointsEarned(){
        return this.scoreManager.calculatePointsEarned(this.lastDrawnTile);
    }

    public Map<Player, Integer> calculateEndGamePoints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateEndGamePoints'");
    }
}
