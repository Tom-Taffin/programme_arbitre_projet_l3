package l3s6.projet.star.referee;

import l3s6.projet.star.game.edge.Zone;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private final Deck deck;
    private final Board board;
    private final Set<Meeple> meeples;

    public Game(String path) throws IOException, ParseException {
        this.board = new Board();
        this.deck = new Deck(path);
        this.meeples = new HashSet<>();
    }

    /**
     * Initializes all the players: Waits for their connection and sets their score to 0.
     */
    public void addPlayer(Player player){
        this.players.add(player);
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
     */
    public void placeTile(Tile tile, Coordinates coordinates) throws ImpossibleBoardMove {
        BoardMove.placeTile(this.board, tile, coordinates);
    }

    /**
     * Places a meeple on the tile.
     */
    public void placeMeeple(Tile tile, String type, String position) throws ImpossibleMeepleMoveException {
        if (!this.currentPlayer.hasMeeples()){
            throw new ImpossibleMeepleMoveException("Player doesn't have any meeple.");
        }

        Zone zone = getZoneByPosition(tile, position);

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
     * If the tile finishes a zone, updates scores and gives back meeples.
     * Otherwise, does nothing.
     * @param tile the tiles that was placed by the player
     */
    private void moveConsequences(Tile tile){
        // ToDo: Check si la tuile finish des zones.

        // ToDo: Update les scores

        // ToDo: Rendre les meeples
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

    /**
     * wait players connections and initialize the players.
     */
    public void initializePlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializePlayers'");
    }

    /**
     * choice randomly the first player
     */
    public void initializeFirstPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeFirstPlayer'");
    }

    /**
     * is finshed if the deck is empty or if there is only one player.
     */
    public boolean isFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinished'");
    }

    /**
     * draw a tile, checks which one can be placed and offers it to the current player.
     */
    public void offerTile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerTile'");
    }

    public void changeCurrentPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeCurrentPlayer'");
    }

    /**
     * wait player move x secondes, check it. 
     * If no response or wrong response or other player response send blame.
     * If player ban, remove player in players
     * @return false if the current player is ban
     */
    public Boolean checkPlayerMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPlayerMove'");
    }

    /**
     * send the current player move to all the player
     */
    public void sendMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMove'");
    }

    /**
     * place the tile on the board, 
     * place the meeple on the tile,
     * check closed zones, 
     * give back meeple to players 
     * and send score and meeple informations
     */
    public void playMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playMove'");
    }

    public void sendWinner() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWinner'");
    }

}
