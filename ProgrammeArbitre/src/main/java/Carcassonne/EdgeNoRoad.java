package Carcassonne;

public class EdgeNoRoad implements Edge {
    public boolean hasRoad(){
        return false;
    }

    public boolean canConnectTo(){
        return false;
    }
}
