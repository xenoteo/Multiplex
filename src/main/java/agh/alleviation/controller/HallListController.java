package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kamil Krzempek
 * Controller responsible for displaying list of halls and control panel, which allows to manipulate them (only adding at the moment)
 */
@Component
@FxmlView("/views/HallList.fxml")
public class HallListController extends GenericController {
    HallService hallService;

    /**
     * ObservableList of halls used for TableView setup
     */
    ObservableList<Hall> hallObservableList;

    @FXML
    private TableView<Hall> hallTable;

    @FXML
    private TableColumn<Hall, Integer> numberColumn;

    @FXML
    private TableColumn<Hall, Integer> capacityColumn;

    @FXML
    private void initialize() {
        hallTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        numberColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberProperty().asObject());
        capacityColumn.setCellValueFactory(dataValue -> dataValue.getValue().capacityProperty().asObject());

        this.hallObservableList = FXCollections.observableArrayList(hallService.getAllHalls());

        hallTable.setItems(hallObservableList);
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        Hall hall = this.viewControllerManager.showAddHallDialog();
        if(hall != null) {
            this.hallObservableList.add(hall);
        }
    }

    @Autowired
    public HallListController(HallService hallService) {
        this.hallService = hallService;
    }
}
