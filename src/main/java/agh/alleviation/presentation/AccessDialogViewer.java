package agh.alleviation.presentation;

import agh.alleviation.presentation.controller.access_dialog.AccessDialogController;
import agh.alleviation.presentation.controller.access_dialog.LoginDialogController;
import agh.alleviation.model.user.User;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;

/**
 * Class responsible for showing access dialogs.
 *
 * @param <Controller> the type parameter
 * @author Ksenia Fiodarava
 * @see AccessDialogController
 */
public class AccessDialogViewer<Controller extends AccessDialogController> extends StageAndSceneSetupper{
    /**
     * The Controller and view.
     */
    FxControllerAndView<Controller, Pane> controllerAndView;
    /**
     * The Controller.
     */
    Controller controller;

    /**
     * Instantiates a new Access dialog viewer.
     *
     * @param primaryStage      the primary stage
     * @param controllerAndView the controller and view
     */
    public AccessDialogViewer(Stage primaryStage, FxControllerAndView<Controller, Pane> controllerAndView) {
        super(primaryStage);
        this.controllerAndView = controllerAndView;
    }

    /**
     * Shows a dialog with provided title.
     * @param title the title
     */
    private void showDialog(String title){
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), title);
        controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
    }

    /**
     * Shows login dialog.
     *
     * @return whether user was successfully logged in
     */
    public User showLoginDialog(){
        showDialog("Login");
        if (((LoginDialogController)controller).isLoggedIn())
            return controller.getUser();
        return null;
    }

    /**
     * Shows registration dialog.
     */
    public void showRegisterDialog() {
        showDialog("Registration");
    }
}
