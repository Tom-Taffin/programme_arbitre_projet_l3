package Carcassonne.Tile;

import Carcassonne.Edge.Edge;

public class Tile {
    private Edge topEdge;
    private Edge rightEdge;
    private Edge bottomEdge;
    private Edge leftEdge;

    public Tile(Edge topEdge, Edge rightEdge, Edge bottomEdge, Edge leftEdge) {
        this.topEdge = topEdge;
        this.rightEdge = rightEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;
    }

    public Edge getEdge(Direction direction) {
        switch (direction) {
            case Direction.TOP:
                return topEdge;
            case Direction.RIGHT:
                return rightEdge;
            case Direction.BOTTOM:
                return bottomEdge;
            default:
                return leftEdge;
        }
    }
}
