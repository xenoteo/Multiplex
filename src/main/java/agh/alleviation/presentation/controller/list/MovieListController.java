package agh.alleviation.presentation.controller.list;

import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.service.SeanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * The type Movie list controller.
 */
@Component
@FxmlView("/views/MovieList.fxml")
public class MovieListController extends GenericListController<Movie> {
    private SeanceService seanceService;

    /**
     * The Add movie.
     */
    @FXML
    public Button addMovie;

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

    @FXML
    public void initialize() {
        super.initialize();

        serviceManager.fillFromService(Movie.class);
        itemTable.setItems(serviceManager.getActiveElementsList(Movie.class));

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().genreProperty().asString());
        descriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().descriptionProperty());
        directorColumn.setCellValueFactory(dataValue -> dataValue.getValue().directorProperty());
        actorsColumn.setCellValueFactory(dataValue -> dataValue.getValue().actorsProperty());

    }

    @Override
    protected void handleAddAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        switch (buttonId) {
            case "addMovie" -> {
                viewControllerManager.getMovieDialogContext().showAddItemDialog();
            }
            case "addSeance" -> {
                Movie movie = (Movie) itemTable.getSelectionModel().getSelectedItem();
                if (movie != null) {
                    Seance seance =
                        seanceService.addSeance(movie);  // TODO: sad panda, maybe instead accept a movie in show editItemDialog
                    viewControllerManager.getSeanceDialogContext().showEditItemDialog(seance);
                }
            }
            default -> System.out.println("No action");
        }
    }

    @Override
    protected void handleEditAction(ActionEvent event) {
        Movie movie = (Movie) itemTable.getSelectionModel().getSelectedItem();
        if (movie != null) {
            viewControllerManager.getMovieDialogContext().showEditItemDialog(movie);
        }
    }

    @Override
    protected void handleDeleteAction(ActionEvent event) {
        Movie movie = (Movie) itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(movie);
    }

}
