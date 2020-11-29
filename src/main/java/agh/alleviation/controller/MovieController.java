package agh.alleviation.controller;

import agh.alleviation.model.Movie;
import agh.alleviation.service.MovieService;
import io.reactivex.rxjava3.core.Single;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("/MovieController.fxml")
public class MovieController {
    MovieService movieService;

    @FXML
    private TextField movieNameField;

    @FXML
    private ListView<String> movieListView;

    @FXML
    private void addMovie() {
        String name = movieNameField.getText();
        movieService.addMovie(name);
    }

    @FXML
    private void fetchMovies() {
        ObservableList<String> movieNamesList = movieService
                .getAllMovies().stream()
                .map(Movie::getName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        movieListView.itemsProperty().setValue(movieNamesList);
    }

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
}
