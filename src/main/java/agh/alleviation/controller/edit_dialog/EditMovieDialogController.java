package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.service.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/EditMovieDialog.fxml")
public class EditMovieDialogController extends EditDialogController<Movie> {

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

        MovieService service = (MovieService) serviceManager.getService(Movie.class);

        if (editedItem == null) {
            Movie movie = service.addMovie(name, genreName, description, director, actors);
            serviceManager.addToObservable(movie);
        } else {
            editedItem.setName(name);
            Genre genre = service.getGenre(genreName);
            editedItem.setGenre(genre);
            editedItem.setDescription(description);
            editedItem.setDirector(director);
            editedItem.setActors(actors);
            serviceManager.update(editedItem);
        }
        dialogStage.close();
    }

}
