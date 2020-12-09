package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with hall editing (supports only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditHallDialog.fxml")
public class EditHallDialogController extends EditDialogController<Hall> {
    private final HallService hallService;

    /**
     * Instantiates a new Edit hall dialog controller.
     *
     * @param hallService the hall service
     */
    @Autowired
    public EditHallDialogController(HallService hallService) {
        this.hallService = hallService;
    }

    @FXML
    private TextField capacityField;

    @FXML
    private TextField numberField;

    @FXML
    private void addHall() {
        this.editedItem = this.hallService.addHall(Integer.parseInt(capacityField.getText()), Integer.parseInt(numberField.getText()));
        dialogStage.close();
    }
}
