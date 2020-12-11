package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with hall editing (supports only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditHallDialog.fxml")
public class EditHallDialogController extends EditDialogController<Hall> {

    /**
     * Instantiates a new Edit hall dialog controller.
     *
     * @param hallService the hall service
     */

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

        HallService service = (HallService) observableComposite.getService(Hall.class);
        if (editedItem == null) {

            Hall hall = service.addHall(capacity, hallNumber);
            observableComposite.addToObservable(hall);

        } else {
            editedItem.setCapacity(capacity);
            editedItem.setNumber(hallNumber);

            observableComposite.update(editedItem);
        }
        dialogStage.close();
    }
}
