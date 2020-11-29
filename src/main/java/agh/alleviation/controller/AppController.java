package agh.alleviation.controller;

import agh.alleviation.model.Movie;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ScreenSwitcher;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

public class AppController {
    private FxWeaver fxWeaver;
    private Stage primaryStage;
    private ScreenSwitcher screenSwitcher;

    public AppController(FxWeaver fxWeaver, Stage primaryStage) {
        this.fxWeaver = fxWeaver;
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        this.setViewsAndControllers();

        primaryStage.show();
    }

    public void setViewsAndControllers() {
        this.screenSwitcher = new ScreenSwitcher();

        var main = fxWeaver.load(MainController.class);
        var userList = fxWeaver.load(UserListController.class);

        main.getController().setAppController(this);
        userList.getController().setAppController(this);

        Pane mainRoot = (Pane) main.getView().get();
        screenSwitcher.addScreen(Screen.MAIN, mainRoot);
        Pane userListRoot = (Pane) userList.getView().get();
        screenSwitcher.addScreen(Screen.USER_LIST, userListRoot);

        Scene scene = new Scene(mainRoot);
        screenSwitcher.setMainScene(scene);
        primaryStage.setScene(scene);
    }

    public void switchView(Screen screen) {
        this.screenSwitcher.activate(screen);
    }

    public User showAddUserDialog() {
        Stage dialogStage = new Stage();
        var controllerAndView = fxWeaver.load(EditUserDialogController.class);
        Pane userAddRoot = (Pane) controllerAndView.getView().get();
        dialogStage.setTitle("Add user");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(userAddRoot);
        dialogStage.setScene(scene);

        EditUserDialogController controller = controllerAndView.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
        return controller.getUser();
    }
}
