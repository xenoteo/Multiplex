package agh.alleviation.presentation.controller.edit_dialog;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.service.SeanceService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Controller responsible for modal with seance editing.
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditSeanceDialog.fxml")
public class EditSeanceDialogController extends EditDialogController<Seance> {

    /**
     * The movie choice box.
     */
    @FXML
    private ChoiceBox<Movie> movieChoiceBox;

    /**
     * The hall choice box.
     */
    @FXML
    private ChoiceBox<Hall> hallChoiceBox;

    /**
     * The date picker.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * The price field.
     */
    @FXML
    private TextField priceField;

    /**
     * Initializes the edit seance view.
     */
    @Override
    protected void initialize() {
        super.initialize();

        serviceManager
            .getActiveElementsList(Movie.class)
            .forEach(movie -> movieChoiceBox.getItems().add((Movie) movie));
        serviceManager.getActiveElementsList(Hall.class).forEach(hall -> hallChoiceBox.getItems().add((Hall) hall));
    }

    @Override
    public void setEditedItem(Seance seance) {
        super.setEditedItem(seance);

        movieChoiceBox.setValue(seance.getMovie());
        hallChoiceBox.setValue(seance.getHall());
        datePicker.setValue(seance.getDate() != null ? seance.getDate().toLocalDate() : LocalDate.now());
        priceField.setText(String.valueOf(seance.getPrice()));
    }

    @Override
    protected Validator createValidations() {
        Validator validator = new Validator();
        validator.createCheck().withMethod(c -> {
            try {
                double price = Double.parseDouble(c.get("price"));
                if (price < 0) {
                    c.error("Price must be positive");
                }
            } catch (NumberFormatException e) {
                c.error("Price must be a number");
            }
        }).dependsOn("price", priceField.textProperty());
        return validator;
    }

    /**
     * Saves the seance.
     */
    @FXML
    private void saveSeance() {
        Validator validator = createValidations();
        if (!validator.validate()) {
            showErrors(validator);
            return;
        }

        Movie movie = movieChoiceBox.getValue();
        Hall hall = hallChoiceBox.getValue();
        LocalDateTime date = datePicker.getValue().atStartOfDay();
        double price = Double.parseDouble(priceField.getText());

        SeanceService service = (SeanceService) serviceManager.getService(Seance.class);

        if (editedItem == null) {
            Seance seance = service.addSeance(movie, hall, date, price);
            serviceManager.add(seance);
        } else {
            editedItem.setMovie(movie);
            editedItem.setHall(hall);
            editedItem.setDate(date);
            editedItem.setPrice(price);
            serviceManager.update(editedItem);
        }
        dialogStage.close();
    }
}
