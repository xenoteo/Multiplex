package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ViewControllerManager;
import javafx.fxml.FXML;

/**
 * Base class for non-modal controllers
 *
 * @author Kamil Krzempek
 */
public abstract class GenericController {
    /**
     * ViewControllerManager instance used for navigation and opening modal windows
     */

    protected ViewControllerManager viewControllerManager;

    /**
     * Sets app controller.
     *
     * @param viewControllerManager the view controller manager
     */
    public void setAppController(ViewControllerManager viewControllerManager) {
        this.viewControllerManager = viewControllerManager;
    }

}
