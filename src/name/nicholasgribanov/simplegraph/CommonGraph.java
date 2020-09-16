package name.nicholasgribanov.simplegraph;

import name.nicholasgribanov.simplegraph.exceptions.PathNotFoundException;
import name.nicholasgribanov.simplegraph.exceptions.VertexNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommonGraph<V> implements Graph<V> {
    protected List<Vertex<V>> vertices = new ArrayList<>();

    @Override
    public boolean addVertex(Vertex<V> vertex) {
        return vertices.add(vertex);
    }

    @Override
    public abstract boolean addEdge(Vertex<V> source, Vertex<V> target) throws VertexNotFoundException;

    @Override
    public List<Edge<V>> getPath(Vertex<V> source, Vertex<V> target) throws PathNotFoundException, VertexNotFoundException {
        if (!vertices.contains(source) || !vertices.contains(target)) {
            throw new VertexNotFoundException("One of the vertices " + source + ", " + target + " not found");
        }
        Set<Edge<V>> edges = new LinkedHashSet<>();
        vertices.stream().map(Vertex::getEdges).forEach(edges::addAll);
        List<Edge<V>> result = new LinkedList<>();

        Vertex<V> vertexSource = vertices.stream().filter(v -> v.equals(source)).findFirst().get();
        Vertex<V> vertexTarget = vertices.stream().filter(v -> v.equals(target)).findFirst().get();

        if (isLoopEdge(edges, vertexSource, vertexTarget))
            return edges.stream().filter(edge -> isValidEdge(vertexSource, vertexTarget, edge)).collect(Collectors.toList());

        Queue<Vertex<V>> needPath = new LinkedList<>();
        Set<Vertex<V>> alreadyLooked = new HashSet<>();
        needPath.add(vertexSource);

        while (!needPath.isEmpty()) {
            Vertex<V> vertex = needPath.remove();
            if (vertexTarget.equals(vertex)) {
                edges
                        .stream()
                        .filter(e -> isValidEdge(vertex, vertexTarget, e))
                        .findFirst()
                        .ifPresent(result::add);

                return choosingPath(result, vertexSource, vertexTarget);
            }
            alreadyLooked.add(vertex);
            for (Vertex<V> v : vertex.getAllOutVertex()) {
                if (!alreadyLooked.contains(v)) {
                    edges
                            .stream()
                            .filter(e -> isValidEdge(vertex, v, e))
                            .findFirst()
                            .ifPresent(result::add);

                    if (vertexTarget.equals(v)) {
                        return choosingPath(result, vertexSource, vertex);
                    }
                    needPath.add(v);
                }
            }
        }

        throw new PathNotFoundException("Path from " + source + " to " + target + " not found");
    }

    private boolean isLoopEdge(Set<Edge<V>> edges, Vertex<V> vertexSource, Vertex<V> vertexTarget) {
        return vertexSource.equals(vertexTarget) && edges.stream().anyMatch(edge -> isValidEdge(vertexSource, vertexTarget, edge));
    }


    private boolean isValidEdge(Vertex<V> vertexSource, Vertex<V> vertexTarget, Edge<V> edge) {
        return edge.getFrom().equals(vertexSource) && edge.getTo().equals(vertexTarget);
    }

    private List<Edge<V>> choosingPath(List<Edge<V>> result, Vertex<V> vertexSource, Vertex<V> vertex) {
        final Vertex<V>[] resultTarget = new Vertex[]{vertex};
        List<Edge<V>> reverseEdged = new ArrayList<>();
        reverseEdged.add(result.get(result.size() - 1));

        while (!vertexSource.equals(resultTarget[0])) {
            result
                    .stream()
                    .filter(e -> e.getTo().equals(resultTarget[0])).findFirst()
                    .ifPresent(edge -> {
                        reverseEdged.add(edge);
                        resultTarget[0] = edge.getFrom();
                    });
        }

        return result.stream().filter(reverseEdged::contains).collect(Collectors.toList());
    }
}
