package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.service.OrderService;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller responsible for displaying time statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/TimeStats.fxml")
public class TimeStatsController extends GenericStatsController<LocalTime>{

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


    @FXML
    public void initialize() {
        Map<LocalTime, Integer> timeMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(timeMap.keySet()));

        timeColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue();
            return new SimpleIntegerProperty(timeMap.get(time)).asObject();
        });
    }

    @Override
    protected Map<LocalTime, Integer> topStats() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(ticketObj -> ticketObj.getIsActive() && ((Ticket) ticketObj).getSeance().getIsActive())
                .collect(Collectors.toList());
        Map<LocalTime, Integer> timeMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            LocalTime time = ((Ticket) ticketObject).getSeance().getDate().toLocalTime();
            timeMap.put(time, timeMap.getOrDefault(time, 0) + 1);
        }
        return sortMap(timeMap, 10);
    }
}
