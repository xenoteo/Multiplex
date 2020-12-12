package agh.alleviation.controller.edit_dialog;

import agh.alleviation.controller.GenericController;
import agh.alleviation.controller.ValidatingController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.util.stream.Collectors;

public abstract class EditDialogController<T> extends ValidatingController {
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
