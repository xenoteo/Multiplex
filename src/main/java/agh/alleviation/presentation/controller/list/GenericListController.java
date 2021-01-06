package agh.alleviation.presentation.controller.list;

import agh.alleviation.presentation.controller.GenericController;
import agh.alleviation.model.EntityObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

/**
 * Abstraction for ListControllers - responsible for displaying lists of specific EntityObjects.
 *
 * @param <Item> specific EntityObject displayed in the view's table
 * @author Kamil Krzempek
 */
public abstract class GenericListController<Item extends EntityObject> extends GenericController {

    /**
     * The Item table.
     */
    @FXML
    protected TableView<EntityObject> itemTable;

    /**
     * Initialize.
     */
    protected void initialize() {
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Handle add action.
     *
     * @param event the event
     */
    @FXML
    protected abstract void handleAddAction(ActionEvent event);

    /**
     * Handle edit action.
     *
     * @param event the event
     */
    @FXML
    protected abstract void handleEditAction(ActionEvent event);

    /**
     * Handle delete action.
     *
     * @param event the event
     */
    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        EntityObject item = itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(item);
    }

}
