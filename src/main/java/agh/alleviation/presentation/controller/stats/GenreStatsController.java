package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.*;
import agh.alleviation.model.user.User;
import agh.alleviation.service.OrderService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;

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
        Map<Genre, Integer> genreMap = top10stats();
        itemTable.setItems(FXCollections.observableArrayList(genreMap.keySet()));

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        ticketsColumn.setCellValueFactory(cellData ->{
            Genre genre = cellData.getValue();
            return new SimpleIntegerProperty(genreMap.get(genre)).asObject();
        });
    }

    @Override
    protected Map<Genre, Integer> top10stats() {
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);
        List<EntityObject> orders = orderService.getAll();
        Map<Genre, Integer> genreMap = new HashMap<>();
        for (EntityObject orderObject : orders){
            Order order = (Order) orderObject;
            List<Ticket> tickets = order.getTickets();
            for (Ticket ticket : tickets){
                Genre genre = ticket.getSeance().getMovie().getGenre();
                genreMap.put(genre, genreMap.getOrDefault(genre, 0) + 1);
            }
        }

        Map<Genre, Integer> genreMapSorted = new LinkedHashMap<>();
        genreMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEachOrdered(x -> genreMapSorted.put(x.getKey(), x.getValue()));

        return genreMapSorted;
    }
}
