package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.util.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.util.Pair;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@FxmlView("/views/Menu.fxml")
public class MenuController extends GenericController {
    private UserType activeUserType;

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

    public void setActiveUserType(UserType userType) {
        this.activeUserType = userType;
        screenHashMap.forEach((button, screen) -> button.setVisible(screen.getPrivilegeLevel() <= activeUserType.getPrivilegeLevel()));
    }

    @FXML
    public void handleActiveButtonChanged(ActionEvent event){
        ToggleButton button = (ToggleButton) event.getSource();
        Screen newScreen = screenHashMap.get(button);
        viewControllerManager.switchView(newScreen);
    }



    /**
     * Constants with possible login button's texts.
     */
    public static class LoginButtonText {
        /**
         * Text displayed on login button.
         */
        public static final String LOGIN = "Login";

        /**
         * Text displayed on logout button.
         */
        public static final String LOGOUT = "Logout";
    }

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    /**
     * Handles login button and depending on its state (login/logout) proceeds login/logout operation.
     * @param event the event
     */
    @FXML
    public void handleLoginButton(ActionEvent event){
        String loginButtonText = loginButton.getText();
        if (loginButtonText.equals(LoginButtonText.LOGIN)){
            login(event);
        }
        else if (loginButtonText.equals(LoginButtonText.LOGOUT)) {
            logout(event);
        }
    }

    /**
     * Proceeds login operation.
     * @param event the event
     */
    public void login(ActionEvent event) {
        if (viewControllerManager.showLoginDialog()){
            loginButton.setText(LoginButtonText.LOGOUT);
            registerButton.setVisible(false);
        }
    }

    /**
     * Proceeds logout operation.
     * @param event the event
     */
    public void logout(ActionEvent event) {
        viewControllerManager.logout();
        loginButton.setText(LoginButtonText.LOGIN);
        registerButton.setVisible(true);
    }

    /**
     * Handles register button and proceeds registration.
     * @param event the event.
     */
    @FXML
    public void register(ActionEvent event) {
        this.viewControllerManager.showRegistrationDialog();
    }
}
