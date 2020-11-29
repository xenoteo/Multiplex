package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ScreenSwitcher;
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

    AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    BorderPane mainPane;

    @FXML
    public void switchToUserListView() {
        this.appController.switchView(Screen.USER_LIST);
    }

    @Autowired
    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
}
