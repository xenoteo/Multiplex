package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller responsible for displaying genre statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/GenreStats.fxml")
public class GenreStatsController extends GenericStatsController<Genre>{
    /**
     * The Name column.
     */
    @FXML
    public TableColumn<Genre, String> nameColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<Genre, Integer> ticketsColumn;

    @FXML
    public void initialize() {
        Map<Genre, Integer> genreMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(genreMap.keySet()));

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        ticketsColumn.setCellValueFactory(cellData ->{
            Genre genre = cellData.getValue();
            return new SimpleIntegerProperty(genreMap.get(genre)).asObject();
        });
    }

    @Override
    protected Map<Genre, Integer> topStats() {
        TicketService ticketService = (TicketService) serviceManager.getService(Ticket.class);
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<Genre, Integer> genreMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            Genre genre = ((Ticket) ticketObject).getSeance().getMovie().getGenre();
            genreMap.put(genre, genreMap.getOrDefault(genre, 0) + 1);
        }
        return sortMap(genreMap, 10);
    }
}
