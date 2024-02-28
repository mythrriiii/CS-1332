import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Mythri Muralikannan
 * @userid mmuralikannan3
 * @GTID 903805814
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        } else if (start == null) {
            throw new IllegalArgumentException("Start vertex cannot be null.");
        }

        Set<Vertex<T>> vertexSet = graph.getVertices();

        if (!vertexSet.contains(start)) {
            throw new IllegalArgumentException("Start vertex is not in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> returnList = new LinkedList<>();

        visitedSet.add(start);
        queue.add(start);

        Vertex<T> currV;

        while (!queue.isEmpty()) {
            currV = queue.poll();
            returnList.add(currV);
            for (VertexDistance<T> w: graph.getAdjList().get(currV)) {
                if (!visitedSet.contains(w.getVertex())) {
                    visitedSet.add(w.getVertex());
                    queue.add(w.getVertex());
                }
            }
        }


        return returnList;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        } else if (start == null) {
            throw new IllegalArgumentException("Start vertex cannot be null.");
        }

        Set<Vertex<T>> vertexSet = graph.getVertices();

        if (!vertexSet.contains(start)) {
            throw new IllegalArgumentException("Start vertex is not in the graph.");
        }


        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnList = new LinkedList<>();

        dfs(start, graph, returnList, visitedSet);
        return returnList;

    }

    /** Private recursive helper method of Depth First Search.
     *
     * @param currV the current pointer node
     * @param graph the graph to search through
     * @param returnList list of vertices in visited order
     * @param vS set containing all visited vertices
     * @param <T> the generic typing of the data
     */
    private static <T> void dfs(Vertex<T> currV, Graph<T> graph, List<Vertex<T>> returnList, Set<Vertex<T>> vS) {

        vS.add(currV);
        returnList.add(currV);

        for (VertexDistance<T> w: graph.getAdjList().get(currV)) {
            if (!vS.contains(w.getVertex())) {
                dfs(w.getVertex(), graph, returnList, vS);
            }
        }

    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        } else if (start == null) {
            throw new IllegalArgumentException("Start vertex cannot be null.");
        }

        Set<Vertex<T>> vertexSet = graph.getVertices();

        if (!vertexSet.contains(start)) {
            throw new IllegalArgumentException("Start vertex is not in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();

        for (Vertex<T> v: graph.getVertices()) {
            if (v.equals(start)) {
                distanceMap.put(v, 0);
            } else {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
        }

        VertexDistance<T> currV;

        priorityQueue.add(new VertexDistance<>(start, 0));
        while (!priorityQueue.isEmpty()) {
            currV = priorityQueue.remove();
            for (VertexDistance<T> w: graph.getAdjList().get(currV.getVertex())) {
                int distance = currV.getDistance() + w.getDistance();
                if (distanceMap.get(w.getVertex()) > distance) {
                    distanceMap.put(w.getVertex(), distance);
                    priorityQueue.add(new VertexDistance<>(w.getVertex(), distance));
                }
            }

        }

        return distanceMap;

    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        } else if (start == null) {
            throw new IllegalArgumentException("Start vertex cannot be null.");
        }

        Set<Vertex<T>> vertexSet = graph.getVertices();

        if (!vertexSet.contains(start)) {
            throw new IllegalArgumentException("Start vertex is not in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Edge<T>> priorityQueue = new PriorityQueue<>();
        Set<Edge<T>> mstSet = new HashSet<>();

        for (VertexDistance<T> vertexDistance: graph.getAdjList().get(start)) {
            priorityQueue.add(new Edge<T>(start, vertexDistance.getVertex(), vertexDistance.getDistance()));
        }

        visitedSet.add(start);

        while (!priorityQueue.isEmpty() && visitedSet.size() != vertexSet.size()) {
            Edge<T> currEdge = priorityQueue.remove();
            if (!visitedSet.contains(currEdge.getV())) {
                visitedSet.add(currEdge.getV());
                mstSet.add(currEdge);
                mstSet.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                for (VertexDistance<T> vertexDistance: graph.getAdjList().get(currEdge.getV())) {
                    priorityQueue.add(new Edge<T>(currEdge.getV(), vertexDistance.getVertex(),
                            vertexDistance.getDistance()));
                }
            }
        }

        if (mstSet.size() < vertexSet.size() - 1) {
            return null;
        }

        return mstSet;

    }
}