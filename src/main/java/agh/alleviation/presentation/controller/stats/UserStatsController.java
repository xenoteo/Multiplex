package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.OrderService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Controller responsible for displaying user statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/UserStats.fxml")
public class UserStatsController extends GenericStatsController<User>{
    /**
     * The login column.
     */
    @FXML
    public TableColumn<User, String> loginColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<User, Integer> ticketsColumn;

    @FXML
    public void initialize() {
        Map<User, Integer> userMap = top10stats();
        itemTable.setItems(FXCollections.observableArrayList(userMap.keySet()));

        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        ticketsColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleIntegerProperty(userMap.get(user)).asObject();
        });
    }

    @Override
    protected Map<User, Integer> top10stats() {
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);
        List<EntityObject> orders = orderService.getAll();
        Map<User, Integer> userMap = new HashMap<>();
        for (EntityObject orderObject : orders){
            Order order = (Order) orderObject;
            User user = order.getUser();
            userMap.put(user, userMap.getOrDefault(user, 0) + order.getTickets().size());
        }

        Map<User, Integer> userMapSorted = new LinkedHashMap<>();
        userMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEachOrdered(x -> userMapSorted.put(x.getKey(), x.getValue()));

        return userMapSorted;
    }
}
