package agh.alleviation.presentation;

import agh.alleviation.AlleviationApplication;
import agh.alleviation.controller.AppController;
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
    private AppController appController;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder().sources(AlleviationApplication.class).run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        this.appController = new AppController(fxWeaver, primaryStage);
        this.appController.initRootLayout();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
