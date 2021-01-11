package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of movie statistics and update of the statistics table.
 *
 * @author Ksenia Fiodarava
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 */
public class MovieStats extends GenericStats<Movie> {
    /**
     * The Name column.
     */
    public TableColumn<Movie, String> nameColumn;

    /**
     * The Genre column.
     */
    public TableColumn<Movie, String> genreColumn;

    /**
     * The Description column.
     */
    public TableColumn<Movie, String> descriptionColumn;

    /**
     * The Director column.
     */
    public TableColumn<Movie, String> directorColumn;

    /**
     * The Actors column.
     */
    public TableColumn<Movie, String> actorsColumn;

    /**
     * The rating column.
     */
    public TableColumn<Movie, String> ratingColumn;

    /**
     * The tickets bought column.
     */
    public TableColumn<Movie, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant NAME.
         */
        public static final String NAME = "Name";
        /**
         * The constant GENRE.
         */
        public static final String GENRE = "Genre";
        /**
         * The constant DESCRIPTION.
         */
        public static final String DESCRIPTION = "Description";
        /**
         * The constant DIRECTOR.
         */
        public static final String DIRECTOR = "Director";
        /**
         * The constant ACTORS.
         */
        public static final String ACTORS = "Actors";
        /**
         * The constant RATING.
         */
        public static final String RATING = "Rating";
        /**
         * The constant TICKETS.
         */
        public static final String TICKETS = "Tickets bought";
    }

    /**
     * Instantiates a movie stats.
     *
     * @param itemTable     the item table
     * @param ticketService the ticket service
     */
    public MovieStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<Movie, Integer> topStats() {
        List<EntityObject> tickets = ticketService.getAll().stream()
                .filter(EntityObject::getIsActive)
                .collect(Collectors.toList());
        Map<Movie, Integer> movieMap = new HashMap<>();
        for (EntityObject ticketObject : tickets){
            Movie movie = ((Ticket) ticketObject).getSeance().getMovie();
            movieMap.put(movie, movieMap.getOrDefault(movie, 0) + 1);
        }
        return sortMap(movieMap, 10);
    }

    @Override
    public void showStats() {
        updateTableColumns();
        Map<Movie, Integer> movieMap = topStats();
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

                return ratesCount == 0 ? "No rates" : 100 * likes / ratesCount + "%";
            }, movie.likesProperty(), movie.dislikesProperty());
        });
        ticketsColumn.setCellValueFactory(cellData ->{
            Movie movie = cellData.getValue();
            return new SimpleIntegerProperty(movieMap.get(movie)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        nameColumn = new TableColumn<>();
        genreColumn = new TableColumn<>();
        descriptionColumn = new TableColumn<>();
        directorColumn = new TableColumn<>();
        actorsColumn = new TableColumn<>();
        ratingColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        nameColumn.setText(Columns.NAME);
        genreColumn.setText(Columns.GENRE);
        descriptionColumn.setText(Columns.DESCRIPTION);
        directorColumn.setText(Columns.DIRECTOR);
        actorsColumn.setText(Columns.ACTORS);
        ratingColumn.setText(Columns.RATING);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(nameColumn);
        columns.add(genreColumn);
        columns.add(descriptionColumn);
        columns.add(directorColumn);
        columns.add(actorsColumn);
        columns.add(ratingColumn);
        columns.add(ticketsColumn);
    }
}
