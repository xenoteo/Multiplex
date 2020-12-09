package agh.alleviation.presentation;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * Class responsible for keeping root panes for each navigable screen in application and switching them accordingly
 *
 * @author Kamil Krzempek
 */
public class ScreenSwitcher {
    private HashMap<Screen, Node> screenMap;
    private BorderPane mainPane;

    public ScreenSwitcher(BorderPane mainPane) {
        this.screenMap = new HashMap<>();
        this.mainPane = mainPane;
    }

    public void addScreen(Screen screen, Node node) {
        screenMap.put(screen, node);
    }

    public void activate(Screen screen) {
        mainPane.setCenter(screenMap.get(screen));
    }
}
