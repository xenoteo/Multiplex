package agh.alleviation.presentation;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.util.HashMap;

/**
 * Class responsible for keeping root panes for each navigable screen in application and switching them accordingly
 *
 * @author Kamil Krzempek
 */
public class ScreenSwitcher {
    /**
     * The screen map.
     */
    private HashMap<Screen, Node> screenMap;
    /**
     * The main pane.
     */
    private BorderPane mainPane;

    /**
     * Instantiates a new Screen switcher.
     *
     * @param mainPane the main pane
     */
    public ScreenSwitcher(BorderPane mainPane) {
        this.screenMap = new HashMap<>();
        this.mainPane = mainPane;
    }

    /**
     * Adds a screen.
     *
     * @param screen the screen
     * @param node   the node
     */
    public void addScreen(Screen screen, Node node) {
        screenMap.put(screen, node);
    }

    /**
     * Activates a screen.
     *
     * @param screen the screen
     */
    public void activate(Screen screen) {
        mainPane.setCenter(screenMap.get(screen));
    }
}
