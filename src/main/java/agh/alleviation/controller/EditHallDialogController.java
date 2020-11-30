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
 * @author Kamil Krzempek
 * Controller responsible for modal with hall editing (support only adding at the moment)
 */
@Component
@FxmlView("/views/EditHallDialog.fxml")
public class EditHallDialogController {
    private final HallService hallService;

    /**
     * Hall instance on which controller is operating
     */
    private Hall hall;

    /**
     * Stage on which modal is placed
     */
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

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
        this.hall = this.hallService.addHall(Integer.parseInt(capacityField.getText()), Integer.parseInt(numberField.getText()));
        dialogStage.close();
    }

    public Hall getHall() {
        return this.hall;
    }
}
