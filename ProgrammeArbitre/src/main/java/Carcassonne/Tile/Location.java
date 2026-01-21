package Carcassonne.Tile;

public enum Location {
    /* Represents the location of the edge */
    TOP, RIGHT, BOTTOM, LEFT;

    /**
     * @return the opposite location
     */
    public Location toOpposite(){
        switch (this) {
            case TOP:
                return Location.BOTTOM;
            case RIGHT:
                return Location.LEFT;
            case LEFT:
                return Location.RIGHT;
            default:
                return Location.TOP;
        }
    }

    /**
     * @return the next location in a counter-clockwise direction
     */
    public Location toLeft(){
        switch (this) {
            case TOP:
                return Location.LEFT;
            case RIGHT:
                return Location.TOP;
            case LEFT:
                return Location.BOTTOM;
            default:
                return Location.RIGHT;
        }
    }

    /**
     * @return the next location in a clockwise direction
     */
    public Location toRigth(){
        switch (this) {
            case TOP:
                return Location.RIGHT;
            case RIGHT:
                return Location.BOTTOM;
            case LEFT:
                return Location.TOP;
            default:
                return Location.LEFT;
        }
    }
}
