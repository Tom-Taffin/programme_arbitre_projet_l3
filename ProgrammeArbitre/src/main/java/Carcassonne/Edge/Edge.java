package Carcassonne.Edge;

public interface Edge {
    public boolean canConnectTo(EdgeNoRoad edge);

    public boolean canConnectTo(EdgeWithRoad edge);
}
