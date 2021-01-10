package agh.alleviation.presentation.controller;

import agh.alleviation.model.*;
import agh.alleviation.presentation.controller.edit_dialog.EditDialogController;
import agh.alleviation.service.MovieService;
import agh.alleviation.util.Rating;
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

/**
 * A controller of view responsible for displaying details of an existing order.
 *
 * @author Kamil Krzempek
 */

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

    /**
     * Column with percentage of positive reviews
     */
    @FXML
    public TableColumn<Seance, Void> rateColumn;

//    @FXML
//    public TableColumn<Seance, Boolean> activeColumn;

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
//        activeColumn.setCellValueFactory(dataValue -> dataValue.getValue().isActiveProperty());
        Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Seance, Void> call(final TableColumn<Seance, Void> param) {
                final TableCell<Seance, Void> cell = new TableCell<>() {

                    private final HBox box = new HBox();
                    private final ToggleButton likeButton = new ToggleButton("");
                    private final ToggleButton dislikeButton = new ToggleButton("");
                    private final String selectedClassName = "button-selected";
                    private final MovieService movieService = ((MovieService) serviceManager.getService(Movie.class));

                    {
                        likeButton.setGraphic(new FontIcon());
                        likeButton.setId("like-button");
                        likeButton.setOnAction((ActionEvent event) -> {
                            Ticket ticket = editedItem.getTickets().get(getIndex());
                            likeButton.getStyleClass().add(selectedClassName);
                            dislikeButton.getStyleClass().remove(selectedClassName);
                            movieService.rateMovie(ticket, Rating.POSITIVE);
                            serviceManager.deleteFromObservable(ticket.getSeance().getMovie());
                            serviceManager.addToObservable(ticket.getSeance().getMovie());
                            serviceManager.deleteFromObservable(ticket.getSeance());
                            serviceManager.addToObservable(ticket.getSeance());
                        });

                        dislikeButton.setGraphic(new FontIcon());
                        dislikeButton.setId("dislike-button");
                        dislikeButton.setOnAction((ActionEvent event) -> {
                            Ticket ticket = editedItem.getTickets().get(getIndex());
                            likeButton.getStyleClass().remove(selectedClassName);
                            dislikeButton.getStyleClass().add(selectedClassName);
                            movieService.rateMovie(ticket, Rating.NEGATIVE);
                            serviceManager.deleteFromObservable(ticket.getSeance().getMovie());
                            serviceManager.addToObservable(ticket.getSeance().getMovie());
                            serviceManager.deleteFromObservable(ticket.getSeance());
                            serviceManager.addToObservable(ticket.getSeance());
                        });

                        box.getChildren().addAll(likeButton, dislikeButton);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Ticket ticket = editedItem.getTickets().get(getIndex());

                            if (ticket.getIsActive()) {
                                setGraphic(box);

                                if (ticket.getIsRated()) {
                                    ToggleButton button =
                                        ticket.getIsRatingPositive() == Rating.POSITIVE ? likeButton : dislikeButton;
                                    button.getStyleClass().add(selectedClassName);
                                }
                            }
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
