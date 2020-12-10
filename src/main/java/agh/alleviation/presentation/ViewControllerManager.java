package agh.alleviation.presentation;

import agh.alleviation.controller.*;
import agh.alleviation.controller.access_dialog.LoginDialogController;
import agh.alleviation.controller.access_dialog.RegistrationDialogController;
import agh.alleviation.controller.edit_dialog.EditHallDialogController;
import agh.alleviation.controller.edit_dialog.EditMovieDialogController;
import agh.alleviation.controller.edit_dialog.EditSeanceDialogController;
import agh.alleviation.controller.edit_dialog.EditUserDialogController;
import agh.alleviation.controller.list.HallListController;
import agh.alleviation.controller.list.MovieListController;
import agh.alleviation.controller.list.SeanceListController;
import agh.alleviation.controller.list.UserListController;
import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.model.user.User;
import agh.alleviation.util.UserType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewControllerManager is responsible for setting up the controllers of the application.
 * It heavily relies on FxWeaver in order to maintain cooperation between JavaFX and Spring Boot.
 * It sets up modal views for adding user and hall.
 *
 * @author Kamil Krzempek
 * @see ScreenSwitcher
 */
public class ViewControllerManager {
    private FxWeaver fxWeaver;
    private Stage primaryStage;
    private ScreenSwitcher screenSwitcher;
    private Map<Screen, FxControllerAndView<? extends GenericController, Node>> controllersAndViews;
    private UserType activeUserType;
    private User activeUser;

    /**
     * Instantiates a new View controller manager.
     *
     * @param fxWeaver     the fx weaver
     * @param primaryStage the primary stage
     */
    public ViewControllerManager(FxWeaver fxWeaver, Stage primaryStage) {
        this.fxWeaver = fxWeaver;
        this.primaryStage = primaryStage;
        this.activeUserType = UserType.ADMIN;
    }

    /**
     * Init root layout.
     */
    public void initRootLayout() {
        this.setViewsAndControllers();

        primaryStage.show();
    }

    /**
     * Loads controllers into the FxWeaver and sets them up with corresponding views.
     */

    private FxControllerAndView<? extends GenericController, Node> addToControllersAndViews(Screen screen, Class<? extends GenericController> controller){
        var controllerAndView = fxWeaver.load(controller);
        controllersAndViews.put(screen, controllerAndView);
        return controllerAndView;
    }

    public void setViewsAndControllers() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        this.screenSwitcher = new ScreenSwitcher(borderPane);

        borderPane.setPrefHeight(400);

        controllersAndViews = new HashMap<>();

        addToControllersAndViews(Screen.MAIN, MainController.class);
        addToControllersAndViews(Screen.USER_LIST, UserListController.class);
        addToControllersAndViews(Screen.HALL_LIST, HallListController.class);
        addToControllersAndViews(Screen.MOVIE_LIST, MovieListController.class);
        addToControllersAndViews(Screen.SEANCE_LIST, SeanceListController.class);

        controllersAndViews.forEach((screen, cv) -> cv.getController().setAppController(this));

        var menuBar= fxWeaver.load(MenuController.class);
        menuBar.getController().setAppController(this);
        menuBar.getController().setActiveUserType(activeUserType);
        borderPane.setTop(menuBar.getView().get());

        controllersAndViews.forEach((screen, controllerAndView) -> {
            Pane rootPane = (Pane) controllerAndView.getView().get();
            screenSwitcher.addScreen(screen, rootPane);
        });

//        borderPane.setCenter(mainRoot);


        primaryStage.setScene(scene);
    }

    /**
     * Switch view.
     *
     * @param screen the screen
     */
    public void switchView(Screen screen) {
        this.screenSwitcher.activate(screen);
    }

    public ItemDialogContext<User, EditUserDialogController> getUserDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditUserDialogController.class));
    }

    public ItemDialogContext<Hall, EditHallDialogController> getHallDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditHallDialogController.class));
    }

    public ItemDialogContext<Movie, EditMovieDialogController> getMovieDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditMovieDialogController.class));
    }

    public ItemDialogContext<Seance, EditSeanceDialogController> getSeanceDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditSeanceDialogController.class));
    }

    /**
     * Logs out a user.
     */
    public void logout(){
        activeUser = null;
        activeUserType = null;
    }

    /**
     * Shows login dialog.
     * @return whether user was successfully logged in
     */
    public boolean showLoginDialog(){
        updateActiveUser(
                new AccessDialogViewer<>(primaryStage, fxWeaver.load(LoginDialogController.class)).showLoginDialog());
        return (activeUser != null);
    }

    /**
     * Updates active user.
     * @param user new user
     */
    private void updateActiveUser(User user){
        activeUser = user;
        activeUserType = (activeUser != null) ? activeUser.getUserType() : null;
    }

    /**
     * Shows registration dialog.
     */
    public void showRegistrationDialog(){
        new AccessDialogViewer<>(primaryStage, fxWeaver.load(RegistrationDialogController.class)).showRegisterDialog();
    }
}
