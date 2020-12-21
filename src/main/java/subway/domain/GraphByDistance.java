package subway.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.TransitRouteException;

import java.util.List;

public class GraphByDistance {
    private static final String ERROR_STATIONS_NOT_CONNECTED = "출발역과 도착역이 연결되어 있지 않습니다";

    private static final WeightedMultigraph<Station, DefaultWeightedEdge> graph =
            new WeightedMultigraph(DefaultWeightedEdge.class);
    private static final DijkstraShortestPath shortestDistanceGraph = new DijkstraShortestPath(graph);

    public static WeightedMultigraph getGraph() {
        return graph;
    }

    public static void addVertex(Station station) {
        graph.addVertex(station);
    }

    public static void setEdgeWeight(Station from, Station to, int distance) {
        graph.setEdgeWeight(graph.addEdge(from, to), distance);
    }

    public static List<Station> getShortestPathByDistance(Station from, Station to) {
        List<Station> shortestPath = shortestDistanceGraph.getPath(from, to).getVertexList();
        if(shortestPath.isEmpty()){
            throw new TransitRouteException(ERROR_STATIONS_NOT_CONNECTED);
        }
        return shortestPath;
    }

    public static int getTimeOfShortestPathByDistance(Station from, Station to) {
        WeightedMultigraph<Station, DefaultWeightedEdge> timeGraph = GraphByTime.getGraph();
        int totalTime = shortestDistanceGraph
                .getPath(from, to)
                .getEdgeList()
                .stream()
                .mapToInt(edge -> (int) timeGraph
                        .getEdgeWeight((DefaultWeightedEdge) edge))
                        .sum();
        return totalTime;
    }

    public static int getDistanceOfShortestPathByDistance(Station from, Station to) {
        return (int) shortestDistanceGraph.getPathWeight(from, to);
    }
}
