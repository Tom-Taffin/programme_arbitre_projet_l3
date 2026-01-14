package Carcassonne.Edge;

public class EdgeWithRoad implements Edge {
    private Zone zone1;
    private Zone zone2;

    public EdgeWithRoad(Zone zone1, Zone zone2) {
        this.zone1 = zone1;
        this.zone2 = zone2;
    }

    public Zone getZone1() {
        return zone1;
    }

    public Zone getZone2() {
        return zone2;
    }

    @Override
    public boolean canConnectTo(Edge other) {
        return other.accept(new EdgeWithRoadVisitor(this));
    }

    @Override
    public boolean accept(EdgeVisitor visitor) {
        return visitor.visit(this);
    }
}
