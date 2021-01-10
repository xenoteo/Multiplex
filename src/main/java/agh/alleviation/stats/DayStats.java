package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of day of week statistics and update of the statistics table.
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 * @author Ksenia Fiodarava
 */
public class DayStats extends GenericStats<DayOfWeek>{
    /**
     * The day of week column.
     */
    public TableColumn<DayOfWeek, String> dayColumn;

    /**
     * The tickets bought column.
     */
    public TableColumn<DayOfWeek, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        public static final String DAY = "Day of week";
        public static final String TICKETS = "Tickets bought";
    }

    public DayStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<DayOfWeek, Integer> topStats() {
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<DayOfWeek, Integer> dayMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            DayOfWeek day = ((Ticket) ticketObject).getSeance().getDate().getDayOfWeek();
            dayMap.put(day, dayMap.getOrDefault(day, 0) + 1);
        }
        return sortMap(dayMap, 7);
    }

    @Override
    public void showStats() {
        updateTableColumns();
        Map<DayOfWeek, Integer> dayMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(dayMap.keySet()));

        dayColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            DayOfWeek day = cellData.getValue();
            return new SimpleIntegerProperty(dayMap.get(day)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        dayColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        dayColumn.setText(Columns.DAY);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(dayColumn);
        columns.add(ticketsColumn);
    }
}
