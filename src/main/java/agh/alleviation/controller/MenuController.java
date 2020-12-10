package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.util.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private final HashMap<ToggleButton, Screen> screenHashMap = new HashMap<>();

    @FXML
    public void initialize() {
        screenHashMap.put(users, Screen.USER_LIST);
        screenHashMap.put(halls, Screen.HALL_LIST);
        screenHashMap.put(movies, Screen.MOVIE_LIST);
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
}
