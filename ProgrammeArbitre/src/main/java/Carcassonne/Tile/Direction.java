package Carcassonne.Tile;

public enum Direction {
    TOP, RIGHT, BOTTOM, LEFT;

    public Direction oppositeDirection(){
        return switch (this) {
            case Direction.TOP -> BOTTOM;
            case Direction.RIGHT -> LEFT;
            case Direction.BOTTOM -> TOP;
            default -> RIGHT;
        };
    }

    public Direction rotateLeft(){
        return switch (this) {
            case Direction.TOP -> LEFT;
            case Direction.RIGHT -> TOP;
            case Direction.BOTTOM -> RIGHT;
            default -> BOTTOM;
        };
    }

    public Direction rotateRight(){
        return switch (this) {
            case Direction.TOP -> RIGHT;
            case Direction.RIGHT -> BOTTOM;
            case Direction.BOTTOM -> LEFT;
            default -> TOP;
        };
    }
}
