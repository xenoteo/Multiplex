package agh.alleviation.controller;

import agh.alleviation.model.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/UserList.fxml")
public class UserListController {
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, UserType> typeColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

//        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
//        emailColumn.setCellValueFactory(dataValue -> dataValue.getValue().emailProperty());
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
    }
}
