package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ViewControllerManager;
import javafx.fxml.FXML;

/**
 * Base class for non-modal controllers
 *
 * @author Kamil Krzempek
 */
public class GenericController {
    /**
     * ViewControllerManager instance used for navigation and opening modal windows
     */
    ViewControllerManager viewControllerManager;

    /**
     * Sets app controller.
     *
     * @param viewControllerManager the view controller manager
     */
    public void setAppController(ViewControllerManager viewControllerManager) {
        this.viewControllerManager = viewControllerManager;
    }

    /**
     * Navigation stub, will be replaced by more scalable solution in the future
     */
    @FXML
    public void switchToUserListView() {
        this.viewControllerManager.switchView(Screen.USER_LIST);
    }

    /**
     * Navigation stub, will be replaced by more scalable solution in the future
     */
    @FXML
    public void switchToHallListView() {
        this.viewControllerManager.switchView(Screen.HALL_LIST);
    }
}
