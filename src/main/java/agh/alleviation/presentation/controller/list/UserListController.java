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
        serviceManager.fillFromService(User.class);
        itemTable.setItems(serviceManager.getActiveElementsList(User.class));
        typeColumn.setCellValueFactory(dataValue -> dataValue.getValue().userTypeProperty());
        nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().nameProperty());
        loginColumn.setCellValueFactory(dataValue -> dataValue.getValue().loginProperty());
        emailColumn.setCellValueFactory(dataValue -> dataValue.getValue().emailProperty());

    }

    /**
     * Handle add action.
     *
     * @param event the event
     */
    @FXML
    protected void handleAddAction(ActionEvent event) {
        viewControllerManager.getUserDialogContext().showAddItemDialog();
    }

    @FXML
    protected void handleEditAction(ActionEvent event) {
        User user = (User) itemTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            this.viewControllerManager.getUserDialogContext().showEditItemDialog(user);
        }
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        User user = (User) itemTable.getSelectionModel().getSelectedItem();
        serviceManager.delete(user);
    }
    
}
