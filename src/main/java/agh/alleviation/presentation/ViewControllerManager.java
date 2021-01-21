package agh.alleviation.presentation;

import agh.alleviation.model.Order;
import agh.alleviation.presentation.context.ActiveUser;
import agh.alleviation.presentation.controller.*;
import agh.alleviation.presentation.controller.access_dialog.LoginDialogController;
import agh.alleviation.presentation.controller.access_dialog.RegistrationDialogController;
import agh.alleviation.presentation.controller.edit_dialog.*;
import agh.alleviation.presentation.controller.list.HallListController;
import agh.alleviation.presentation.controller.list.MovieListController;
import agh.alleviation.presentation.controller.list.SeanceListController;
import agh.alleviation.presentation.controller.list.UserListController;
import agh.alleviation.model.Hall;
import agh.alleviation.model.Movie;
import agh.alleviation.model.Seance;
import agh.alleviation.model.user.User;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
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
@Component
public class ViewControllerManager {
    private FxWeaver fxWeaver;
    private Stage primaryStage;
    private ScreenSwitcher screenSwitcher;
    private Map<Screen, FxControllerAndView<? extends GenericController, Node>> controllersAndViews;
    private ActiveUser activeUser;

    /**
     * Instantiates a new View controller manager.
     */
    public ViewControllerManager() {
    }

    /**
     * Sets the fx weaver.
     *
     * @param weaver the weaver
     */
    public void setFxWeaver(FxWeaver weaver) {
        fxWeaver = weaver;
    }

    /**
     * Sets the primary stage.
     *
     * @param stage the stage
     */
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Inits the root layout.
     */
    public void initRootLayout() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        screenSwitcher = new ScreenSwitcher(borderPane);

        borderPane.setPrefHeight(400);

        controllersAndViews = new HashMap<>();

        addToControllersAndViews(Screen.MAIN, MainController.class);
        addToControllersAndViews(Screen.USER_LIST, UserListController.class);
        addToControllersAndViews(Screen.HALL_LIST, HallListController.class);
        addToControllersAndViews(Screen.MOVIE_LIST, MovieListController.class);
        addToControllersAndViews(Screen.SEANCE_LIST, SeanceListController.class);
        addToControllersAndViews(Screen.TICKET_LIST, BasketController.class);
        addToControllersAndViews(Screen.ORDER_LIST, OrdersHistoryController.class);
        addToControllersAndViews(Screen.STATISTICS, StatisticsController.class);

        controllersAndViews.forEach((screen, cv) -> cv.getController().setAppController(this));

        var menuBar = fxWeaver.load(MenuController.class);
        menuBar.getController().setAppController(this);

        activeUser.addUserChangeListener(menuBar.getController());
        activeUser.addUserChangeListener((PropertyChangeListener) controllersAndViews
            .get(Screen.SEANCE_LIST)
            .getController());
        activeUser.addUserChangeListener((PropertyChangeListener) controllersAndViews
            .get(Screen.TICKET_LIST)
            .getController());
        activeUser.addUserChangeListener((PropertyChangeListener) controllersAndViews
            .get(Screen.ORDER_LIST)
            .getController());

        borderPane.setTop(menuBar.getView().get());

        controllersAndViews.forEach((screen, controllerAndView) -> {
            Pane rootPane = (Pane) controllerAndView.getView().get();
            screenSwitcher.addScreen(screen, rootPane);
        });

        var login = fxWeaver.load(LoginDialogController.class);
        login.getController().setAppController(this);

        var register = fxWeaver.load(RegistrationDialogController.class);
        register.getController().setAppController(this);

        primaryStage.setScene(scene);
    }

    /**
     * Shows the primary stage.
     */
    public void showPrimaryStage() {
        primaryStage.show();
        screenSwitcher.activate(Screen.MAIN);
    }

    /**
     * Hides the primary stage.
     */
    public void hidePrimaryStage() {
        primaryStage.close();
    }

    /**
     * Loads controllers into the FxWeaver and sets them up with corresponding views.
     */
    private FxControllerAndView<? extends GenericController, Node> addToControllersAndViews(
        Screen screen, Class<? extends GenericController> controller) {
        var controllerAndView = fxWeaver.load(controller);
        controllersAndViews.put(screen, controllerAndView);
        return controllerAndView;
    }

    /**
     * Switches the view.
     *
     * @param screen the screen
     */
    public void switchView(Screen screen) {
        screenSwitcher.activate(screen);
    }

    /**
     * Gets user dialog context.
     *
     * @return the user dialog context
     */
    public ItemDialogContext<User, EditUserDialogController> getUserDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditUserDialogController.class));
    }

    /**
     * Gets hall dialog context.
     *
     * @return the hall dialog context
     */
    public ItemDialogContext<Hall, EditHallDialogController> getHallDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditHallDialogController.class));
    }

    /**
     * Gets movie dialog context.
     *
     * @return the movie dialog context
     */
    public ItemDialogContext<Movie, EditMovieDialogController> getMovieDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditMovieDialogController.class));
    }

    /**
     * Gets seance dialog context.
     *
     * @return the seance dialog context
     */
    public ItemDialogContext<Seance, EditSeanceDialogController> getSeanceDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(EditSeanceDialogController.class));
    }

    /**
     * Gets order dialog context.
     *
     * @return the order dialog context
     */
    public ItemDialogContext<Order, OrderDetailsDialogController> getOrderDialogContext() {
        return new ItemDialogContext<>(primaryStage, fxWeaver.load(OrderDetailsDialogController.class));
    }

    /**
     * Logs out a user.
     */
    public void logout() {
        switchView(Screen.MAIN);
        activeUser.setUserEntity(null);
        hidePrimaryStage();
        showLoginDialog();
    }

    /**
     * Shows login dialog.
     *
     * @return whether user was successfully logged in
     */
    public boolean showLoginDialog() {
        User user =
            new AccessDialogViewer<>(primaryStage, fxWeaver.load(LoginDialogController.class)).showLoginDialog();
        if (user != null) {
            updateActiveUser(user);
            showPrimaryStage();
        }
        return user != null;
    }

    /**
     * Updates the active user.
     *
     * @param user new user
     */
    private void updateActiveUser(User user) {
        activeUser.setUserEntity(user);
    }

    /**
     * Shows registration dialog.
     */
    public void showRegistrationDialog() {
        new AccessDialogViewer<>(primaryStage, fxWeaver.load(RegistrationDialogController.class)).showRegisterDialog();
    }

    /**
     * Sets the active user.
     *
     * @param activeUser the active user
     */
    @Autowired
    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

}
