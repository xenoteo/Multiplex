package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.service.HallService;
import agh.alleviation.service.SeanceService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateStringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@FxmlView("/views/EditSeanceDialog.fxml")
public class EditSeanceDialogController extends EditDialogController<Seance> {
    private final SeanceService seanceService;
    private final HallService hallService;

    @FXML
    private ChoiceBox<Hall> hallChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField priceField;

    @Autowired
    public EditSeanceDialogController(SeanceService seanceService, HallService hallService) {
        this.seanceService = seanceService;
        this.hallService = hallService;
    }

    @Override
    protected void initialize() {
        super.initialize();

        observableComposite.getList(Hall.class).forEach( hall ->
                hallChoiceBox.getItems().add((Hall) hall)
        );

    }

    @Override
    public void setEditedItem(Seance seance) {
        super.setEditedItem(seance);

        hallChoiceBox.setValue(seance.getHall());
        datePicker.setValue(seance.getDate() != null ? seance.getDate().toLocalDate() : LocalDate.now());
        priceField.setText(String.valueOf(seance.getPrice()));
    }


    @FXML
    private void saveSeance() {
        Hall hall = hallChoiceBox.getValue();
        LocalDateTime date = datePicker.getValue().atStartOfDay();
        double price = Double.parseDouble(priceField.getText());

        editedItem.setHall(hall);
        editedItem.setDate(date);
        editedItem.setPrice(price);

        observableComposite.update(editedItem);
        dialogStage.close();
    }
}
