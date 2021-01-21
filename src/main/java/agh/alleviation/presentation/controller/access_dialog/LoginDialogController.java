package agh.alleviation.presentation.controller.access_dialog;

import agh.alleviation.presentation.context.ActiveUser;
import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for user's login.
 *
 * @author Ksenia Fiodarava
 * @see AccessDialogController
 */
@Component
@FxmlView("/views/LoginDialog.fxml")
public class LoginDialogController extends AccessDialogController {

    /**
     * The login text field.
     */
    @FXML
    private TextField loginField;

    /**
     * The password text field.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * An active user.
     */
    private ActiveUser activeUser;

    /**
     * Boolean variable indicating whether login operation was successful.
     */
    private boolean loggedIn;

    /**
     * Gets info about login operation's success.
     *
     * @return is user logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    protected Validator createValidations() {
        UserService userService = (UserService) serviceManager.getService(User.class);
        Validator validator = new Validator();
        validator.createCheck()
                .withMethod(c -> {
                    String login = c.get("login");
                    String password = c.get("password");
                    if (!userService.validateUser(login, password)){
                        c.error("Invalid login or password");
                    }
                })
                .dependsOn("login", loginField.textProperty())
                .dependsOn("password", passwordField.textProperty());
        return validator;
    }

    /**
     * Handles login button click and if data provided properly sets the user logged in.
     */
    @FXML
    public void login() {
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

        String login = loginField.getText();
        String password = passwordField.getText();
        UserService userService = (UserService) serviceManager.getService(User.class);
        if (!userService.validateUser(login, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid login or password");
            alert.show();
            return;
        }
        loggedIn = true;
        dialogStage.close();
        activeUser.setUserEntity(userService.findUserByLogin(login));
    }

    /**
     * Handles register button click.
     */
    @FXML
    public void register(){
        viewControllerManager.showRegistrationDialog();
    }

    /**
     * Sets an active user.
     *
     * @param activeUser the active user
     */
    @Autowired
    public void setActiveUser(ActiveUser activeUser){
        this.activeUser = activeUser;
    }

    @Override
    public User getUser() {
        return activeUser.getUserEntity();
    }
}