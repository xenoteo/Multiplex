package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Movie;
import agh.alleviation.model.user.User;
import agh.alleviation.service.MovieService;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
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
    }

    @FXML
    private void saveMovie() {
        String name = nameField.getText();
        String genre = genreField.getText();
        String description = descriptionField.getText();
        String director = directorField.getText();
        String actors = actorsField.getText();

        if(this.editedItem == null) {
            this.editedItem = this.movieService.addMovie(name, genre);
        } else {
            this.editedItem.setName(name);
            this.movieService.updateMovie(this.editedItem);
        }
        dialogStage.close();
    }


}
