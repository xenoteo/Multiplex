package agh.alleviation.presentation;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * Class responsible for keeping root panes for each navigable screen in application and switching them accordingly
 *
 * @author Kamil Krzempek
 */
public class ScreenSwitcher {
    private HashMap<Screen, Pane> screenMap;
    private BorderPane mainPane;

    public ScreenSwitcher(BorderPane mainPane) {
        this.screenMap = new HashMap<>();
        this.mainPane = mainPane;
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
        mainPane.setCenter(screenMap.get(screen));
    }
}
