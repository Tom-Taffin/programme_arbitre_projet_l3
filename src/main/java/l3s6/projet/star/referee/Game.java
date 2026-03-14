package l3s6.projet.star.referee;

import l3s6.projet.star.game.edge.Zone;
import l3s6.projet.star.game.meeple.Color;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.referee.board.BoardMove;
import l3s6.projet.star.referee.board.ImpossibleBoardMove;
import l3s6.projet.star.referee.board.OfferTile;
import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.parser.ParseException;
import l3s6.projet.star.referee.player.Player;
import l3s6.projet.star.referee.tile.Deck;
import l3s6.projet.star.referee.tile.EmptyDeckException;

import java.io.IOException;
import java.util.*;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private final Deck deck;
    private final Board board;
    private final Set<Meeple> meeples;
    private final int MAX_NUMBER_OF_BLAMES = 5;
    private final int NB_MEEPLES_PER_PLAYER = 7;

    public Game(String path) throws IOException, ParseException {
        this.board = new Board();
        this.deck = new Deck(path);
        this.meeples = new HashSet<>();
    }

    public int getNbMeeplesPerPlayer() {
        return NB_MEEPLES_PER_PLAYER;
    }

    /**
     * Initializes all the players: Waits for their connection and sets their score to 0.
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void setStartingPlayer(Player player){
        this.currentPlayer = player;
    }
    /**
     * Draws tile from the deck, if it can't be placed, draws another one.
     * Throws exception if deck is empty.
     */
    public Tile drawTile() throws EmptyDeckException, WrongTileSyntaxException {
        Tile tile = new TileBuilder().build(this.deck.drawTile());

        while (!OfferTile.checkIfTileCanBePlaced(tile, this.board)){
            tile = new TileBuilder().build(this.deck.drawTile());
        }
        return tile;
    }

    /**
     * Puts the tile on the board at the given coordinates.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeTile(Tile tile, Coordinates coordinates) throws ImpossibleBoardMove {
        BoardMove.placeTile(this.board, tile, coordinates);
    }

    /**
     * Places a meeple on the tile.
     * Throws ImpossibleBoardMove if the move is impossible
     */
    public void placeMeeple(Tile tile, String type, String position) throws ImpossibleMeepleMoveException {
        if (!this.currentPlayer.hasMeeples()){
            throw new ImpossibleMeepleMoveException("Player doesn't have any meeple.");
        }

        if (!type.equals("regular")){
            throw new ImpossibleMeepleMoveException("Meeple Type is not regular");
        }

        Zone zone = getZoneByPosition(tile, position);

        Set<Zone> connectedZones = zone.getAllBoardConnectingZones();

        for (Meeple meeple: this.meeples){
            if (connectedZones.contains(meeple.getZone())){
                throw new ImpossibleMeepleMoveException("Zone already has a meeple!");
            }
        }

        Meeple meeple = new Meeple(zone, currentPlayer.getColor());

        meeples.add(meeple);
        currentPlayer.decrementMeepleCount();
    }

    private Zone getZoneByPosition(Tile tile, String position) throws ImpossibleMeepleMoveException {
        String tileStr = tile.toString();

        String withoutOrientation = tileStr.substring(1);

        String[] edgeParts = withoutOrientation.split("-");

        Direction[] directions = {Direction.TOP, Direction.RIGHT, Direction.BOTTOM, Direction.LEFT};

        for (int edgeIndex = 0; edgeIndex < edgeParts.length; edgeIndex++) {
            String edgePart = edgeParts[edgeIndex];
            List<Zone> zones = tile.getZones(directions[edgeIndex]);

            List<String> labels = parseZoneLabels(edgePart);

            if (labels.size() != zones.size()) {
                continue;
            }

            for (int i = 0; i < labels.size(); i++) {
                if (labels.get(i).equals(position)) {
                    return zones.get(i);
                }
            }
        }

        throw new ImpossibleMeepleMoveException("Zone position '" + position + "' does not exist on this tile.");
    }

    /**
     * Separates an edge into each zone.
     */
    private List<String> parseZoneLabels(String edgePart) {
        List<String> labels = new ArrayList<>();
        int i = 0;
        while (i < edgePart.length()) {
            int j = i + 1;
            while (j < edgePart.length() && Character.isDigit(edgePart.charAt(j))) {
                j++;
            }
            labels.add(edgePart.substring(i, j));
            i = j;
        }
        return labels;
    }

    /**
     * If a zone is finished, updates scores and gives back meeples.
     * Otherwise, does nothing.
     */
    public void countPoints(Tile tile){
        Direction[] directions = {Direction.TOP, Direction.RIGHT, Direction.BOTTOM, Direction.LEFT};

        for(Direction direction: directions){
            for(Zone zone: tile.getZones(direction)){
                if (zone.isFinished()){
                    Map<Color, Integer> meeples = this.meeplesOnZone(zone);
                    // ToDo Compter les points en fonction de la zone
                }
            }
        }
    }

    private Map<Color, Integer> meeplesOnZone(Zone zone){
        Set<Zone> connectedZones = zone.getConnectingZones();
        HashMap<Color, Integer> meeples = new HashMap<>();

        for (Meeple meeple: this.meeples){
            if (connectedZones.contains(meeple.getZone())){
                meeples.put(meeple.getColor() ,meeples.getOrDefault(meeple.getColor(), 0));
            }
        }

        return meeples;
    }

    /**
     * Returns the winning player i.e. the player with most points.
     * If there is only one player left, he is returned.
     */
    public Player winner() {
        return currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
