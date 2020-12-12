package agh.alleviation.presentation.controller;

import agh.alleviation.presentation.context.manager.ServiceManager;
import agh.alleviation.presentation.ViewControllerManager;
import org.springframework.beans.factory.annotation.Autowired;

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
     * The Service manager.
     */
    protected ServiceManager serviceManager;

    /**
     * Sets app controller.
     *
     * @param viewControllerManager the view controller manager
     */
    public void setAppController(ViewControllerManager viewControllerManager) {
        this.viewControllerManager = viewControllerManager;
    }

    /**
     * Sets observable composite.
     *
     * @param serviceManager the service manager
     */
    @Autowired
    public void setObservableComposite(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

}
