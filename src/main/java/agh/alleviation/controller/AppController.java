package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.Screen;
import agh.alleviation.presentation.ScreenSwitcher;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
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
        var hallList = fxWeaver.load(HallListController.class);

        main.getController().setAppController(this);
        userList.getController().setAppController(this);
        hallList.getController().setAppController(this);

        Pane mainRoot = (Pane) main.getView().get();
        screenSwitcher.addScreen(Screen.MAIN, mainRoot);
        Pane userListRoot = (Pane) userList.getView().get();
        screenSwitcher.addScreen(Screen.USER_LIST, userListRoot);
        Pane hallListRoot = (Pane) hallList.getView().get();
        screenSwitcher.addScreen(Screen.HALL_LIST, hallListRoot);

        Scene scene = new Scene(mainRoot);
        screenSwitcher.setMainScene(scene);
        primaryStage.setScene(scene);
    }

    public void switchView(Screen screen) {
        this.screenSwitcher.activate(screen);
    }

    public Stage setupStageAndScene(Pane root, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    public User showAddUserDialog() {
        FxControllerAndView<EditUserDialogController, Pane> controllerAndView = fxWeaver.load(EditUserDialogController.class);
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), "Add user");
        EditUserDialogController controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
        return controller.getUser();
    }

    public Hall showAddHallDialog() {
        FxControllerAndView<EditHallDialogController, Pane> controllerAndView = fxWeaver.load(EditHallDialogController.class);
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), "Add hall");
        EditHallDialogController controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
        return controller.getHall();
    }
}
