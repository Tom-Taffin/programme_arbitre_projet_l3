package Carcassonne.Tile;

public enum Direction {
    TOP, RIGHT, BOTTOM, LEFT;

    public Direction oppositeDirection(){
        switch (this) {
            case TOP:
                return Direction.BOTTOM;
            case RIGHT:
                return Direction.LEFT;
            case BOTTOM:
                return Direction.TOP;
            default:
                return Direction.RIGHT;
        }
    }

    public Direction rotateLeft(){
        switch (this) {
            case TOP:
                return Direction.LEFT;
            case RIGHT:
                return Direction.TOP;
            case BOTTOM:
                return Direction.RIGHT;
            default:
                return Direction.BOTTOM;
        }
    }

    public Direction rotateRight(){
        switch (this) {
            case TOP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.BOTTOM;
            case BOTTOM:
                return Direction.LEFT;
            default:
                return Direction.TOP;
        }
    }
}
