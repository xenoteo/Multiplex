package agh.alleviation.presentation.controller.access_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for user's registration.
 *
 * @author Ksenia Fiodarava
 * @see AccessDialogController
 */
@Component
@FxmlView("/views/RegistrationDialog.fxml")
public class RegistrationDialogController extends AccessDialogController {

    /**
     * The name text field.
     */
    @FXML
    private TextField nameField;

    /**
     * The login text field.
     */
    @FXML
    private TextField loginField;

    /**
     * The email text field.
     */
    @FXML
    private TextField emailField;

    /**
     * The password text field.
     */
    @FXML
    private PasswordField passwordField;

    @Override
    protected Validator createValidations() {
        UserService userService = (UserService) serviceManager.getService(User.class);
        Validator validator = new Validator();
        validator.createCheck()
                .withMethod(c -> {
                    String name = c.get("name");
                    String login = c.get("login");
                    String email = c.get("email");
                    if (name.isEmpty() || login.isEmpty() || email.isEmpty()){
                        c.error("All fields must be filled");
                    }
                    if (userService.findUserByEmail(email) != null){
                        c.error("User with such email already exists");
                    }
                    if (userService.findUserByLogin(login) != null){
                        c.error("User with such login already exists");
                    }
                })
                .dependsOn("name", nameField.textProperty())
                .dependsOn("login", loginField.textProperty())
                .dependsOn("email", emailField.textProperty());
        return validator;
    }

    /**
     * Handles register button and if all necessary inputs provided properly saves newly registered user to database.
     */
    @FXML
    public void register(){
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

        String name = nameField.getText();
        String login = loginField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        UserService userService = (UserService) serviceManager.getService(User.class);

        this.user = userService.addUser(name, login, email, UserType.CUSTOMER, password);
        if (user != null){
            showSuccessAlert();
            serviceManager.addToObservable(user);
        }
        dialogStage.close();
    }

    /**
     * Shows alert with information about successful registration.
     */
    private void showSuccessAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Registration completed successfully");
        alert.show();
    }
}