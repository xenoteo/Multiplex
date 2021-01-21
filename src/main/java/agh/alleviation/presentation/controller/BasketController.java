package agh.alleviation.presentation.controller;

import agh.alleviation.model.*;
import agh.alleviation.presentation.context.ActiveUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

/**
 * Controller of the basket view. Responsible for displaying current content of active user's basket.
 *
 * @author Anna Nosek
 */
@Component
@FxmlView("/views/Basket.fxml")
public class BasketController extends GenericController implements PropertyChangeListener {

    /**
     * The Item table.
     */
    @FXML
    private TableView<EntityObject> ticketTable;

    /**
     * The movie column.
     */
    @FXML
    public TableColumn<Ticket, String> movieColumn;

    /**
     * The hall column.
     */
    @FXML
    public TableColumn<Ticket, Number> hallColumn;

    /**
     * The date column.
     */
    @FXML
    public TableColumn<Ticket, LocalDateTime> dateColumn;

    /**
     * The price column.
     */
    @FXML
    public TableColumn<Ticket, Number> priceColumn;

    /**
     * The active user.
     */
    private ActiveUser activeUser;

    /**
     * Sets the active user.
     *
     * @param activeUser the active user
     */
    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Initializes the empty basket.
     */
    @FXML
    private void initialize() {

        ticketTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ticketTable.setItems(serviceManager.getActiveElementsList(Ticket.class));
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSeance().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSeance().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSeance().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSeance().priceProperty());
    }

    /**
     * Deletes a ticket from an order.
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        EntityObject item = ticketTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            activeUser.getActiveOrder().getTickets().remove((Ticket) item);
            serviceManager.deleteFromObservable(item);
        }
    }

    /**
     * Checkouts from the basket. The order is persisted and the basket is cleared.
     */
    @FXML
    private void handleCheckoutAction(ActionEvent event) {
        Order order = activeUser.getActiveOrder();
        if (order.getTickets() == null || order.getTickets().isEmpty()) return;
        order.setDate(LocalDateTime.now());
        serviceManager.add(order);
        serviceManager.clearObservableList(Ticket.class);
        activeUser.setEmptyOrder();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Order finished successfully!");
        alert.show();
    }

    /**
     * The basket is cleared after the change of the active user.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        serviceManager.clearObservableList(Ticket.class);
    }
}
