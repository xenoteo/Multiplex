package agh.alleviation.controller.list;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for displaying list of users and control panel, which allows to manipulate them (only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/UserList.fxml")
public class UserListController extends GenericListController<User, UserService> {
    @FXML
    private TableColumn<User, UserType> typeColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    protected void initialize() {
        super.initialize();

        typeColumn.setCellValueFactory(dataValue -> dataValue.getValue().userTypeProperty());
        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        emailColumn.setCellValueFactory(dataValue -> dataValue.getValue().emailProperty());

        this.itemObservableList.addAll(service.getAllUsers());
    }

    /**
     * Handle add action.
     *
     * @param event the event
     */
    @FXML
    protected void handleAddAction(ActionEvent event) {
        User user = this.viewControllerManager.getUserDialogContext().showAddItemDialog();
        if (user != null) {
            this.itemObservableList.add(user);
        }
    }

    @FXML
    protected void handleEditAction(ActionEvent event) {
        User user = itemTable.getSelectionModel().getSelectedItem();
        if(user != null) {
            this.viewControllerManager.getUserDialogContext().showEditItemDialog(user);
        }
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        User user = itemTable.getSelectionModel().getSelectedItem();
        if(user != null) {
            itemObservableList.remove(user);
            service.delete(user);
        }
    }


    /**
     * Instantiates a new User list controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserListController(UserService userService) {
        this.service = userService;
    }
}
