package agh.alleviation.controller.list;

import agh.alleviation.controller.GenericController;
import agh.alleviation.controller.ObservableComposite;
import agh.alleviation.model.EntityObject;
import agh.alleviation.service.EntityObjectService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

public abstract class GenericListController<Item extends EntityObject, Service> extends GenericController {
    /**
     * ObservableList of users used for TableView setup
     */
//    protected ObservableList<Item> itemObservableList;

    protected Service service;


    @FXML
    protected TableView<EntityObject> itemTable;

    protected void initialize() {
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        itemTable.setItems(itemObservableList);
//        resetContents();
    }

//    protected abstract void resetContents();

    @FXML
    protected abstract void handleAddAction(ActionEvent event);

    @FXML
    protected abstract void handleEditAction(ActionEvent event);

    @FXML
    protected void handleDeleteAction(ActionEvent event){
        EntityObject item = itemTable.getSelectionModel().getSelectedItem();
        observableComposite.delete(item);
    }


}
