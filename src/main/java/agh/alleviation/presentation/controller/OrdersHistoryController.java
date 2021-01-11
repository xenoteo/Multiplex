package agh.alleviation.presentation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.context.ActiveUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller of the orders history view. Responsible for displaying all of the user's orders.
 *
 * @author Anna Nosek
 */
@Component
@FxmlView("/views/OrdersHistory.fxml")
public class OrdersHistoryController extends GenericController implements PropertyChangeListener {

    /**
     * The order list.
     */
    @FXML
    private ListView<EntityObject> ordersList;

    /**
     * The active user.
     */
    private ActiveUser activeUser;

    /**
     * Sets the active user.
     * @param activeUser  the active user
     */
    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Initializes the order history view.
     */
    @FXML
    private void initialize() {
        ordersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serviceManager.addObservableList(Order.class);
        ordersList.setItems(serviceManager.getActiveElementsList(Order.class));
        ordersList.setCellFactory(column -> {
            ListCell<EntityObject> cell = new ListCell<>() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                @Override
                protected void updateItem(EntityObject item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else {
                        Order order = (Order) item;
                        LocalDateTime date = order.getDate();
                        List<Ticket> tickets = order.getTickets();

                        String firstMovieName = tickets.get(0).getSeance().getMovie().getName();
                        String ticketInfo =
                            tickets.size() == 1 ? "ticket for " + firstMovieName : tickets.size() + " tickets";
                        String formattedDate = date.format(formatter);

                        setText("Order date: " + formattedDate + ", " + ticketInfo);
                    }
                }
            };

            return cell;
        });
    }

    /**
     * Handles show details action.
     *
     * @param event  the event.
     */
    @FXML
    private void showDetailsAction(ActionEvent event) {
        Order selectedOrder = (Order) ordersList.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            viewControllerManager.getOrderDialogContext().showEditItemDialog(selectedOrder);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        User user = (User) evt.getNewValue();
        if (user == null) return;
        serviceManager.clearObservableList(Order.class);
        activeUser.fillOrders(user);
        activeUser.getAllOrders().forEach(order -> serviceManager.addToObservable(order));
        ordersList.setItems(serviceManager.getList(Order.class));
    }
}
