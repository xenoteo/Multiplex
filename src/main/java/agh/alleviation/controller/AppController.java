package agh.alleviation.controller;

import agh.alleviation.model.user.User;
import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ScreenSwitcher;
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
        Pane root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        this.screenSwitcher = new ScreenSwitcher(scene);
        this.screenSwitcher.addScreen(Screen.MAIN, root);
        this.setupScreenSwitcher();

        fxWeaver.loadController(MainController.class).setAppController(this);
        fxWeaver.loadController(UserListController.class).setAppController(this);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupScreenSwitcher() {
        Pane userListRoot = fxWeaver.loadView(UserListController.class);
        screenSwitcher.addScreen(Screen.USER_LIST, userListRoot);
    }

    public void switchView(Screen screen) {
        this.screenSwitcher.activate(screen);
    }

    public User showAddUserDialog() {
        Stage dialogStage = new Stage();
        Pane userAddRoot = fxWeaver.loadView(EditUserDialogController.class);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(userAddRoot);
        dialogStage.setScene(scene);

        EditUserDialogController editUserDialogController = fxWeaver.loadController(EditUserDialogController.class);
        editUserDialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        return editUserDialogController.getUser();
    }
}
