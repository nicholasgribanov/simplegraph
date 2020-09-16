package name.nicholasgribanov.simplegraph;

import name.nicholasgribanov.simplegraph.exceptions.VertexNotFoundException;

public class UndirectedGraph<V> extends CommonGraph<V> {

    @Override
    public boolean addEdge(Vertex<V> source, Vertex<V> target) throws VertexNotFoundException {
        if (vertices.contains(source) && vertices.contains(target)) {
            Edge<V> edge = new Edge<V>(source, target);
            Edge<V> edge2 = new Edge<V>(target, source);
            source.getEdges().add(edge);
            source.getEdges().add(edge2);
            target.getEdges().add(edge);
            target.getEdges().add(edge2);

            return true;
        }

        throw new VertexNotFoundException("Can't add edge between vertices. Not found " + source + ", " + target);
    }

}
