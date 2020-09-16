package name.nicholasgribanov.test.simplegraph;

import name.nicholasgribanov.simplegraph.*;
import name.nicholasgribanov.simplegraph.exceptions.PathNotFoundException;
import name.nicholasgribanov.simplegraph.exceptions.VertexNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectedGraphTest {

    Graph<String> stringGraph;
    Graph<Integer> integerGraph;

    @Before
    public void setUp() throws Exception {
        stringGraph = new DirectedGraph<>();
        integerGraph = new DirectedGraph<>();

        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");
        Vertex<String> vertex3 = new Vertex<>("C");
        Vertex<String> vertex4 = new Vertex<>("D");
        Vertex<String> vertex5 = new Vertex<>("E");
        stringGraph.addVertex(vertex1);
        stringGraph.addVertex(vertex2);
        stringGraph.addVertex(vertex3);
        stringGraph.addVertex(vertex4);
        stringGraph.addVertex(vertex5);
        stringGraph.addEdge(vertex1, vertex1);
        stringGraph.addEdge(vertex1, vertex2);
        stringGraph.addEdge(vertex1, vertex3);
        stringGraph.addEdge(vertex3, vertex5);
        stringGraph.addEdge(vertex5, vertex4);

        Vertex<Integer> vertex11 = new Vertex<>(1);
        Vertex<Integer> vertex22 = new Vertex<>(2);
        Vertex<Integer> vertex33 = new Vertex<>(3);
        Vertex<Integer> vertex44 = new Vertex<>(4);
        Vertex<Integer> vertex55 = new Vertex<>(5);

        integerGraph.addVertex(vertex11);
        integerGraph.addVertex(vertex22);
        integerGraph.addVertex(vertex33);
        integerGraph.addVertex(vertex44);
        integerGraph.addVertex(vertex55);
        integerGraph.addEdge(vertex11, vertex11);
        integerGraph.addEdge(vertex11, vertex22);
        integerGraph.addEdge(vertex11, vertex33);
        integerGraph.addEdge(vertex33, vertex55);
        integerGraph.addEdge(vertex55, vertex44);
    }

    @Test
    public void getPathString() throws PathNotFoundException, VertexNotFoundException {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("D");

        List<Edge<String>> path = stringGraph.getPath(vertex1, vertex2);

        List<Edge<String>> expected = List.of(
                new Edge<>(new Vertex<>("A"), new Vertex<>("C")),
                new Edge<>(new Vertex<>("C"), new Vertex<>("E")),
                new Edge<>(new Vertex<>("E"), new Vertex<>("D"))
        );

        assertEquals(expected, path);
    }


    @Test
    public void getPathInteger() throws PathNotFoundException, VertexNotFoundException {
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(5);

        List<Edge<Integer>> path = integerGraph.getPath(vertex1, vertex2);

        List<Edge<Integer>> expected = List.of(
                new Edge<>(new Vertex<>(1), new Vertex<>(3)),
                new Edge<>(new Vertex<>(3), new Vertex<>(5))
        );

        assertEquals(expected, path);
    }


    @Test(expected = PathNotFoundException.class)
    public void getPath() throws PathNotFoundException, VertexNotFoundException {
        Vertex<String> vertex1 = new Vertex<>("D");
        Vertex<String> vertex2 = new Vertex<>("A");

        List<Edge<String>> path = stringGraph.getPath(vertex1, vertex2);

    }

    @Test(expected = VertexNotFoundException.class)
    public void getPathStringVertexNotFound() throws PathNotFoundException, VertexNotFoundException {
        Vertex<String> vertex1 = new Vertex<>("W");
        Vertex<String> vertex2 = new Vertex<>("A");

        List<Edge<String>> path = stringGraph.getPath(vertex1, vertex2);
    }

    @Test
    public void getPathStringLoop() throws PathNotFoundException, VertexNotFoundException {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("A");
        List<Edge<String>> path = stringGraph.getPath(vertex1, vertex2);

        Edge<String> edge = new Edge<>(vertex1, vertex2);

        assertEquals(edge, path.get(0));
    }
}
