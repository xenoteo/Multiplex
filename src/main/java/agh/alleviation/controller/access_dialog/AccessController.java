package agh.alleviation.controller.access_dialog;

import agh.alleviation.model.user.User;
import agh.alleviation.service.UserService;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AccessController {
    /**
     * Stage on which modal is placed.
     */
    protected Stage dialogStage;

    /**
     * User service.
     */
    protected final UserService userService;

    /**
     * Instance of user, where newly logged in or registered user is saved.
     */
    protected User user;

    @Autowired
    public AccessController(UserService userService) {
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
     * Gets the user.
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
