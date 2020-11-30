package agh.alleviation.presentation;

import agh.alleviation.AlleviationApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Kamil Krzempek
 * Class responsible for Spring Boot context creation and JavaFx application launch
 */
public class CinemaApp extends Application {
    private ConfigurableApplicationContext applicationContext;
    private ViewControllerManager viewControllerManager;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder().sources(AlleviationApplication.class).run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        this.viewControllerManager = new ViewControllerManager(fxWeaver, primaryStage);
        this.viewControllerManager.initRootLayout();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
