package agh.alleviation.controller.access_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/RegistrationDialog.fxml")
public class RegistrationDialogController {

    @FXML
    private ChoiceBox<UserType> userTypeDropdown;

    @FXML
    private TextField nameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    /**
     * Stage on which modal is placed.
     */
    private Stage dialogStage;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Instance of user, where newly registered user is saved.
     */
    private User user;

    /**
     * Initializes fields of form.
     */
    @FXML
    private void initialize() {
        userTypeDropdown.getItems().addAll(UserType.values());
        userTypeDropdown.setValue(UserType.CUSTOMER);
    }

    /**
     * Instantiates a new register dialog controller.
     * @param userService the user service
     */
    @Autowired
    public RegistrationDialogController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets dialog stage.
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Gets newly registered user.
     * @return registered user
     */
    public User getUser() {
        return user;
    }

    /**
     * Handles register button and if all necessary inputs provided properly saves newly registered user to database.
     */
    @FXML
    public void register(){
        String name = nameField.getText();
        String login = loginField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || login.isEmpty() || email.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields must be filled");
            alert.show();
            return;
        }

        if (userService.getUserByLogin(login) != null || userService.getUserByEmail(email) != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided login or email is already in use");
            alert.show();
            return;
        }

        this.user = userService.addUser(name, login, email, userTypeDropdown.getValue(), password);
        if (user != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Registration completed successfully");
            alert.show();
        }
        dialogStage.close();
    }
}