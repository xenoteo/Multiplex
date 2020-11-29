package agh.alleviation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/EditUserDialog.fxml")
public class EditUserDialogController {
    @FXML
    private ChoiceBox<UserType> userTypeDropdown;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button addUserButton;

    @FXML
    private ListView<Object> userListView;

    @FXML
    private void initialize() {
        userTypeDropdown.getItems().addAll(UserType.values());
    }

    @FXML
    private void addUser() {
        System.out.println(userTypeDropdown.getValue());
        System.out.println(nameField.getText());
        System.out.println(emailField.getText());
    }
}
