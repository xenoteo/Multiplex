package agh.alleviation.presentation.controller.list;

import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.context.ActiveUser;
import agh.alleviation.presentation.filter.*;
import agh.alleviation.util.UserType;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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

    /**
     * The Rating column.
     */
    @FXML
    public TableColumn<Seance, String> ratingColumn;

    /**
     * The movie text field.
     */
    @FXML
    private TextField movieField;

    /**
     * The hall text field.
     */
    @FXML
    private TextField hallField;

    /**
     * The date from picker.
     */
    @FXML
    private DatePicker dateFromField;

    /**
     * The date to picker.
     */
    @FXML
    private DatePicker dateToField;

    /**
     * The max price text field.
     */
    @FXML
    private TextField maxPriceField;

    /**
     * The active user.
     */
    private ActiveUser activeUser;

    /**
     * The composite filter.
     */
    private CompositeFilter filter;

    /**
     * The add button.
     */
    @FXML
    private Button add;

    /**
     * The edit button.
     */
    @FXML
    private Button edit;

    /**
     * The delete button.
     */
    @FXML
    private Button delete;

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
     * Initializes the seance list view.
     */
    @FXML
    protected void initialize() {
        super.initialize();
        filter = new CompositeFilter();

        serviceManager.fillFromService(Seance.class);
        var sortedList = serviceManager.getActiveElementsList(Seance.class).sorted();
        sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedList);

        resetTableItems();
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().priceProperty());
        ratingColumn.setCellValueFactory(cellData -> {
            Seance seance = cellData.getValue();
            return Bindings.createStringBinding(() -> {
                int likes = seance.getMovie().getLikes();
                int ratesCount = likes + seance.getMovie().getDislikes();

                return ratesCount == 0 ? "No rates" : 100 * likes / ratesCount + "%";
            }, seance.getMovie().likesProperty(), seance.getMovie().dislikesProperty());
        });
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

    @Override
    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        Seance seance = (Seance) itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(seance);
    }

    /**
     * Handles add to basket action.
     *
     * @param event  the event
     */
    @FXML
    private void handleAddToBasketAction(ActionEvent event) {
        Seance seance = (Seance) itemTable.getSelectionModel().getSelectedItem();
        if (seance != null) {
            Ticket ticket = new Ticket(seance);
            activeUser.getActiveOrder().addTicket(ticket);
            serviceManager.addToObservable(ticket);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Ticket added to basket successfully!");

            ButtonType buttonContinue = new ButtonType("Continue");
            ButtonType buttonGoToBasket = new ButtonType("Go to basket");
            alert.getButtonTypes().setAll(buttonContinue, buttonGoToBasket);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonGoToBasket) {
                viewControllerManager.switchView(Screen.TICKET_LIST);
            }
        }
    }

    /**
     * Resets the table items.
     */
    private void resetTableItems() {
        User userEntity = activeUser.getUserEntity();
        if (userEntity != null && userEntity.getUserType() == UserType.CUSTOMER)
            filter.addFilter(new DateFilter(LocalDateTime.now(), LocalDateTime.now().plusDays(14)));

        var sortedList =
            serviceManager.getActiveElementsList(Seance.class).filtered(item -> filter.apply((Seance) item)).sorted();
        sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedList);
    }

    /**
     * Applies the filters.
     */
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

        var sortedList = itemTable.getItems().filtered(item -> filter.apply((Seance) item)).sorted();
        sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedList);
    }

    /**
     * Clears the filters.
     */
    @FXML
    private void clearFilters() {
        filter.clearFilters();
        resetTableItems();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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
