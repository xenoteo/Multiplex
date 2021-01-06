package agh.alleviation.presentation.controller.list;

import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.context.ActiveUser;
import agh.alleviation.presentation.filter.*;
import agh.alleviation.util.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The type Seance list controller.
 */
@Component
@FxmlView("/views/SeanceList.fxml")
public class SeanceListController extends GenericListController<Seance> implements PropertyChangeListener {

    /**
     * The Movie column.
     */
    @FXML
    public TableColumn<Seance, String> movieColumn;

    /**
     * The Hall column.
     */
    @FXML
    public TableColumn<Seance, Number> hallColumn;

    /**
     * The Date column.
     */
    @FXML
    public TableColumn<Seance, LocalDateTime> dateColumn;

    /**
     * The Price column.
     */
    @FXML
    public TableColumn<Seance, Number> priceColumn;

    @FXML
    private TextField movieField;

    @FXML
    private TextField hallField;

    @FXML
    private DatePicker dateFromField;

    @FXML
    private DatePicker dateToField;

    @FXML
    private TextField maxPriceField;

    private ActiveUser activeUser;

    private CompositeFilter filter;

    @FXML
    private Button add;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @FXML
    protected void initialize() {
        super.initialize();
        serviceManager.fillFromService(Seance.class);
        filter = new CompositeFilter();
        itemTable.setItems(serviceManager.getActiveElementsList(Seance.class));
        resetTableItems();
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().priceProperty());
    }

    @Override
    protected void handleAddAction(ActionEvent event) {
        viewControllerManager.getSeanceDialogContext().showAddItemDialog();
    }

    @Override
    protected void handleEditAction(ActionEvent event) {
        Seance seance = (Seance) itemTable.getSelectionModel().getSelectedItem();
        if (seance != null) {
            viewControllerManager.getSeanceDialogContext().showEditItemDialog(seance);
        }
    }

    @FXML
    private void handleAddToBasketAction(ActionEvent event) {
        Seance seance = (Seance) itemTable.getSelectionModel().getSelectedItem();
        if (seance != null) {
            Ticket ticket = new Ticket(seance);
            activeUser.getActiveOrder().addTicket(ticket);
            serviceManager.addToObservable(ticket);
        }
    }

    private void resetTableItems() {
        User userEntity = activeUser.getUserEntity();
        if (userEntity != null && userEntity.getUserType() == UserType.CUSTOMER) //TODO: on user change reset
            filter.addFilter(new DateFilter(LocalDateTime.now(), LocalDateTime.now().plusDays(14)));
        itemTable.setItems(serviceManager
            .getActiveElementsList(Seance.class)
            .filtered(item -> filter.apply((Seance) item)));
    }

    @FXML
    private void applyFilters() {

        clearFilters();

        if (!movieField.getText().isEmpty()) filter.addFilter(new MovieFilter(movieField.getText()));

        if (!hallField.getText().isEmpty()) filter.addFilter(new HallFilter(Integer.parseInt(hallField.getText())));

        if (!maxPriceField.getText().isEmpty())
            filter.addFilter(new PriceFilter(Integer.parseInt(maxPriceField.getText())));

        DateFilter dateFilter = new DateFilter();
        if (dateFromField.getValue() != null)
            dateFilter.setMinDate(LocalDateTime.of(dateFromField.getValue(), LocalTime.of(0, 0)));
        if (dateToField.getValue() != null)
            dateFilter.setMaxDate(LocalDateTime.of(dateToField.getValue(), LocalTime.of(23, 59)));

        filter.addFilter(dateFilter);

        itemTable.setItems(itemTable.getItems().filtered(item -> filter.apply((Seance) item)));
    }

    @FXML
    private void clearFilters() {
        filter.clearFilters();
        resetTableItems();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property changed");
        boolean canEdit;
        if (evt.getNewValue() != null) {
            canEdit = ((User) evt.getNewValue()).getUserType() != UserType.CUSTOMER;
        } else {
            canEdit = false;
        }
        add.setVisible(canEdit);
        edit.setVisible(canEdit);
        delete.setVisible(canEdit);
        filter.clearFilters();
        resetTableItems();
    }
}
