package agh.alleviation.controller;

import agh.alleviation.presentation.Screen;
import javafx.fxml.FXML;

public class GenericListController {
    AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void switchToUserListView() {
        this.appController.switchView(Screen.USER_LIST);
    }

    @FXML
    public void switchToHallListView() {
        this.appController.switchView(Screen.HALL_LIST);
    }
}
