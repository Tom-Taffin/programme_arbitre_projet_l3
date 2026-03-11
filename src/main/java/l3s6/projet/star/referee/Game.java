package l3s6.projet.star.referee;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Deck deck;
    private Board board;
    private final Map<Meeple, Player> meeples;

    public Game(String path) throws IOException, ParseException {
        initializePlayers();
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
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    /**
     * Plays a turn for the current player.
     */
    private void playTurn() throws EmptyDeckException, WrongTileSyntaxException {
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
    private Tile tileMove() throws EmptyDeckException, WrongTileSyntaxException {
        Tile tile = new TileBuilder().build(this.deck.drawTile());

        while (!OfferTile.checkIfTileCanBePlaced(tile, this.board)){
            tile = new TileBuilder().build(this.deck.drawTile());
        }

        //OfferTile.offerTile(tile, player, new AdminClient()); ToDo: Communiquer correctement avec le joueur et récupérer sa réponse dans coord

        Coordinates coordinates = null;

        if (BoardMove.checkIfTileCanBePlaced(this.board, tile, coordinates)){
            currentPlayer.blame();
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
}
