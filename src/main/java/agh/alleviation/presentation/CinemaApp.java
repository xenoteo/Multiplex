package agh.alleviation.presentation;

import agh.alleviation.AlleviationApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Class responsible for Spring Boot context creation and JavaFx application launch
 *
 * @author Kamil Krzempek
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
//        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        viewControllerManager = applicationContext.getBean(ViewControllerManager.class);
        viewControllerManager.setFxWeaver(applicationContext.getBean(FxWeaver.class));
        viewControllerManager.setPrimaryStage(primaryStage);
        viewControllerManager.initRootLayout();
        viewControllerManager.showLoginDialog();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
