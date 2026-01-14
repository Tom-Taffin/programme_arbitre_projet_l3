package Carcassonne.Edge;

public class EdgeNoRoadVisitor implements EdgeVisitor{

    private final EdgeNoRoad origin;

    public EdgeNoRoadVisitor(EdgeNoRoad origin) {
        this.origin = origin;
    }

    @Override
    public boolean visit(EdgeNoRoad other) {
        return origin.getZone() == other.getZone();
    }

    @Override
    public boolean visit(EdgeWithRoad other) {
        return false;
    }
    
}
