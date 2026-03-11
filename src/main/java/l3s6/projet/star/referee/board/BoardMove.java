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

        if (board.hasTile(coordinates.upCoordinates())){
            Tile upperTile = board.getTileAt(coordinates.upCoordinates());

            if (!tile.isCompatibleWith(upperTile, Direction.TOP)){
                return false;
            }
        }

        if (board.hasTile(coordinates.rightCoordinates())){
            Tile rigthTile = board.getTileAt(coordinates.rightCoordinates());

            if (!rigthTile.isCompatibleWith(tile, Direction.LEFT)){
                return false;
            }
        }

        if (board.hasTile(coordinates.downCoordinates())){
            Tile downTile = board.getTileAt(coordinates.downCoordinates());

            if (!downTile.isCompatibleWith(tile, Direction.TOP)){
                return false;
            }
        }

        if (board.hasTile(coordinates.leftCoordinates())){
            Tile leftTile = board.getTileAt(coordinates.leftCoordinates());

            if (!leftTile.isCompatibleWith(tile, Direction.RIGHT)){
                return false;
            }
        }

        return true;
    }
}
