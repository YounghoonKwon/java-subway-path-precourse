package subway.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.CustomWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.TransitRouteException;

import java.util.List;

public class GraphByTime {
    private static final String ERROR_STATIONS_NOT_CONNECTED = "출발역과 도착역이 연결되어 있지 않습니다";

    private static final WeightedMultigraph<Station, CustomWeightedEdge> graph =
            new WeightedMultigraph(CustomWeightedEdge.class);
    private static final DijkstraShortestPath shortestTimeGraph = new DijkstraShortestPath(graph);

    public static WeightedMultigraph getGraph() {
        return graph;
    }

    public static void addVertex(Station station) {
        graph.addVertex(station);
    }

    public static void setEdgeWeight(Station from, Station to, int time) {
        graph.setEdgeWeight(graph.addEdge(from, to), time);
    }

    public static List<Station> getShortestPathByTime(Station from, Station to) {
        List<Station> shortestPath = shortestTimeGraph.getPath(from, to).getVertexList();
        if(shortestPath.isEmpty()){
            throw new TransitRouteException(ERROR_STATIONS_NOT_CONNECTED);
        }
        return shortestPath;
    }

    public static List<CustomWeightedEdge> getShortestPathEdgeList(Station from, Station to) {
        return shortestTimeGraph
                .getPath(from, to)
                .getEdgeList();
    }

    public static int getDistanceOfShortestPathByTime(Station from, Station to) {
        List<CustomWeightedEdge> shortestTimeEdgeList = getShortestPathEdgeList(from, to);
        WeightedMultigraph<Station, CustomWeightedEdge> distanceGraph = GraphByDistance.getGraph();
        int totalDistance = 0;
        for(CustomWeightedEdge edge : shortestTimeEdgeList){
            Station source = (Station) edge.getSource();
            Station target = (Station) edge.getTarget();
            totalDistance += distanceGraph.getEdge(source, target).getWeight();
        }
        return totalDistance;
    }


    public static int getTimeOfShortestPathByTime(Station from, Station to) {
        return (int) shortestTimeGraph.getPathWeight(from, to);
    }
}
