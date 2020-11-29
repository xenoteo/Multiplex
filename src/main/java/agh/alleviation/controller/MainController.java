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

    ScreenSwitcher screenSwitcher;

    public void setScreenSwitcher(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;
    }

    @FXML
    BorderPane mainPane;

    @FXML
    public void switchToUserListView() {
        this.screenSwitcher.activate(Screen.USER_LIST);
    }

    @Autowired
    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
}
