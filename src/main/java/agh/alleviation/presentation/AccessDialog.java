package agh.alleviation.presentation;

import agh.alleviation.controller.access_dialog.LoginDialogController;
import agh.alleviation.controller.access_dialog.RegistrationDialogController;
import agh.alleviation.model.user.User;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;

public class AccessDialog extends StageAndSceneSetupper{
    private FxWeaver fxWeaver;

    public AccessDialog(Stage primaryStage, FxWeaver fxWeaver) {
        super(primaryStage);
        this.fxWeaver = fxWeaver;
    }

    public User showLoginDialog(){
        FxControllerAndView<LoginDialogController, Pane> controllerAndView = fxWeaver.load(LoginDialogController.class);
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), "Login");
        LoginDialogController controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
        if (controller.isLoggedIn())
            return controller.getUser();
        return null;
    }

    public void showRegisterDialog() {
        FxControllerAndView<RegistrationDialogController, Pane> controllerAndView = fxWeaver.load(RegistrationDialogController.class);
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), "Registration");
        RegistrationDialogController controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
    }
}
