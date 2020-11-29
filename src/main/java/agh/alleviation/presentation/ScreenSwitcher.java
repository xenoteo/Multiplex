package agh.alleviation.presentation;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenSwitcher {
    private HashMap<Screen, Pane> screenMap = new HashMap<>();
    private Scene main;

    public void setMainScene(Scene main) {
        this.main = main;
    }

    public void addScreen(Screen screen, Pane pane) {
        screenMap.put(screen, pane);
    }

    public void activate(Screen screen) {
        main.setRoot(screenMap.get(screen));
    }
}
