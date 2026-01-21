package Carcassonne.Tile;

import Carcassonne.Edge.Edge;

public class Tile {
    private Edge topEdge;
    private Edge rightEdge;
    private Edge bottomEdge;
    private Edge leftEdge;

    private Direction direction;
    // Direction where the TOP Edge of the tile is.
    // By default, it's on NORTH.

    public Tile(Edge topEdge, Edge rightEdge, Edge bottomEdge, Edge leftEdge) {
        this.topEdge = topEdge;
        this.rightEdge = rightEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;
        this.direction = Direction.NORTH;
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeDirection(Direction direction){
        this.direction = direction;
    }

    public Edge getEdge(Location location) {
        switch (location) {
            case TOP:
                return topEdge;
            case RIGHT:
                return rightEdge;
            case BOTTOM:
                return bottomEdge;
            default:
                return leftEdge;
        }
    }

    /**
     * @param other
     * @param location
     * @return true if the other tile can connect to the edge location of this tile based on their direction
     */
    public boolean canConnectTo(Tile other, Location location){
        if (this.direction == other.getDirection()){
            return this.getEdge(location).canConnectTo(other.getEdge(location.toOpposite()));
        }
        else if (this.direction == other.getDirection().rotateLeft()){
            return this.getEdge(location).canConnectTo(other.getEdge(location.toRigth()));
        }
        else if (this.direction == other.getDirection().rotateRight()){
            return this.getEdge(location).canConnectTo(other.getEdge(location.toLeft()));
        }
        else {
            return this.getEdge(location).canConnectTo(other.getEdge(location));
        }
    }
}
