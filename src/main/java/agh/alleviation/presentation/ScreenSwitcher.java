package agh.alleviation.presentation;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * Class responsible for keeping root panes for each navigable screen in application and switching them accordingly
 *
 * @author Kamil Krzempek
 */
public class ScreenSwitcher {
    private HashMap<Screen, Pane> screenMap = new HashMap<>();
    private Scene main;

    /**
     * Sets main scene.
     *
     * @param main the main
     */
    public void setMainScene(Scene main) {
        this.main = main;
    }

    /**
     * Add screen.
     *
     * @param screen the screen
     * @param pane   the pane
     */
    public void addScreen(Screen screen, Pane pane) {
        screenMap.put(screen, pane);
    }

    /**
     * Activate.
     *
     * @param screen the screen
     */
    public void activate(Screen screen) {
        main.setRoot(screenMap.get(screen));
    }
}
