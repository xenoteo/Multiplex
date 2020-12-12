package agh.alleviation.presentation.controller;

import agh.alleviation.presentation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import agh.alleviation.model.user.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

/**
 * Controller responsible for management of the navigation bar. Displayed content depends on the logged in user's privileges - achieved through observing ActiveUser's field via Spring PropertyChangeListener interface.
 * @author Kamil Krzempek
 * @author Anna Nosek
 */
@Component
@FxmlView("/views/Menu.fxml")
public class MenuController extends GenericController implements PropertyChangeListener {

    @FXML
    private ToggleButton users;

    @FXML
    private ToggleButton halls;

    @FXML
    private ToggleButton movies;

    @FXML
    private ToggleButton seances;

    private final HashMap<ToggleButton, Screen> screenHashMap = new HashMap<>();

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        screenHashMap.put(users, Screen.USER_LIST);
        screenHashMap.put(halls, Screen.HALL_LIST);
        screenHashMap.put(movies, Screen.MOVIE_LIST);
        screenHashMap.put(seances, Screen.SEANCE_LIST);
    }


    /**
     * Handle active button changed.
     *
     * @param event the event
     */
    @FXML
    public void handleActiveButtonChanged(ActionEvent event){
        ToggleButton button = (ToggleButton) event.getSource();
        Screen newScreen = screenHashMap.get(button);
        viewControllerManager.switchView(newScreen);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        User activeUser = (User) evt.getNewValue();
        int level = 0;
        if(activeUser != null) level = activeUser.getUserType().getPrivilegeLevel();
        int finalLevel = level;
        screenHashMap.forEach((button, screen) ->
                button.setVisible(screen.getPrivilegeLevel() <= finalLevel));
    }

    /**
     * Proceeds logout operation.
     *
     * @param event the event
     */
    public void logout(ActionEvent event) {
        viewControllerManager.logout();
    }


}
