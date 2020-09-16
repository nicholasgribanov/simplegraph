package name.nicholasgribanov.simplegraph;

import name.nicholasgribanov.simplegraph.exceptions.VertexNotFoundException;

public class DirectedGraph<V> extends CommonGraph<V> {

    @Override
    public boolean addEdge(Vertex<V> source, Vertex<V> target) throws VertexNotFoundException {
        if (vertices.contains(source) && vertices.contains(target)) {
            Edge<V> edge = new Edge<V>(source, target);
            source.getEdges().add(edge);
            target.getEdges().add(edge);

            return true;
        }
        throw new VertexNotFoundException("Can't add edge between vertices. Not found " + source + ", " + target);
    }
}
