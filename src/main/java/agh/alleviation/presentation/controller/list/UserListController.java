package agh.alleviation.presentation.controller.list;

import agh.alleviation.model.user.User;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for displaying list of users and control panel, which allows to manipulate them (only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/UserList.fxml")
public class UserListController extends GenericListController<User> {
    /**
     * The user type column.
     */
    @FXML
    private TableColumn<User, UserType> typeColumn;

    /**
     * The name column.
     */
    @FXML
    private TableColumn<User, String> nameColumn;

    /**
     * The login column.
     */
    @FXML
    private TableColumn<User, String> loginColumn;

    /**
     * The email column.
     */
    @FXML
    private TableColumn<User, String> emailColumn;

    /**
     * Initializes the user list view.
     */
    @FXML
    protected void initialize() {
        super.initialize();
        serviceManager.fillFromService(User.class);
        var sortedList = serviceManager.getActiveElementsList(User.class).sorted();
        sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedList);

        typeColumn.setCellValueFactory(dataValue -> dataValue.getValue().userTypeProperty());
        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        emailColumn.setCellValueFactory(dataValue -> dataValue.getValue().emailProperty());
    }

    @Override
    @FXML
    protected void handleAddAction(ActionEvent event) {
        viewControllerManager.getUserDialogContext().showAddItemDialog();
    }

    @Override
    @FXML
    protected void handleEditAction(ActionEvent event) {
        User user = (User) itemTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            viewControllerManager.getUserDialogContext().showEditItemDialog(user);
        }
    }

    @Override
    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        User user = (User) itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(user);
    }
}
