package subway.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.CustomWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.TransitRouteException;

import java.util.List;

public class TimeGraph {
    private static final String ERROR_STATIONS_NOT_CONNECTED = "출발역과 도착역이 연결되어 있지 않습니다";

    private static final WeightedMultigraph<Station, CustomWeightedEdge> timeGraph =
            new WeightedMultigraph(CustomWeightedEdge.class);
    private static final WeightedMultigraph<Station, CustomWeightedEdge> distanceGraph =
            DistanceGraph.getGraph();
    private static final DijkstraShortestPath shortestTimeGraph = new DijkstraShortestPath(timeGraph);

    public static WeightedMultigraph getGraph() {
        return timeGraph;
    }

    public static void addVertex(Station station) {
        timeGraph.addVertex(station);
    }

    public static void setEdgeWeight(Station from, Station to, int time) {
        timeGraph.setEdgeWeight(timeGraph.addEdge(from, to), time);
    }

    public static List<Station> getVertexListShortestTime(Station from, Station to) {
        List<Station> shortestPath = shortestTimeGraph.getPath(from, to).getVertexList();
        if(shortestPath.isEmpty()){
            throw new TransitRouteException(ERROR_STATIONS_NOT_CONNECTED);
        }
        return shortestPath;
    }

    public static List<CustomWeightedEdge> getEdgeListShortestTime(Station from, Station to) {
        return shortestTimeGraph
                .getPath(from, to)
                .getEdgeList();
    }

    public static int getTotalDistanceShortestTime(Station from, Station to) {
        List<CustomWeightedEdge> shortestTimeEdgeList = getEdgeListShortestTime(from, to);
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
