package agh.alleviation.presentation.controller;

import javafx.scene.control.Alert;
import net.synedra.validatorfx.Validator;

import java.util.stream.Collectors;

/**
 * Abstract controller using validation methods.
 *
 * @author Ksenia Fiodarava
 */
public abstract class ValidatingController extends GenericController{

    /**
     * Creates validator with validating methods.
     *
     * @return the validator
     */
    protected abstract Validator createValidations();

    /**
     * Gets text with errors from validator separating errors by a new line.
     *
     * @param validator the validator to get errors from
     * @return string representing all errors from validator
     */
    protected String getProblemText(Validator validator) {
        return validator.validationResultProperty().get().getMessages().stream()
                .map(msg -> msg.getSeverity().toString() + ": " + msg.getText())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Creates alert with errors from validator and shows it.
     *
     * @param validator the validator
     */
    protected void showErrors(Validator validator){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(getProblemText(validator));
        alert.show();
    }
}