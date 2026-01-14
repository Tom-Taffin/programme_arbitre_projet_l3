package Carcassonne.Edge;

public class EdgeNoRoad implements Edge {
    private Zone zone;

    public EdgeNoRoad(Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }

    public boolean canConnectTo(EdgeWithRoad edge){
        return false;
    }

    public boolean canConnectTo(EdgeNoRoad edge){
        return edge.getZone() == this.zone;
    }
}
