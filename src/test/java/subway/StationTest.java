package subway;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.exception.TransitRouteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StationTest {
    static Station station;

    @BeforeAll
    static void init(){
        Application.init();
    }

    @DisplayName("이름으로 역을 찾는다.")
    @Test
    void When_기본상태역찾기_Expect_찾기완료() {
        String testStationName = "군자역";
        Station testStation = new Station(testStationName);
        StationRepository.addStation(testStation);
        assertThat(StationRepository.findStationByName(testStationName)).isEqualTo(testStation);
    }

    @DisplayName("예외 : 이름으로 역을 찾는다.")
    @Test
    void When_존재하지않는역찾기_Expect_예외발생() {
        String testName = "강변역";
        assertThatThrownBy(() -> StationRepository.findStationByName(testName))
                .isInstanceOf(TransitRouteException.class)
                .hasMessage("[ERROR] 해당 역이 존재하지 않습니다");
    }
}
