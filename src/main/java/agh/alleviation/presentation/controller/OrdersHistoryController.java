package agh.alleviation.presentation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.context.ActiveUser;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.List;

@Component
@FxmlView("/views/OrdersHistory.fxml")
public class OrdersHistoryController extends GenericController implements PropertyChangeListener {

    @FXML
    public TableColumn<Order, LocalDateTime> dateColumn;

    @FXML
    private TableView<EntityObject> ordersTable;

    private ActiveUser activeUser;

    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @FXML
    private void initialize() {

        ordersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serviceManager.addObservableList(Order.class);
        ordersTable.setItems(serviceManager.getList(Order.class));
//        activeUser.getAllOrders().forEach(order -> serviceManager.addToObservable(order));
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());

    }

    @FXML
    private void showDetailsAction(){
        System.out.println("I will show details in the future");
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        User user = (User) evt.getNewValue();
        activeUser.fillOrders(user);
        activeUser.getAllOrders().forEach(order -> serviceManager.addToObservable(order));
        ordersTable.setItems(serviceManager.getList(Order.class));

    }
}
