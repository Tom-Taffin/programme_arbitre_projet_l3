package Carcassonne.Tile;

public enum Location {
    TOP, RIGHT, BOTTOM, LEFT;

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
