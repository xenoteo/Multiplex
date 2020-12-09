package agh.alleviation.controller;

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

    public T getEditedItem() { return this.editedItem; }

}
