package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for modal with user editing (support only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/EditUserDialog.fxml")
public class EditUserDialogController extends EditDialogController<User> {
    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * Instantiates a new Edit user dialog controller.
     *
     * @param userService the user service
     */
    @Autowired
    public EditUserDialogController(UserService userService) {
        this.userService = userService;
    }

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

    @FXML
    private void saveUser() {
        String name = nameField.getText();
        String login = loginField.getText();
        String email = emailField.getText();
        UserType userType = userTypeDropdown.getValue();

        UserService userService = (UserService) observableComposite.getService(User.class);

        if(editedItem == null) {
            editedItem = userService.addUser(name, login, email, userType);
            observableComposite.addToObservable(editedItem);
        } else {
            editedItem.setName(name);
            editedItem.setLogin(login);
            editedItem.setEmail(email);
            editedItem.setUserType(userType);
            observableComposite.update(editedItem);
        }
        dialogStage.close();
    }
}
