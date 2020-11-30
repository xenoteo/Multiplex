package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import javafx.fxml.FXML;

public class GenericListController {
    ViewControllerSetup viewControllerSetup;

    public void setAppController(ViewControllerSetup viewControllerSetup) {
        this.viewControllerSetup = viewControllerSetup;
    }

    @FXML
    public void switchToUserListView() {
        this.viewControllerSetup.switchView(Screen.USER_LIST);
    }

    @FXML
    public void switchToHallListView() {
        this.viewControllerSetup.switchView(Screen.HALL_LIST);
    }
}
