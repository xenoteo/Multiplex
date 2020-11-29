package agh.alleviation.presentation;

import agh.alleviation.AlleviationApplication;
import agh.alleviation.controller.EditUserDialogController;
import agh.alleviation.controller.MainController;
import agh.alleviation.controller.UserListController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class CinemaApp extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
            .sources(AlleviationApplication.class)
            .run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Pane root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        ScreenSwitcher screenSwitcher = new ScreenSwitcher(scene);

        screenSwitcher.addScreen(Screen.MAIN, root);

        Pane userListRoot = fxWeaver.loadView(UserListController.class);
        screenSwitcher.addScreen(Screen.USER_LIST, userListRoot);

        fxWeaver.loadController(MainController.class).setScreenSwitcher(screenSwitcher);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
