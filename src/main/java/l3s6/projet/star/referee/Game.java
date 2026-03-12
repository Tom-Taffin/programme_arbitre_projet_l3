package l3s6.projet.star.referee;

import l3s6.projet.star.interaction.command.InvalidArgumentNumberException;
import l3s6.projet.star.referee.board.BoardMove;
import l3s6.projet.star.referee.board.ImpossibleBoardMove;
import l3s6.projet.star.referee.board.OfferTile;
import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.meeple.Meeple;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.game.tile.TileBuilder;
import l3s6.projet.star.game.tile.WrongTileSyntaxException;
import org.json.simple.parser.ParseException;
import l3s6.projet.star.referee.player.Player;
import l3s6.projet.star.referee.tile.Deck;
import l3s6.projet.star.referee.tile.EmptyDeckException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Deck deck;
    private Board board;
    private final Map<Meeple, Player> meeples;
    private final RefereeView refereeView;
    private final int MAX_NUMBER_OF_BLAMES = 5;

    public Game(String path) throws IOException, ParseException, URISyntaxException, InterruptedException {
        initializePlayers();
        this.refereeView = new RefereeView("0", 0, "0");
        this.board = new Board();
        this.deck = new Deck(path);
        this.currentPlayer = players.get(0);
        this.meeples = new HashMap<>();
    }

    /**
     * Initializes all the players: Waits for their connection and sets their score to 0.
     */
    private void initializePlayers(){
        //ToDo: Attendre la connection de tout les joueurs et les mettre dans players
    }

    public void playGame() throws WrongTileSyntaxException {
        while(!this.deck.isEmpty()){
            try {
                playTurn();
                currentPlayer = players.get((players.indexOf(currentPlayer) + 1)% players.size());
            } catch (EmptyDeckException | InvalidArgumentNumberException e) {
                break;
            }
        }
    }

    /**
     * Plays a turn for the current player.
     */
    private void playTurn() throws EmptyDeckException, WrongTileSyntaxException, InvalidArgumentNumberException {
        Tile tile = tileMove();
        meepleMove(tile);

        // Voir si une zone se finit
        // Update les scores et rendre les meeples si c'est le cas
    }

    /**
     * Part of the turn where we offer a tile to the player, and he gives an answer about where to place it.
     * Checks if the tile can be placed, draws a new one if it can't.
     * Updates the board.
     * Blames the player if the position is wrong.
     */
    private Tile tileMove() throws EmptyDeckException, WrongTileSyntaxException, InvalidArgumentNumberException {
        Tile tile = new TileBuilder().build(this.deck.drawTile());

        while (!OfferTile.checkIfTileCanBePlaced(tile, this.board)){
            tile = new TileBuilder().build(this.deck.drawTile());
        }

        refereeView.send("OFFERS", currentPlayer.getID(), tile.toString());


        // ToDo: Récupérer réponse du joueur dans coordinates
        Coordinates coordinates = null;

        if (BoardMove.checkIfTileCanBePlaced(this.board, tile, coordinates)){
            currentPlayer.blame();
            refereeView.send("BLAMES", currentPlayer.getID(), "illegal-");
            if (currentPlayer.getNumberOfBlames() > MAX_NUMBER_OF_BLAMES){
                refereeView.send("EXPELS", currentPlayer.getID());
            }
            return null;
        }
        try {
            BoardMove.placeTile(this.board, tile, coordinates);
        } catch (ImpossibleBoardMove e) {
            throw new RuntimeException(e);
            // Should not occur as we test it just before
        }

        return tile;
    }

    /**
     * Part of the turn where the player places a meeple, if he has one, on the tile.
     * Blames the player if the zone already has a meeple.
     * @param tile the tiles that was placed by the player
     */
    private void meepleMove(Tile tile){
        if (!this.currentPlayer.hasMeeples()){
            return;
        }

        // ToDo: Récupérer la réponse du joueur concernant là où il veut placer le meeple, et la placer dans les variables direction et part.
        Direction direction = Direction.TOP;
        int part = 0;

        // ToDo: Check if the position given by the player is correct
        Meeple meeple = new Meeple(tile.getZoneAt(direction, part));

        meeples.put(meeple, currentPlayer);
        currentPlayer.decrementMeepleCount();
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
}
