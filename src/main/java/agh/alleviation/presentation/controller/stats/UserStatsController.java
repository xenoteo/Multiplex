package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        Map<User, Integer> userMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(userMap.keySet()));

        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        ticketsColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleIntegerProperty(userMap.get(user)).asObject();
        });
    }

    @Override
    protected Map<User, Integer> topStats() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(ticketObj -> ticketObj.getIsActive() && ((Ticket) ticketObj).getSeance().getIsActive())
                .collect(Collectors.toList());
        Map<User, Integer> userMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            User user = ((Ticket) ticketObject).getOrder().getUser();
            userMap.put(user, userMap.getOrDefault(user, 0) + 1);
        }
        return sortMap(userMap, 10);
    }
}
