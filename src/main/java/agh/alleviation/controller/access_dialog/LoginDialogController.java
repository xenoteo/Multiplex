package agh.alleviation.controller.access_dialog;

import agh.alleviation.controller.GenericController;
import agh.alleviation.model.user.User;
import agh.alleviation.model.user.UserDTO;
import agh.alleviation.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
public class LoginDialogController extends GenericController {

    @FXML
    private TextField loginField;

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
     * Instance of user, where newly logged in user is saved.
     */
    private User user;

    /**
     * Boolean variable indicating whether login operation was successful.
     */
    private boolean loggedIn;

    /**
     * Instantiates a new login dialog controller.
     * @param userService the user service
     */
    @Autowired
    public LoginDialogController(UserService userService) {
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
     * Gets newly logged in user.
     * @return logged in user
     */
    public User getUser() {
        return user;
    }

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
        UserDTO userDTO = new UserDTO(loginField.getText(), passwordField.getText());
        if (!userService.validateUser(userDTO.getLogin(), userDTO.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid login or password");
            alert.show();
            return;
        }
        user = userService.getUserByLogin(userDTO.getLogin());
        loggedIn = true;
        dialogStage.close();
    }
}