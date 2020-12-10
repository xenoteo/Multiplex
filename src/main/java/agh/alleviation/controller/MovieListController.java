package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.presentation.Screen;
import agh.alleviation.service.MovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
@FxmlView("/views/MovieList.fxml")
public class MovieListController extends GenericController{

    private final MovieService movieService;

    private ObservableList<Movie> movieObservableList;

    @FXML
    public TableView<Movie> movieTable;

    @FXML
    public TableColumn<Movie, String> nameColumn;

    @FXML
    public TableColumn<Movie, String> directorColumn;

    @FXML
    public TableColumn<Movie, String> genreColumn;

    @FXML
    private void initialize() {
        movieTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        directorColumn.setCellValueFactory(dataValue -> dataValue.getValue().directorProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGenre().nameProperty());

        movieObservableList = FXCollections.observableArrayList(movieService.getAllMovies());
        movieTable.setItems(movieObservableList);
    }


    @FXML
    private void handleAddAction(ActionEvent event) {

        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        switch (buttonId) {
            case "addMovie" -> System.out.println("Add Movie");     //in the future viewControllerManager.showAddMovieDialog();
            case "addSeance" -> System.out.println("Add Seance");   //in the future viewControllerManager.showAddSeanceDialog();
            default -> System.out.println("");
        }

    }

    @Autowired
    public MovieListController(MovieService movieService){ this.movieService = movieService; }


}
