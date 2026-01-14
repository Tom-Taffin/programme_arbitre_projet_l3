package Carcassonne.Edge;

public class EdgeWithRoadVisitor implements EdgeVisitor{

    private final EdgeWithRoad origin;

    public EdgeWithRoadVisitor(EdgeWithRoad origin) {
        this.origin = origin;
    }

    @Override
    public boolean visit(EdgeNoRoad other) {
        return false;
    }

    @Override
    public boolean visit(EdgeWithRoad other) {
        return origin.getZone1() == other.getZone2() && origin.getZone2() == other.getZone1();
    }
    
}
