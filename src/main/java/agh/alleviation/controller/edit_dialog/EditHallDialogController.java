package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    @Override
    public void setEditedItem(Hall hall) {
        super.setEditedItem(hall);

        capacityField.setText(String.valueOf(hall.getCapacity()));
        numberField.setText(String.valueOf(hall.getNumber()));
    }

    @FXML
    private void saveHall() {
        int capacity = Integer.parseInt(capacityField.getText());
        int hallNumber = Integer.parseInt(numberField.getText());

        if(this.editedItem == null) {
            this.editedItem = this.hallService.addHall(capacity, hallNumber);
        } else {
            this.editedItem.setCapacity(capacity);
            this.editedItem.setNumber(hallNumber);
            this.hallService.updateHall(this.editedItem);
        }
        dialogStage.close();
    }
}
