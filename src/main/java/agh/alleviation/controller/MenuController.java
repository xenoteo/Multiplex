package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.util.UserType;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import agh.alleviation.model.user.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

@Component
@FxmlView("/views/Menu.fxml")
public class MenuController extends GenericController implements PropertyChangeListener {

    private User activeUser;

    @FXML
    private ToggleButton users;

    @FXML
    private ToggleButton halls;

    @FXML
    private ToggleButton movies;

    @FXML
    private ToggleButton seances;

    private final HashMap<ToggleButton, Screen> screenHashMap = new HashMap<>();

    @FXML
    public void initialize() {
        screenHashMap.put(users, Screen.USER_LIST);
        screenHashMap.put(halls, Screen.HALL_LIST);
        screenHashMap.put(movies, Screen.MOVIE_LIST);
        screenHashMap.put(seances, Screen.SEANCE_LIST);
    }


    @FXML
    public void handleActiveButtonChanged(ActionEvent event){
        ToggleButton button = (ToggleButton) event.getSource();
        Screen newScreen = screenHashMap.get(button);
        viewControllerManager.switchView(newScreen);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        activeUser = (User) evt.getNewValue();
        int level = 0;
        if(activeUser != null) level = activeUser.getUserType().getPrivilegeLevel();
        int finalLevel = level;
        screenHashMap.forEach((button, screen) ->
                button.setVisible(screen.getPrivilegeLevel() <= finalLevel));
    }


    /**
     * Constants with possible login button's texts.
     */
    public static class LoginButtonText {
        /**
         * Text displayed on logout button.
         */
        public static final String LOGOUT = "Logout";
    }

    @FXML
    private Button logoutButton;

    /**
     * Proceeds logout operation.
     * @param event the event
     */
    public void logout(ActionEvent event) {
        viewControllerManager.logout();
    }


}
