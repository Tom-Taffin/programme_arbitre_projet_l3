package l3s6.projet.star.referee;

import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.referee.board.BoardManager;
import l3s6.projet.star.referee.board.ImpossibleBoardMove;
import l3s6.projet.star.referee.deck.Deck;
import l3s6.projet.star.referee.deck.EmptyDeckException;
import l3s6.projet.star.referee.players.PlayersManager;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.player.Player;
import l3s6.projet.star.referee.score.ScoreManager;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Game {
    private static final int NB_MEEPLES_PER_PLAYER = 7;

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

    public int getNbMeeplesPerPlayer() {
        return NB_MEEPLES_PER_PLAYER;
    }

    public Tile getLastDrawnTile() {
        return lastDrawnTile;
    }

    public void addPlayer(Player player){
        this.playersManager.addPlayer(player);
    }

    public void setStartingPlayer(Player player){
        this.playersManager.setStartingPlayer(player);
    }

    public Player winner() {
        return playersManager.winner();
    }

    public Player getCurrentPlayer() {
        return playersManager.getCurrentPlayer();
    }

    public ArrayList<Player> getPlayers() {
        return playersManager.getPlayers();
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
     * Puts the tile on the board at the given coordinates.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeTile(Tile tile, Coordinates coordinates) throws ImpossibleBoardMove {
        this.boardManager.placeTile(tile, coordinates);
    }

    public boolean checkIfTileCanBePlaced(Tile tile, Coordinates coordinates){
        return this.boardManager.checkIfTileCanBePlaced(tile, coordinates);
    }

    /**
     * Places a meeple on the tile.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeMeeple(Tile tile, String type, String position) throws ImpossibleMeepleMoveException {
        if (!this.playersManager.getCurrentPlayer().hasMeeples()){
            throw new ImpossibleMeepleMoveException("Player doesn't have any meeple.");
        }
        if (!type.equals("regular")){
            throw new ImpossibleMeepleMoveException("Meeple Type is not regular");
        }

        int index;
        try {
            index = Integer.parseInt(position.substring(1));
        } catch (Exception e) {
            throw new ImpossibleMeepleMoveException("Wrong meeple position syntax");
        }

        Zone zone = tile.getZoneAt(this.parseDirection(position.charAt(0)), index);

        if (this.boardManager.hasMeepleOnBoardZone(zone)){
            throw new ImpossibleMeepleMoveException("There is already meeple on the board zone");
        }

        zone.setMeeple(new Meeple(this.playersManager.getCurrentPlayer()));
    }

    private Direction parseDirection(char direction) throws ImpossibleMeepleMoveException {
        switch (direction) {
            case 'T':
                return Direction.TOP;
            case 'R':
                return Direction.RIGHT;
            case 'B':
                return Direction.BOTTOM;
            case 'L':
                return Direction.LEFT;
            default:
                throw new ImpossibleMeepleMoveException("Wrong meeple position syntax");
        }
    }

    /**
     * Return true if provided tile finishes any zone on the board.
     */
    public boolean checkIfTileFinishesZone(Tile tile){
        return false;
    }

    /**
     * After the tile is placed,
     * If a zone is finished, updates players scores and gives back meeples.
     * Otherwise, does nothing.
     * @return a map with the players who have earned points in keys and the number of points earned in value
     */
    public Map<Player,Integer> calculatePointsEarned(){
        return this.scoreManager.calculatePointsEarned(this.lastDrawnTile);
    }

    public Map<Player, Integer> calculateEndGamePoints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateEndGamePoints'");
    }

    /**
     * Changes the current player for the next one.
     */
    public void changeCurrentPlayer(){
        //ToDo
    }

    /**
     * Returns true if a player with the provided ID exists in this game.
     * */
    public boolean playerExists(String ID){
        for(Player player: this.getPlayers()){
            if (player.getID().equals(ID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the provided player exists in this game.
     * */
    public boolean playerExists(Player player){
        return this.getPlayers().contains(player);
    }

    /**
     * Removes provided player from the game.
     * Removes all his meeples.
     */
    public void removePlayer(Player player){
        //ToDo
    }

    /**
     * Returns the player with the provided ID from the game.
     * If this player doesn't exist, throws an Exception.
     */
    public Player findPlayerFromId(String ID) throws NonExistantPlayerException {
        for(Player player: this.getPlayers()){
            if (player.getID().equals(ID)){
                return player;
            }
        }
        throw new NonExistantPlayerException("This player does not exist.");
    }
}
