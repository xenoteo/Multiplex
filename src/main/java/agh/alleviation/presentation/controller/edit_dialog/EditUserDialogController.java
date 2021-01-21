package agh.alleviation.presentation.controller.edit_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with user editing
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditUserDialog.fxml")
public class EditUserDialogController extends EditDialogController<User> {

    /**
     * The user type dropdown.
     */
    @FXML
    private ChoiceBox<UserType> userTypeDropdown;

    /**
     * The name field.
     */
    @FXML
    private TextField nameField;

    /**
     * The login field.
     */
    @FXML
    private TextField loginField;

    /**
     * The email field.
     */
    @FXML
    private TextField emailField;

    /**
     * Initializes fields of form
     */
    @FXML
    protected void initialize() {
        super.initialize();
        userTypeDropdown.getItems().addAll(UserType.values());
        userTypeDropdown.setValue(UserType.CUSTOMER);
    }

    @Override
    public void setEditedItem(User user) {
        super.setEditedItem(user);

        nameField.setText(user.getName());
        loginField.setText(user.getLogin());
        emailField.setText(user.getEmail());
        userTypeDropdown.setValue(user.userTypeProperty().getValue());
    }

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
                    if ((editedItem == null || !editedItem.getEmail().equals(email)) && userService.findUserByEmail(email) != null){
                        c.error("User with such email already exists");
                    }
                    if ((editedItem == null || !editedItem.getLogin().equals(login)) && userService.findUserByLogin(login) != null){
                        c.error("User with such login already exists");
                    }
                })
                .dependsOn("name", nameField.textProperty())
                .dependsOn("login", loginField.textProperty())
                .dependsOn("email", emailField.textProperty());
        return validator;
    }

    /**
     * Saves the user.
     */
    @FXML
    private void saveUser() {
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

        String name = nameField.getText();
        String login = loginField.getText();
        String email = emailField.getText();
        UserType userType = userTypeDropdown.getValue();

        UserService userService = (UserService) serviceManager.getService(User.class);

        if (editedItem == null) {
            editedItem = userService.addUser(name, login, email, userType);
            serviceManager.addToObservable(editedItem);
        } else {
            editedItem.setName(name);
            editedItem.setLogin(login);
            editedItem.setEmail(email);
            editedItem.setUserType(userType);
            serviceManager.update(editedItem);
        }
        dialogStage.close();
    }
}
