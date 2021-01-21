package agh.alleviation.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Genre;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generation of genre statistics and update of the statistics table.
 *
 * @author Ksenia Fiodarava
 * @see GenericStats
 * @see agh.alleviation.presentation.controller.StatisticsController
 */
public class GenreStats extends GenericStats<Genre> {
    /**
     * The Name column.
     */
    public TableColumn<Genre, String> nameColumn;

    /**
     * The tickets bought column.
     */
    public TableColumn<Genre, Integer> ticketsColumn;

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant NAME.
         */
        public static final String NAME = "Name";
        /**
         * The constant TICKETS.
         */
        public static final String TICKETS = "Tickets bought";
    }

    /**
     * Instantiates a genre stats.
     *
     * @param itemTable     the item table
     * @param ticketService the ticket service
     */
    public GenreStats(TableView itemTable, TicketService ticketService) {
        super(itemTable, ticketService);
    }

    @Override
    protected Map<Genre, Integer> topStats() {
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

    @Override
    public void showStats() {
        updateTableColumns();
        Map<Genre, Integer> genreMap = topStats();
        itemTable.setItems(FXCollections.observableArrayList(genreMap.keySet()));

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        ticketsColumn.setCellValueFactory(cellData ->{
            Genre genre = cellData.getValue();
            return new SimpleIntegerProperty(genreMap.get(genre)).asObject();
        });
    }

    @Override
    protected void setUpColumns() {
        nameColumn = new TableColumn<>();
        ticketsColumn = new TableColumn<>();

        nameColumn.setText(Columns.NAME);
        ticketsColumn.setText(Columns.TICKETS);
    }

    @Override
    protected void updateTableColumns() {
        var columns = itemTable.getColumns();
        columns.clear();
        columns.add(nameColumn);
        columns.add(ticketsColumn);
    }
}
