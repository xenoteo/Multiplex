package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/views/Menu.fxml")
public class MenuController extends GenericController {

    @FXML
    public void handleActiveButtonChanged(ActionEvent event){
        ToggleButton button = (ToggleButton) event.getSource();
        String buttonId = button.getId();
        Screen newScreen = switch (buttonId) {
            case "users" -> Screen.USER_LIST;
            case "halls" -> Screen.HALL_LIST;
            case "movies" -> Screen.MOVIE_LIST;
            default -> null;
        };
        viewControllerManager.switchView(newScreen);
    }

}
