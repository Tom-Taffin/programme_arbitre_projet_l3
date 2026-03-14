package l3s6.projet.star.referee.board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;

public class BoardMove {
    /**
     * Places the given tile at the given coordinates on the board.
     * @throws ImpossibleBoardMove if the tile can't be placed.
     */
    public static void placeTile(Board board, Tile tile, Coordinates coordinates) throws ImpossibleBoardMove{
        if (!BoardMove.checkIfTileCanBePlaced(board, tile, coordinates)){
            throw new ImpossibleBoardMove("Tile not compatible!");
        }

        board.putTileAt(tile, coordinates);
    }

    /**
     * @return True if the given tile can be placed on the board at the given coordinates. Checks if neighboring tiles are compatible.
     */
    public static boolean checkIfTileCanBePlaced(Board board, Tile tile, Coordinates coordinates){
        if (board.hasTile(coordinates)){
            return false;
        }

        for(Direction direction : Direction.values()){
            if (board.hasTile(coordinates.getAdjacent(direction))){
                Tile adjacentTile = board.getTileAt(coordinates.getAdjacent(direction));
                if (!tile.isCompatibleWith(adjacentTile, direction)){
                    return false;
                }
            }
        }

        return true;
    }
}
