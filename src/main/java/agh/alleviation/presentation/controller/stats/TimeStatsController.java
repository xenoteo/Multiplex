package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.service.OrderService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;

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
        Map<LocalTime, Integer> timeMap = top10stats();
        itemTable.setItems(FXCollections.observableArrayList(timeMap.keySet()));

        timeColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().toString()));
        ticketsColumn.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue();
            return new SimpleIntegerProperty(timeMap.get(time)).asObject();
        });
    }

    @Override
    protected Map<LocalTime, Integer> top10stats() {
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);
        List<EntityObject> orders = orderService.getAll();
        Map<LocalTime, Integer> timeMap = new HashMap<>();
        for (EntityObject orderObject : orders){
            Order order = (Order) orderObject;
            List<Ticket> tickets = order.getTickets();
            for (Ticket ticket : tickets){
                LocalTime time = ticket.getSeance().getDate().toLocalTime();
                timeMap.put(time, timeMap.getOrDefault(time, 0) + 1);
            }
        }

        Map<LocalTime, Integer> timeMapSorted = new LinkedHashMap<>();
        timeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEachOrdered(x -> timeMapSorted.put(x.getKey(), x.getValue()));

        return timeMapSorted;
    }
}
