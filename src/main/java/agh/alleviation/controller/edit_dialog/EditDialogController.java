package agh.alleviation.controller.edit_dialog;

import agh.alleviation.controller.GenericController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class EditDialogController<T> extends GenericController {
    /**
     * Stage on which modal is placed
     */
    protected Stage dialogStage;

    protected T editedItem;

    @FXML
    protected void initialize() {
        this.editedItem = null;
    }

    /**
     * Sets dialog stage.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public T getEditedItem() { return this.editedItem; };

    public void setEditedItem(T item) { this.editedItem = item; }
}
