package agh.alleviation.controller.access_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for user's login.
 *
 * @see AccessDialogController
 * @author Ksenia Fiodarava
 */
@Component
@FxmlView("/views/LoginDialog.fxml")
public class LoginDialogController extends AccessDialogController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

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
     * Handles login button and if data provided properly sets the user logged in.
     */
    @FXML
    public void login() {
        Validator validator = createValidations();
        if (!validator.validate()){
            showErrors(validator);
            return;
        }

        UserService userService = (UserService) serviceManager.getService(User.class);
        user = userService.findUserByLogin(loginField.getText());
        loggedIn = true;
        dialogStage.close();
    }
}