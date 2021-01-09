package agh.alleviation.presentation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.context.ActiveUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@FxmlView("/views/OrdersHistory.fxml")
public class OrdersHistoryController extends GenericController implements PropertyChangeListener {

    @FXML
    private ListView<EntityObject> ordersList;

    private ActiveUser activeUser;

    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @FXML
    private void initialize() {
        ordersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serviceManager.addObservableList(Order.class);
        ordersList.setItems(serviceManager.getList(Order.class));
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
