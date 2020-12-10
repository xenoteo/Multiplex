package agh.alleviation.controller.list;

import agh.alleviation.controller.GenericController;
import agh.alleviation.service.EntityObjectService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

public abstract class GenericListController<Item, Service> extends GenericController {
    /**
     * ObservableList of users used for TableView setup
     */
    protected ObservableList<Item> itemObservableList;

    protected Service service;

    @FXML
    protected TableView<Item> itemTable;

    protected void initialize() {
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemObservableList = FXCollections.observableArrayList();
        itemTable.setItems(itemObservableList);
        resetContents();
    }

    protected abstract void resetContents();

    @FXML
    protected abstract void handleAddAction(ActionEvent event);

    @FXML
    protected abstract void handleEditAction(ActionEvent event);

    @FXML
    protected abstract void handleDeleteAction(ActionEvent event);
}
