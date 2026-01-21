package Carcassonne.Tile;

public enum Direction {
    /* Represents the direction of the tile. */
    NORTH, SOUTH, WEST, EAST;

    public Direction rotateHalf(){
        switch (this) {
            case NORTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.WEST;
            case SOUTH:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }

    public Direction rotateLeft(){
        switch (this) {
            case NORTH:
                return Direction.WEST;
            case EAST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.EAST;
            default:
                return Direction.SOUTH;
        }
    }

    public Direction rotateRight(){
        switch (this) {
            case NORTH:
                return Direction.EAST;
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
            default:
                return Direction.NORTH;
        }
    }
}
