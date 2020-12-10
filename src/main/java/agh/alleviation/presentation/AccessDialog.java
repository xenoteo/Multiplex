package agh.alleviation.presentation;

import agh.alleviation.controller.access_dialog.AccessController;
import agh.alleviation.controller.access_dialog.LoginDialogController;
import agh.alleviation.model.user.User;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;

public class AccessDialog<Controller extends AccessController> extends StageAndSceneSetupper{
    FxControllerAndView<Controller, Pane> controllerAndView;
    Controller controller;

    public AccessDialog(Stage primaryStage, FxControllerAndView<Controller, Pane> controllerAndView) {
        super(primaryStage);
        this.controllerAndView = controllerAndView;
    }

    private void showDialog(String title){
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), title);
        controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
    }

    public User showLoginDialog(){
        showDialog("Login");
        if (((LoginDialogController)controller).isLoggedIn())
            return controller.getUser();
        return null;
    }

    public void showRegisterDialog() {
        showDialog("Registration");
    }
}
