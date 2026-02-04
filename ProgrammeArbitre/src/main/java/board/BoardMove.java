package board;

import l3s6.projet.star.game.board.Board;
import l3s6.projet.star.game.board.Coordinates;
import l3s6.projet.star.game.tile.Direction;
import l3s6.projet.star.game.tile.Tile;

public class BoardMove {
    public static void placeTile(Board board, Tile tile, Coordinates coordinates) throws ImpossibleBoardMove{
        if (board.hasTile(coordinates.upCoordinates())){
            Tile upperTile = board.getTileAt(coordinates.upCoordinates());

            if (!tile.isCompatibleWith(upperTile, Direction.TOP)){
                throw new ImpossibleBoardMove("Upper tile not compatible!");
            }
        }

        if (board.hasTile(coordinates.rightCoordinates())){
            Tile rigthTile = board.getTileAt(coordinates.rightCoordinates());

            if (!rigthTile.isCompatibleWith(tile, Direction.LEFT)){
                throw new ImpossibleBoardMove("Right tile not compatible!");
            }
        }

        if (board.hasTile(coordinates.downCoordinates())){
            Tile downTile = board.getTileAt(coordinates.upCoordinates());

            if (!downTile.isCompatibleWith(tile, Direction.TOP)){
                throw new ImpossibleBoardMove("Down tile not compatible!");
            }
        }

        if (board.hasTile(coordinates.leftCoordinates())){
            Tile leftTile = board.getTileAt(coordinates.upCoordinates());

            if (!leftTile.isCompatibleWith(tile, Direction.RIGHT)){
                throw new ImpossibleBoardMove("Left tile not compatible!");
            }
        }

        board.putTileAt(tile, coordinates);
    }
}
