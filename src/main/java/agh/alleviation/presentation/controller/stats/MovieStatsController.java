package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Order;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.OrderService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Controller responsible for displaying movie statistics.
 * @author Ksenia Fiodarava
 * @see GenericStatsController
 */
@Component
@FxmlView("/views/MovieStats.fxml")
public class MovieStatsController extends GenericStatsController<Movie> {
    /**
     * The Name column.
     */
    @FXML
    public TableColumn<Movie, String> nameColumn;

    /**
     * The Genre column.
     */
    @FXML
    public TableColumn<Movie, String> genreColumn;

    /**
     * The Description column.
     */
    @FXML
    public TableColumn<Movie, String> descriptionColumn;

    /**
     * The Director column.
     */
    @FXML
    public TableColumn<Movie, String> directorColumn;

    /**
     * The Actors column.
     */
    @FXML
    public TableColumn<Movie, String> actorsColumn;

    /**
     * The rating column.
     */
    @FXML
    public TableColumn<Movie, String> ratingColumn;

    /**
     * The tickets bought column.
     */
    @FXML
    public TableColumn<Movie, Integer> ticketsColumn;

    @FXML
    public void initialize() {
        serviceManager.fillFromService(Movie.class);
        Map<Movie, Integer> movieMap = top10stats();
        itemTable.setItems(FXCollections.observableArrayList(movieMap.keySet()));

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().genreProperty().asString());
        descriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().descriptionProperty());
        directorColumn.setCellValueFactory(dataValue -> dataValue.getValue().directorProperty());
        actorsColumn.setCellValueFactory(dataValue -> dataValue.getValue().actorsProperty());
        ratingColumn.setCellValueFactory(cellData -> {
            Movie movie = cellData.getValue();
            return Bindings.createStringBinding(() -> {
                int likes = movie.getLikes();
                int ratesCount = likes + movie.getDislikes();
                System.out.println("likes " + likes + ", ratesCount " + ratesCount);

                return ratesCount == 0 ? "No rates" : 100 * likes / ratesCount + "%";
            }, movie.likesProperty(), movie.dislikesProperty());
        });
        ticketsColumn.setCellValueFactory(cellData ->{
            Movie movie = cellData.getValue();
            return new SimpleIntegerProperty(movieMap.get(movie)).asObject();
        });
    }

    @Override
    protected Map<Movie, Integer> top10stats() {
        OrderService orderService = (OrderService) serviceManager.getService(Order.class);
        List<EntityObject> orders = orderService.getAll();
        Map<Movie, Integer> movieMap = new HashMap<>();
        for (EntityObject orderObject : orders){
            Order order = (Order) orderObject;
            List<Ticket> tickets = order.getTickets();
            for (Ticket ticket : tickets){
                Movie movie = ticket.getSeance().getMovie();
                movieMap.put(movie, movieMap.getOrDefault(movie, 0) + 1);
            }
        }

        Map<Movie, Integer> movieMapSorted = new LinkedHashMap<>();
        movieMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEachOrdered(x -> movieMapSorted.put(x.getKey(), x.getValue()));

        return movieMapSorted;
    }
}
