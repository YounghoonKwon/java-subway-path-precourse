package subway;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;
import subway.domain.Station;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JGraphtTest {
    @Test
    public void getDijkstraShortestPath() {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        Station v1 = new Station("v1");
        Station v2 = new Station("v2");
        Station v3 = new Station("v3");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.setEdgeWeight(graph.addEdge(v1, v2), 2);
        graph.setEdgeWeight(graph.addEdge(v2, v3), 2);
        graph.setEdgeWeight(graph.addEdge(v1, v3), 100);

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        List<Station> shortestPath = dijkstraShortestPath.getPath(v3, v1).getVertexList();

        assertThat(shortestPath.size()).isEqualTo(3);
    }
}
