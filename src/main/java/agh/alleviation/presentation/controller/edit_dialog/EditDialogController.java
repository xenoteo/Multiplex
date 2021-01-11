package agh.alleviation.presentation.controller.edit_dialog;

import agh.alleviation.presentation.controller.ValidatingController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Abstraction for EditDialogControllers - responsible for both adding a new element to the database as well as updating an existing one.
 *
 * @param <T> - type of edited/added EntityObject
 * @author Kamil Krzempek
 */
public abstract class EditDialogController<T> extends ValidatingController {
    /**
     * Stage on which modal is placed
     */
    protected Stage dialogStage;

    /**
     * The edited item.
     */
    protected T editedItem;

    /**
     * Initializes edited item.
     */
    @FXML
    protected void initialize() {
        this.editedItem = null;
    }

    /**
     * Sets dialog stage.
     *
     * @param dialogStage  the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Gets edited item.
     *
     * @return the edited item
     */
    public T getEditedItem() { return this.editedItem; };

    /**
     * Sets edited item.
     *
     * @param item  the item
     */
    public void setEditedItem(T item) { this.editedItem = item; }

}
