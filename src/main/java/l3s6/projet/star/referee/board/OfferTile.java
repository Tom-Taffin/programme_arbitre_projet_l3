package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.network.AdminClient;
import l3s6.projet.star.referee.player.Player;

public class OfferTile {

 /**
 * Returns true if the given tile can be placed on the board. Checks tile with all neighboring empty tiles on the board, with all orientations.
 */
   public static boolean checkIfTileCanBePlaced(Tile tile, Board board){
        for(Coordinates coord: board.getOutsideFrontierTiles()){
            for (int i=0; i < 4; i++){
                if(BoardMove.checkIfTileCanBePlaced(board, tile, coord)){
                    return true;
                }
                tile.rotateRight();
            }
        }
        return false;
    }

    /**
     * Gives the literal representation of a given tile to the given player with OFFER command.
     */
    public static void offerTile(Tile tile, Player player, AdminClient adminClient) {
        adminClient.offer(player.getName(), tile.toString());
    }
}
