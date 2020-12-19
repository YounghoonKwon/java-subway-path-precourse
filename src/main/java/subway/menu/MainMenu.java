package subway.menu;

import subway.controller.MainController;
import subway.exception.SubwayException;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

public enum MainMenu {
    VIEW_ROUTE("경로 조회", "1", MainController::viewRoute),
    EXIT("종료", "Q", MainController::exit);

    MainMenu(String title, String command, Runnable action) {
        this.title = title;
        this.command = command;
        this.action = action;
    }

    private final String title;
    private final String command;
    private final Runnable action;

    public static MainMenu findByCommand(String command) {
        return Arrays.stream(MainMenu.values())
                .filter(mainMenu -> mainMenu.command.equalsIgnoreCase(command))
                .findAny()
                .orElseThrow(() -> {
                    throw new SubwayException("메뉴 잘못 입력");
                });
    }

    public static List<String> getTitles() {
        return Arrays.stream(MainMenu.values())
                .map(mainMenu -> mainMenu.title)
                .collect(toList());
    }

    public static List<String> getCommands() {
        return Arrays.stream(MainMenu.values())
                .map(mainMenu -> mainMenu.command)
                .collect(toList());
    }

    public void run() {
        this.action.run();
    }
}
