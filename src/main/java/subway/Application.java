package subway;

import subway.controller.TransitRouteController;
import subway.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        init();
        TransitRouteController.run();
    }

    public static void init() {
        List<String> stations = Arrays.asList("교대역", "강남역", "역삼역", "남부터미널역", "양재역", "양재시민의숲역", "매봉역");
        stations.stream()
                .forEach(station -> StationRepository.addStation(new Station(station)));
        initStationWithLine("2호선", Arrays.asList("교대역", "강남역", "역삼역"));
        initStationWithLine("3호선", Arrays.asList("교대역", "남부터미널역", "양재역", "매봉역"));
        initStationWithLine("신분당선", Arrays.asList("강남역", "양재역", "양재시민의숲역"));
        initGraph();
    }

    public static void initStationWithLine(String lineName, List<String> stationNames) {
        List<Station> stations = stationNames.stream()
                .map(stationName -> StationRepository.findStationByName(stationName))
                .collect(Collectors.toList());
        Line line = new Line(lineName);
        LineRepository.addLine(line);
    }

    public static void initGraph() {
        StationRepository.getStations()
                .stream()
                .forEach(station -> TimeGraph.addVertex(station));
        StationRepository.getStations()
                .stream()
                .forEach(station -> DistanceGraph.addVertex(station));
        initDistanceGraph();
        initTimeGraph();
    }

    public static void initDistanceGraph() {
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("교대역"), StationRepository.findStationByName("강남역"), 2);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("강남역"), StationRepository.findStationByName("역삼역"), 2);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("교대역"), StationRepository.findStationByName("남부터미널역"), 3);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("남부터미널역"), StationRepository.findStationByName("양재역"), 6);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("양재역"), StationRepository.findStationByName("매봉역"), 1);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("강남역"), StationRepository.findStationByName("양재역"), 2);
        DistanceGraph.setEdgeWeight(StationRepository.findStationByName("양재역"), StationRepository.findStationByName("양재시민의숲역"), 10);
    }

    public static void initTimeGraph() {
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("교대역"), StationRepository.findStationByName("강남역"), 3);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("강남역"), StationRepository.findStationByName("역삼역"), 3);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("교대역"), StationRepository.findStationByName("남부터미널역"), 2);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("남부터미널역"), StationRepository.findStationByName("양재역"), 5);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("양재역"), StationRepository.findStationByName("매봉역"), 1);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("강남역"), StationRepository.findStationByName("양재역"), 8);
        TimeGraph.setEdgeWeight(StationRepository.findStationByName("양재역"), StationRepository.findStationByName("양재시민의숲역"), 3);
    }
}
