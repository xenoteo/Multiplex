package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller responsible for displaying month statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/MonthStats.fxml")
public class MonthStatsController extends GenericStatsController<Month>{

    /**
     * The time column.
     */
    @FXML
    public TableColumn<Month, String> monthColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<Month, Integer> ticketsColumn;

    @FXML
    public void initialize() {
        Map<Month, Integer> timeMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(timeMap.keySet()));

        monthColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            Month month = cellData.getValue();
            return new SimpleIntegerProperty(timeMap.get(month)).asObject();
        });
    }

    @Override
    protected Map<Month, Integer> topStats() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
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
}
