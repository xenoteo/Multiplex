package agh.alleviation.controller.list;

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

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().genreProperty().asString());
        descriptionColumn.setCellValueFactory(dataValue -> dataValue.getValue().descriptionProperty());
        directorColumn.setCellValueFactory(dataValue -> dataValue.getValue().directorProperty());
        actorsColumn.setCellValueFactory(dataValue -> dataValue.getValue().actorsProperty());

    }

    @Override
    protected void resetContents(){
        itemObservableList.addAll(service.getAllActive());
    }


    @Override
    protected void handleAddAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        switch (buttonId) {
            case "addMovie" -> {
                Movie movie = viewControllerManager.getMovieDialogContext().showAddItemDialog();
                if(movie != null) {
                    itemObservableList.add(movie);
                }
            }
            case "addSeance" -> {
                Movie movie = itemTable.getSelectionModel().getSelectedItem();
                if(movie != null) {
                    Seance seance = seanceService.addSeance(movie);
                    viewControllerManager.getSeanceDialogContext().showEditItemDialog(seance);
                }
            }
            default -> System.out.println("No action");
        }
    }

    @Override
    protected void handleEditAction(ActionEvent event) {
        Movie movie = itemTable.getSelectionModel().getSelectedItem();
        if(movie != null) {
            viewControllerManager.getMovieDialogContext().showEditItemDialog(movie);
        }
    }

    @Override
    protected void handleDeleteAction(ActionEvent event) {
        Movie movie = itemTable.getSelectionModel().getSelectedItem();
        if(movie != null) {
            itemObservableList.remove(movie);
            service.delete(movie);
        }
    }

    @Autowired
    public MovieListController(MovieService movieService, SeanceService seanceService){
        this.service = movieService;
        this.seanceService = seanceService;
    }


}
