package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.Screen;
import agh.alleviation.service.HallService;
import agh.alleviation.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/HallList.fxml")
public class HallListController {
    HallService hallService;

    ObservableList<Hall> hallObservableList;

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

    @FXML
    private TableView<Hall> hallTable;

    @FXML
    private TableColumn<Hall, Integer> capacityColumn;

    @FXML
    private void initialize() {
        hallTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        capacityColumn.setCellValueFactory(dataValue -> dataValue.getValue().capacityProperty().asObject());

        this.hallObservableList = FXCollections.observableArrayList(hallService.getAllHalls());

        hallTable.setItems(hallObservableList);
    }


    @FXML
    private void handleAddAction(ActionEvent event) {
        Hall hall = this.appController.showAddHallDialog();
        if(hall != null) {
            this.hallObservableList.add(hall);
        }
    }

    @Autowired
    public HallListController(HallService hallService) {
        this.hallService = hallService;
    }
}
