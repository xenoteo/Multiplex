package agh.alleviation.presentation;

import agh.alleviation.AlleviationApplication;
import agh.alleviation.controller.ViewControllerSetup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class CinemaApp extends Application {
    private ConfigurableApplicationContext applicationContext;
    private ViewControllerSetup viewControllerSetup;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder().sources(AlleviationApplication.class).run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        this.viewControllerSetup = new ViewControllerSetup(fxWeaver, primaryStage);
        this.viewControllerSetup.initRootLayout();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
