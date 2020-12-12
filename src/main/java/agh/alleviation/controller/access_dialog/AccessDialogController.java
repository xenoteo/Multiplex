package agh.alleviation.controller.access_dialog;

import agh.alleviation.controller.GenericController;
import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract access controller.
 *
 * @author Ksenia Fiodarava
 * @see LoginDialogController
 * @see RegistrationDialogController
 */
public abstract class AccessDialogController extends GenericController {
    /**
     * Stage on which modal is placed.
     */
    protected Stage dialogStage;
    
    /**
     * Instance of user, where newly logged in or registered user is saved.
     */
    protected User user;

    /**
     * Instantiates a new access dialog controller.
     * @param userService the user service
     */

    /**
     * Sets dialog stage.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
