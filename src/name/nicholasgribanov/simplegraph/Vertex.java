package name.nicholasgribanov.simplegraph;

import java.util.*;
import java.util.stream.Collectors;

public class Vertex<V> {
    private V name;
    private Set<Edge<V>> edges = new LinkedHashSet<>();

    public Vertex(V name) {
        this.name = name;
    }

    public V getName() {
        return name;
    }

    public Set<Edge<V>> getEdges() {
        return edges;
    }

    public List<Vertex<V>> getAllOutVertex(){
        List<Vertex<V>> vertices = new ArrayList<>();
        edges.forEach(edge -> {
            vertices.add(edge.getTo());
        });

        return vertices.stream().distinct().collect(Collectors.toList());
    }

    public boolean addEdge(Edge<V> edge) {
        return this.edges.add(edge);
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<V> vertex = (Vertex<V>) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
