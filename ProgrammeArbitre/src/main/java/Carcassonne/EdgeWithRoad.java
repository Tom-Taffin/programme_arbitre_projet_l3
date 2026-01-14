package Carcassonne;

public class EdgeWithRoad implements Edge {
    public boolean hasRoad(){
        return true;
    }

    public boolean canConnectTo(){
        return false;
    }
}
