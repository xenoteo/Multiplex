package agh.alleviation.controller.edit_dialog;

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
 * Controller responsible for modal with user editing (support only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditUserDialog.fxml")
public class EditUserDialogController extends EditDialogController<User> {

    @FXML
    private ChoiceBox<UserType> userTypeDropdown;

    @FXML
    private TextField nameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField emailField;

    /**
     * Initialize fields of form
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
