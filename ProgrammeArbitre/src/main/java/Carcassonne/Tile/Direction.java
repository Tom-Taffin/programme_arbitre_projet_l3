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
}
