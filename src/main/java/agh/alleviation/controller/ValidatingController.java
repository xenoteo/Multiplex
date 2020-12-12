package agh.alleviation.controller;

import javafx.scene.control.Alert;
import net.synedra.validatorfx.Validator;

import java.util.stream.Collectors;

public abstract class ValidatingController extends GenericController{

    protected abstract Validator createValidations();

    protected String getProblemText(Validator validator) {
        return validator.validationResultProperty().get().getMessages().stream()
                .map(msg -> msg.getSeverity().toString() + ": " + msg.getText())
                .collect(Collectors.joining("\n"));
    }

    protected void showErrors(Validator validator){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(getProblemText(validator));
        alert.show();
    }

}
