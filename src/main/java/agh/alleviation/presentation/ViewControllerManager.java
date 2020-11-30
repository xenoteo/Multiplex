package agh.alleviation.presentation;

import agh.alleviation.controller.*;
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

/**
 * @author Kamil Krzempek
 * ViewControllerManager is responsible for setting up the controllers of the application.
 * It heavily relies on FxWeaver in order to maintain cooperation between JavaFX and Spring Boot.
 * It sets up modal views for adding user and hall.
 * @see ScreenSwitcher
 */
public class ViewControllerManager {
    private FxWeaver fxWeaver;
    private Stage primaryStage;
    private ScreenSwitcher screenSwitcher;

    public ViewControllerManager(FxWeaver fxWeaver, Stage primaryStage) {
        this.fxWeaver = fxWeaver;
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        this.setViewsAndControllers();

        primaryStage.show();
    }

    /**
     * Loads controllers into the FxWeaver and sets them up with corresponding views.
     */
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

    /**
     * A helper function for showAddUserDialog and showAddDialog.
     * @param root  root pane
     * @param title title of the window
     * @return a stage for the window
     */
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
