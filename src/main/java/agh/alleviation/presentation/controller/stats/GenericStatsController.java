package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.presentation.controller.GenericController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Map;

/**
 * Abstraction for StatsControllers - responsible for displaying statistics.
 * @param <Item> specific EntityObject displayed in the view's table
 * @author Ksenia Fiodarava
 */
public abstract class GenericStatsController<Item extends EntityObject> extends GenericController {
    /**
     * The Item table.
     */
    @FXML
    protected TableView<EntityObject> itemTable;

    @FXML
    public Button back;

    /**
     * Stage on which modal is placed.
     */
    protected Stage dialogStage;

    /**
     * Sets dialog stage.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Handles back action
     * @param event the event
     */
    @FXML
    public void handleBackAction(ActionEvent event){
        dialogStage.close();
    }

    /**
     * Generates a map of top 10 items.
     * @return a map of top 10 items
     */
    protected abstract Map<Item, Integer> top10stats();
}
