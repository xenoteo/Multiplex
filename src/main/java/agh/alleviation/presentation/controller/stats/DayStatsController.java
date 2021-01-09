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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller responsible for displaying day of week statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/DayStats.fxml")
public class DayStatsController extends GenericStatsController<DayOfWeek>{

    /**
     * The day of week column.
     */
    @FXML
    public TableColumn<DayOfWeek, String> dayColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<DayOfWeek, Integer> ticketsColumn;

    @FXML
    public void initialize() {
        Map<DayOfWeek, Integer> dayMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(dayMap.keySet()));

        dayColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            DayOfWeek day = cellData.getValue();
            return new SimpleIntegerProperty(dayMap.get(day)).asObject();
        });
    }

    @Override
    protected Map<DayOfWeek, Integer> topStats() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
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
}
