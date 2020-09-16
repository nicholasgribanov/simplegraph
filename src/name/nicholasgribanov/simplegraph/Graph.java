package name.nicholasgribanov.simplegraph;


import name.nicholasgribanov.simplegraph.exceptions.PathNotFoundException;
import name.nicholasgribanov.simplegraph.exceptions.VertexNotFoundException;

import java.util.List;

public interface Graph<V> {

    boolean addVertex(Vertex<V> vertex);

    boolean addEdge(Vertex<V> source, Vertex<V> target) throws VertexNotFoundException;

    List<Edge<V>> getPath(Vertex<V> source, Vertex<V> target) throws PathNotFoundException, VertexNotFoundException;

}
