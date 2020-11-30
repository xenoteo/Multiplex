package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/Main.fxml")
public class MainController {
    FxWeaver fxWeaver;

    ViewControllerSetup viewControllerSetup;

    public void setAppController(ViewControllerSetup viewControllerSetup) {
        this.viewControllerSetup = viewControllerSetup;
    }

    @FXML
    BorderPane mainPane;

    @FXML
    public void switchToUserListView() {
        this.viewControllerSetup.switchView(Screen.USER_LIST);
    }

    @FXML
    public void switchToHallListView() {
        this.viewControllerSetup.switchView(Screen.HALL_LIST);
    }

    @Autowired
    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
}
