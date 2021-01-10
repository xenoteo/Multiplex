package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of time statistics and update of the statistics table.
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 * @author Ksenia Fiodarava
 */
public class TimeStats extends GenericStats<LocalTime>{
    /**
     * The time column.
     */
    @FXML
    public TableColumn<LocalTime, String> timeColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<LocalTime, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        public static final String TIME = "Time";
        public static final String TICKETS = "Tickets bought";
    }

    public TimeStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<LocalTime, Integer> topStats() {
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<LocalTime, Integer> timeMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            LocalTime time = ((Ticket) ticketObject).getSeance().getDate().toLocalTime();
            timeMap.put(time, timeMap.getOrDefault(time, 0) + 1);
        }
        return sortMap(timeMap, 10);
    }

    @Override
    public void showStats() {
        updateTableColumns();
        Map<LocalTime, Integer> timeMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(timeMap.keySet()));

        timeColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue();
            return new SimpleIntegerProperty(timeMap.get(time)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        timeColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        timeColumn.setText(Columns.TIME);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(timeColumn);
        columns.add(ticketsColumn);
    }
}
