package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of user statistics and update of the statistics table.
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 * @author Ksenia Fiodarava
 */
public class UserStats extends GenericStats<User> {
    /**
     * The login column.
     */
    public TableColumn<User, String> loginColumn;

    /**
     * The tickets bought column.
     */
    public TableColumn<User, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        public static final String NICK = "Nick";
        public static final String TICKETS = "Tickets bought";
    }

    public UserStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<User, Integer> topStats() {
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<User, Integer> userMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            User user = ((Ticket) ticketObject).getOrder().getUser();
            userMap.put(user, userMap.getOrDefault(user, 0) + 1);
        }
        return sortMap(userMap, 10);
    }

    @Override
    public void showStats() {
        updateTableColumns();
        Map<User, Integer> userMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(userMap.keySet()));

        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        ticketsColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleIntegerProperty(userMap.get(user)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        loginColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        loginColumn.setText(Columns.NICK);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(loginColumn);
        columns.add(ticketsColumn);
    }
}
