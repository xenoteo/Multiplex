package agh.alleviation.presentation.controller.edit_dialog;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with hall editing
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditHallDialog.fxml")
public class EditHallDialogController extends EditDialogController<Hall> {

    /**
     * The capacity field.
     */
    @FXML
    private TextField capacityField;

    /**
     * The number field.
     */
    @FXML
    private TextField numberField;

    @Override
    public void setEditedItem(Hall hall) {
        super.setEditedItem(hall);

        capacityField.setText(String.valueOf(hall.getCapacity()));
        numberField.setText(String.valueOf(hall.getNumber()));
    }

    @Override
    protected Validator createValidations() {
        HallService service = (HallService) serviceManager.getService(Hall.class);
        Validator validator = new Validator();
        validator.createCheck()
                .withMethod(
                        c -> {
                            try{
                                int capacity = Integer.parseInt(c.get("capacity"));
                                int hallNumber = Integer.parseInt(c.get("hallNumber"));
                                if (capacity < 1 || hallNumber < 1){
                                    c.error("Input data must be positive");
                                }
                                if ((editedItem == null || editedItem.getNumber() != hallNumber) && service.findHallByNumber(hallNumber) != null){
                                    c.error("Hall with such number already exists");
                                }
                            }
                            catch (NumberFormatException e){
                                c.error("Input data must be numeric");
                            }
                        }
                )
                .dependsOn("capacity", capacityField.textProperty())
                .dependsOn("hallNumber", numberField.textProperty());
        return validator;
    }

    /**
     * Saves the hall.
     */
    @FXML
    private void saveHall() {
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

        HallService service = (HallService) serviceManager.getService(Hall.class);

        int capacity = Integer.parseInt(capacityField.getText());
        int hallNumber = Integer.parseInt(numberField.getText());

        if (editedItem == null) {

            Hall hall = service.addHall(capacity, hallNumber);
            serviceManager.addToObservable(hall);

        } else {
            editedItem.setCapacity(capacity);
            editedItem.setNumber(hallNumber);

            serviceManager.update(editedItem);
        }
        dialogStage.close();
    }
}
