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
 * @author Ksenia Fiodarava
 */
@Component
@FxmlView("/views/Statistics.fxml")
public class StatisticsController extends GenericController{

    @FXML
    public Button movies;

    @FXML
    public Button genres;

    @FXML
    public Button users;

    @FXML
    public Button time;

    @FXML
    public Button days;

    @FXML
    public Button months;

    @FXML
    public Button reset;
    
    @FXML
    public TableView itemTable;
    
    private Map<Button, GenericStats> statsMap = new HashMap<>();

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

    @FXML
    public void handleButtonClicked(ActionEvent event){
        Button button = (Button) event.getSource();
        statsMap.get(button).showStats();
    }

    @FXML
    public void reset(ActionEvent event){
        itemTable.getColumns().clear();
    }
}
