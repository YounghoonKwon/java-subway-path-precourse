package subway.controller;

import subway.domain.Station;
import subway.domain.StationRepository;
import subway.domain.TimeGraph;
import subway.exception.TransitRouteException;
import subway.view.InputView;
import subway.view.OutputView;

import java.util.Arrays;
import java.util.List;

public class ShortestTimeRouteController {
    private static final String DEPARTURE_STATION = "출발역";
    private static final String ARRIVAL_STATION = "도착역";
    private static final String SAME_STATION_ERROR_MESSAGE = "%s과 %s이 동일합니다.";

    public static void showShortestTimeRoute() {
        showShortestTimeRouteHelper(getEndStations());
    }

    public static List<Station> getEndStations() {
        OutputView.showPrompt(DEPARTURE_STATION);
        Station departStation = StationRepository.findStationByName(InputView.getInput());
        OutputView.showPrompt(ARRIVAL_STATION);
        Station arrivalStation = StationRepository.findStationByName(InputView.getInput());

        if (departStation.equals(arrivalStation)) {
            throw new TransitRouteException(String.format(SAME_STATION_ERROR_MESSAGE, DEPARTURE_STATION,
                    ARRIVAL_STATION));
        }
        return Arrays.asList(departStation, arrivalStation);
    }

    public static void showShortestTimeRouteHelper(List<Station> endStations) {
        Station departStation = endStations.get(0);
        Station arrivalStation = endStations.get(1);
        List<Station> shortestPath = TimeGraph.getVertexListShortestTime(departStation, arrivalStation);
        int shortestPathTime = TimeGraph.getTimeOfShortestPathByTime(departStation, arrivalStation);
        int distanceOfShortestPathTime = TimeGraph.getTotalDistanceShortestTime(departStation, arrivalStation);
        OutputView.lookUpResult(distanceOfShortestPathTime, shortestPathTime, shortestPath);
    }
}
