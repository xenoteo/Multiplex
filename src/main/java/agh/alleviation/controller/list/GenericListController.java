package agh.alleviation.controller.list;

import agh.alleviation.controller.GenericController;
import agh.alleviation.model.EntityObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

public abstract class GenericListController<Item extends EntityObject, Service> extends GenericController {

    protected Service service;

    @FXML
    protected TableView<EntityObject> itemTable;

    protected void initialize() {
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    protected abstract void handleAddAction(ActionEvent event);

    @FXML
    protected abstract void handleEditAction(ActionEvent event);

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        EntityObject item = itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(item);
    }

}
