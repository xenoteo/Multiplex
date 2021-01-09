package agh.alleviation.presentation.controller;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Order;
import agh.alleviation.model.Seance;
import agh.alleviation.model.Ticket;
import agh.alleviation.presentation.controller.edit_dialog.EditDialogController;
import agh.alleviation.presentation.filter.CompositeFilter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.kordamp.ikonli.javafx.FontIcon;
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

    @FXML
    public TableColumn<Seance, Void> rateColumn;

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
        Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Seance, Void> call(final TableColumn<Seance, Void> param) {
                final TableCell<Seance, Void> cell = new TableCell<>() {

                    private final HBox box = new HBox();
                    private final ToggleButton likeButton = new ToggleButton("");
                    private final ToggleButton dislikeButton = new ToggleButton("");

                    {
                        likeButton.setGraphic(new FontIcon());
                        likeButton.setId("like-button");
                        likeButton.setOnAction((ActionEvent event) -> {
                            Seance data = getTableView().getItems().get(getIndex());
                            likeButton.getStyleClass().add("button-selected");
                            dislikeButton.getStyleClass().remove("button-selected");
                            System.out.println("like: " + data);
                        });

                        dislikeButton.setGraphic(new FontIcon());
                        dislikeButton.setId("dislike-button");
                        dislikeButton.setOnAction((ActionEvent event) -> {
                            Seance data = getTableView().getItems().get(getIndex());
                            likeButton.getStyleClass().remove("button-selected");
                            dislikeButton.getStyleClass().add("button-selected");
                            System.out.println("dislike: " + data);
                        });

                        box.getChildren().addAll(likeButton, dislikeButton);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };

        rateColumn.setCellFactory(cellFactory);
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
