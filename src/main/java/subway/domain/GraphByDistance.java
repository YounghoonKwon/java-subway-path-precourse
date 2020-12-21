package subway.domain;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.CustomWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.TransitRouteException;

import java.util.List;

public class GraphByDistance {
    private static final String ERROR_STATIONS_NOT_CONNECTED = "출발역과 도착역이 연결되어 있지 않습니다";

    private static final WeightedMultigraph<Station, CustomWeightedEdge> graph =
            new WeightedMultigraph(CustomWeightedEdge.class);
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

    public static List<CustomWeightedEdge> getShortestPathEdgeList(Station from, Station to) {
        return shortestDistanceGraph
                .getPath(from, to)
                .getEdgeList();
    }

    public static int getTimeOfShortestPathByDistance(Station from, Station to) {
        List<CustomWeightedEdge> shortestDistanceEdgeList = getShortestPathEdgeList(from, to);
        WeightedMultigraph<Station, CustomWeightedEdge> timeGraph = GraphByTime.getGraph();
        int totalTime = 0;
        for(CustomWeightedEdge edge : shortestDistanceEdgeList){
            Station source = (Station) edge.getSource();
            Station target = (Station) edge.getTarget();
            totalTime += timeGraph.getEdge(source, target).getWeight();
        }
        return totalTime;
    }

    public static int getDistanceOfShortestPathByDistance(Station from, Station to) {
        return (int) shortestDistanceGraph.getPathWeight(from, to);
    }
}
