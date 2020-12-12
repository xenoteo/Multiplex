package agh.alleviation.controller.access_dialog;

import agh.alleviation.controller.ActiveUser;
import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import agh.alleviation.util.UserType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for user's login.
 *
 * @author Ksenia Fiodarava
 */
@Component
@FxmlView("/views/LoginDialog.fxml")
public class LoginDialogController extends AccessDialogController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private ActiveUser activeUser;


    /**
     * Boolean variable indicating whether login operation was successful.
     */
    private boolean loggedIn;


    /**
     * Gets info about login operation's success.
     * @return is user logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Handles login button and if data provided properly sets the user logged in.
     */
    @FXML
    public void login() {
        String login = loginField.getText();
        String password = passwordField.getText();
        UserService userService = (UserService) observableComposite.getService(User.class);
        if (!userService.validateUser(login, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid login or password");
            alert.show();
        }
        loggedIn = true;
        dialogStage.close();
        activeUser.setUserEntity(userService.getUserByLogin(login));

    }

    @Autowired
    public void setActiveUser(ActiveUser activeUser){
        this.activeUser = activeUser;
    }
}