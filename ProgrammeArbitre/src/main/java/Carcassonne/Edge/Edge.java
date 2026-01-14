package Carcassonne.Edge;

public interface Edge {
    boolean canConnectTo(Edge other);
    boolean accept(EdgeVisitor visitor);
}
