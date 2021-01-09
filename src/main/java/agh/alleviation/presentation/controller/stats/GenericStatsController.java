package agh.alleviation.presentation.controller.stats;

import agh.alleviation.presentation.controller.GenericController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstraction for StatsControllers - responsible for displaying statistics.
 * @param <Item> specific object displayed in the view's table
 * @author Ksenia Fiodarava
 */
public abstract class GenericStatsController<Item> extends GenericController {
    /**
     * The Item table.
     */
    @FXML
    protected TableView<Item> itemTable;

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
    protected abstract Map<Item, Integer> topStats();

    /**
     * Sorts a map by values and limits it with provided number of items.
     * @param map a map to sort
     * @param limit number of items to be in a sorted map
     * @return sorted amd limited map
     */
    protected Map<Item, Integer> sortMap(Map<Item, Integer> map, int limit){
        Map<Item, Integer> mapSorted = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .forEachOrdered(x -> mapSorted.put(x.getKey(), x.getValue()));
        return mapSorted;
    }
}
