package agh.alleviation.controller.list;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.service.MovieService;
import agh.alleviation.service.SeanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/MovieList.fxml")
public class MovieListController extends GenericListController<Movie, MovieService> {
    private SeanceService seanceService;

    @FXML
    public Button addMovie;

    @FXML
    public Button addSeance;

    @FXML
    public TableColumn<Movie, String> nameColumn;

    @FXML
    public TableColumn<Movie, String> genreColumn;

    @FXML
    public TableColumn<Movie, String> descriptionColumn;

    @FXML
    public TableColumn<Movie, String> directorColumn;

    @FXML
    public TableColumn<Movie, String> actorsColumn;

    @FXML
    public void initialize() {
        super.initialize();

        observableComposite.fillFromService(Movie.class);
        itemTable.setItems(observableComposite.getActiveElementsList(Movie.class));

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
        observableComposite.delete(movie);
    }

    @Autowired
    public MovieListController(MovieService movieService, SeanceService seanceService) {
        this.service = movieService;
        this.seanceService = seanceService;
    }

}
