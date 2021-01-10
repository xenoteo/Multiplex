package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of month statistics and update of the statistics table.
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 * @author Ksenia Fiodarava
 */
public class MonthStats extends GenericStats<Month> {
    /**
     * The time column.
     */
    public TableColumn<Month, String> monthColumn;

    /**
     * The tickets bought column.
     */
    public TableColumn<Month, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        public static final String MONTH = "Month";
        public static final String TICKETS = "Tickets bought";
    }

    public MonthStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<Month, Integer> topStats() {
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<Month, Integer> monthMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            Month month = ((Ticket) ticketObject).getSeance().getDate().getMonth();
            monthMap.put(month, monthMap.getOrDefault(month, 0) + 1);
        }
        return sortMap(monthMap, 12);
    }

    @Override
    public void showStats() {
        updateTableColumns();
        Map<Month, Integer> timeMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(timeMap.keySet()));

        monthColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            Month month = cellData.getValue();
            return new SimpleIntegerProperty(timeMap.get(month)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        monthColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        monthColumn.setText(Columns.MONTH);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(monthColumn);
        columns.add(ticketsColumn);
    }
}
