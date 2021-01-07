package agh.alleviation.presentation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.presentation.controller.edit_dialog.EditDialogController;
import agh.alleviation.presentation.filter.CompositeFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@FxmlView("/views/OrderDetailsDialog.fxml")
public class OrderDetailsDialogController extends EditDialogController<Order> {
    /**
     * The Item table.
     */
    @FXML
    protected TableView<Seance> itemTable;

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

    @Override
    protected Validator createValidations() {
        return null;
    }

    @FXML
    protected void initialize() {
        super.initialize();
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().priceProperty());
    }

    @Override
    public void setEditedItem(Order item) {
        super.setEditedItem(item);
        itemTable.setItems(FXCollections.observableArrayList(editedItem
            .getTickets()
            .stream()
            .map(Ticket::getSeance)
            .collect(Collectors.toList())));
    }
}
