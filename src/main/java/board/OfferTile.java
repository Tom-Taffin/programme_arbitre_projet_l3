package board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.command.OfferCommand;
import l3s6.projet.star.interaction.network.AdminClient;
import player.Player;
import tile.Deck;
import tile.EmptyDeckException;

public class OfferTile {
    private Deck deck;

    public OfferTile(Deck deck) {
        this.deck = deck;
    }

    public String drawTile() throws EmptyDeckException {
        return deck.drawTile();
    }

/**    public boolean checkIfTileCanBePlaced(Tile tile, Board board){
        if (board.getOutsideFrontierTiles()){
            return true;
        }

        for(Coordinates coord: board.getOutsideFrontierTiles()){
            for (int i=0; i < 4; i++){
                if(BoardMove.checkIfTileCanBePlaced(board, tile, coord)){
                    return true;
                }
                tile.rotateRight();
            }
        }
        return false;
    }*/

    public void offerTile(Tile tile, Player player, AdminClient adminClient) {
        adminClient.offer(player.getName(), tile.toString());
    }
}
