package agh.alleviation.controller.access_dialog;

import agh.alleviation.controller.ValidatingController;
import agh.alleviation.model.user.User;
import javafx.stage.Stage;

/**
 * Abstract access controller.
 *
 * @author Ksenia Fiodarava
 * @see ValidatingController
 */
public abstract class AccessDialogController extends ValidatingController {
    /**
     * Stage on which modal is placed.
     */
    protected Stage dialogStage;
    
    /**
     * Instance of user, where newly logged in or registered user is saved.
     */
    protected User user;

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
