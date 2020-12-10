package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.service.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/EditMovieDialog.fxml")
public class EditMovieDialogController extends EditDialogController<Movie> {
    private final MovieService movieService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField directorField;

    @FXML
    private TextField actorsField;

    @Autowired
    public EditMovieDialogController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void setEditedItem(Movie movie) {
        super.setEditedItem(movie);

        nameField.setText(movie.getName());
        genreField.setText(movie.getGenre().getName());
        descriptionField.setText(movie.getDescription());
        directorField.setText(movie.getDirector());
        actorsField.setText(movie.getActors());
    }

    @FXML
    private void saveMovie() {
        String name = nameField.getText();
        String genreName = genreField.getText();
        String description = descriptionField.getText();
        String director = directorField.getText();
        String actors = actorsField.getText();

        if(this.editedItem == null) {
            this.editedItem = this.movieService.addMovie(name, genreName, description, director, actors);
        } else {
            this.editedItem.setName(name);
            Genre genre = this.movieService.getGenre(genreName);
            this.editedItem.setGenre(genre);
            this.editedItem.setDescription(description);
            this.editedItem.setDirector(director);
            this.editedItem.setActors(actors);
            this.movieService.updateMovie(this.editedItem);
        }
        dialogStage.close();
    }


}
