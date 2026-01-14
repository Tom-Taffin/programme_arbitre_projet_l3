package Carcassonne.Tile;

import Carcassonne.Edge.Edge;

public class Tile {
    private Edge topEdge;
    private Edge rightEdge;
    private Edge bottomEdge;
    private Edge leftEdge;

    private Direction direction;
    // Direction where the top Edge of the tile is.
    // By default, it's on top.

    public Tile(Edge topEdge, Edge rightEdge, Edge bottomEdge, Edge leftEdge) {
        this.topEdge = topEdge;
        this.rightEdge = rightEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;
        this.direction = Direction.TOP;
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeDirection(Direction direction){
        this.direction = direction;
    }

    public Edge getEdge(Direction direction) {
        return switch (direction) {
            case Direction.TOP -> topEdge;
            case Direction.RIGHT -> rightEdge;
            case Direction.BOTTOM -> bottomEdge;
            default -> leftEdge;
        };
    }

    public boolean canConnectTo(Tile tile, Direction placement){
        return this.getEdge(placement).canConnectTo(tile.getEdge(placement.oppositeDirection()));
    }
}
