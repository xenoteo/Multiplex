package agh.alleviation.presentation;

import agh.alleviation.model.Movie;
import agh.alleviation.presentation.controller.stats.GenericStatsController;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;

/**
 * The type stats dialog context.
 * @param <Controller> the controller type parameter
 */
public class StatsDialogContext<Controller extends GenericStatsController<Movie>> extends StageAndSceneSetupper {
    /**
     * The Controller and view.
     */
    FxControllerAndView<Controller, Pane> controllerAndView;

    /**
     * The stage title.
     */
    String title;

    /**
     * Instantiates a new stats dialog context.
     * @param primaryStage      the primary stage
     * @param controllerAndView the controller and view
     * @param title             the stage title
     */
    public StatsDialogContext(Stage primaryStage, FxControllerAndView<Controller, Pane> controllerAndView, String title) {
        super(primaryStage);
        this.controllerAndView = controllerAndView;
        this.title = title;
    }

    /**
     * Shows stats dialog.
     */
    public void showStats(){
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), title);
        Controller controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
    }
}
