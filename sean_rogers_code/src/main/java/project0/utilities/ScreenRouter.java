package project0.utilities;

import project0.screens.Screen;

import java.util.HashSet;
import java.util.Set;

public class ScreenRouter {

    private Set<Screen> screens = new HashSet<>();

    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }

    public void navigate(String route) {
        screens.stream()
                .filter(screen -> screen.getRoute().equals(route))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No screen found with that route"))
                .render();
    }

}
