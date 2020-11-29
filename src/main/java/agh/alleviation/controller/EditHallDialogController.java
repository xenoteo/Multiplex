package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.model.user.User;
import agh.alleviation.service.HallService;
import agh.alleviation.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/EditHallDialog.fxml")
public class EditHallDialogController {
    HallService hallService;

    Hall hall;

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
    private void addHall() {
        this.hall = this.hallService.addHall(Integer.parseInt(capacityField.getText()));
        dialogStage.close();
    }

    public Hall getHall() {
        return this.hall;
    }
}
