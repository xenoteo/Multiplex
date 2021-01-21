package agh.alleviation.presentation.controller.edit_dialog;

import agh.alleviation.model.Genre;
import agh.alleviation.model.Movie;
import agh.alleviation.service.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with movie editing
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditMovieDialog.fxml")
public class EditMovieDialogController extends EditDialogController<Movie> {

    /**
     * The name field.
     */
    @FXML
    private TextField nameField;

    /**
     * The genre field.
     */
    @FXML
    private TextField genreField;

    /**
     * The description field.
     */
    @FXML
    private TextField descriptionField;

    /**
     * The director field.
     */
    @FXML
    private TextField directorField;

    /**
     * THe actors field.
     */
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

    @Override
    protected Validator createValidations() {
        Validator validator = new Validator();
        validator.createCheck()
                .withMethod(c -> {
                    String name = c.get("title");
                    String genreName = c.get("genre");
                    String description = c.get("description");
                    String director = c.get("director");
                    String actors = c.get("actors");
                    if (name.isEmpty() || genreName.isEmpty() || description.isEmpty() || director.isEmpty() || actors.isEmpty()){
                        c.error("All fields must be filled");
                    }
                })
                .dependsOn("title", nameField.textProperty())
                .dependsOn("genre", genreField.textProperty())
                .dependsOn("description", descriptionField.textProperty())
                .dependsOn("director", directorField.textProperty())
                .dependsOn("actors", actorsField.textProperty());
        return validator;
    }

    /**
     * Saves the movie.
     */
    @FXML
    private void saveMovie() {
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

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
