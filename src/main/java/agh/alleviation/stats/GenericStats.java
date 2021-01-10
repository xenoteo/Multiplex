package agh.alleviation.stats;

import agh.alleviation.presentation.controller.GenericController;
import agh.alleviation.service.TicketService;
import javafx.scene.control.TableView;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstraction for stats classes - responsible for generating statistics of certain type.
 * Class responsible for generation of month statistics and update of the statistics table.
 * @see agh.alleviation.presentation.controller.StatisticsController
 * @author Ksenia Fiodarava
 */
public abstract class GenericStats<Item> extends GenericController {
    /**
     * The Item table.
     */
    protected TableView<Item> itemTable;

    /**
     * A ticket service.
     */
    protected TicketService ticketService;

    public GenericStats(TableView itemTable, TicketService ticketService) {
        this.itemTable = itemTable;
        this.ticketService = ticketService;
        setUpColumns();
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

    /**
     * Shows stats.
     */
    public abstract void showStats();

    /**
     * Sets up columns.
     */
    protected abstract void setUpColumns();

    /**
     * Updates table columns.
     */
    protected abstract void updateTableColumns();
}
