package name.nicholasgribanov.simplegraph;

import java.util.Objects;

public class Edge<V> {
    private Vertex<V> from;
    private Vertex<V> to;

    public Edge(Vertex<V> from, Vertex<V> to) {
        this.from = from;
        this.to = to;
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public Vertex<V> getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<V> edge = (Edge<V>) o;
        return Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return from.getName() + " " + to.getName();
    }
}
