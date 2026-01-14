package Carcassonne.Edge;

public class EdgeNoRoad implements Edge {
    private Zone zone;

    public EdgeNoRoad(Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }

    @Override
    public boolean canConnectTo(Edge other) {
        return other.accept(new EdgeNoRoadVisitor(this));
    }

    @Override
    public boolean accept(EdgeVisitor visitor) {
        return visitor.visit(this);
    }
}
