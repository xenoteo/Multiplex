package agh.alleviation.presentation.controller;

import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import agh.alleviation.stats.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsible for displaying a screen where user can choose a type of statistics to see.
 *
 * @author Ksenia Fiodarava
 */
@Component
@FxmlView("/views/Statistics.fxml")
public class StatisticsController extends GenericController{

    /**
     * The movies button.
     */
    @FXML
    public Button movies;

    /**
     * The genres button.
     */
    @FXML
    public Button genres;

    /**
     * The users button.
     */
    @FXML
    public Button users;

    /**
     * The time button.
     */
    @FXML
    public Button time;

    /**
     * The days button.
     */
    @FXML
    public Button days;

    /**
     * The month button.
     */
    @FXML
    public Button months;

    /**
     * The reset button.
     */
    @FXML
    public Button reset;

    /**
     * The item table.
     */
    @FXML
    public TableView itemTable;

    /**
     * The button-statistics map.
     */
    private Map<Button, GenericStats> statsMap = new HashMap<>();

    /**
     * Initialized the statistics view.
     */
    @FXML
    public void initialize() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
        statsMap.put(movies, new MovieStats(itemTable, ticketService));
        statsMap.put(genres, new GenreStats(itemTable, ticketService));
        statsMap.put(users, new UserStats(itemTable, ticketService));
        statsMap.put(time, new TimeStats(itemTable, ticketService));
        statsMap.put(days, new DayStats(itemTable, ticketService));
        statsMap.put(months, new MonthStats(itemTable, ticketService));
    }

    /**
     * Handles button click.
     *
     * @param event the event
     */
    @FXML
    public void handleButtonClicked(ActionEvent event){
        Button button = (Button) event.getSource();
        statsMap.get(button).showStats();
    }

    /**
     * Resets the item table.
     *
     * @param event the event
     */
    @FXML
    public void reset(ActionEvent event){
        itemTable.getColumns().clear();
    }
}
