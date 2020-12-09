package agh.alleviation.controller.edit_dialog;

import javafx.stage.Stage;

public class EditDialogController<T> {
    /**
     * Stage on which modal is placed
     */
    protected Stage dialogStage;

    protected T editedItem;

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
