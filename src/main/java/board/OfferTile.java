package board;

import l3s6.projet.star.game.tile.Tile;
import l3s6.projet.star.interaction.network.AdminClient;
import player.Player;

public class OfferTile {
/**    public static boolean checkIfTileCanBePlaced(Tile tile, Board board){
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

    public static void offerTile(Tile tile, Player player, AdminClient adminClient) {
        adminClient.offer(player.getName(), tile.toString());
    }
}
