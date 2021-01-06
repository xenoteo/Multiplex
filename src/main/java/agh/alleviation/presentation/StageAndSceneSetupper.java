package agh.alleviation.presentation;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Helper class responsible for setting up a stage and a scene.
 *
 * @author Ksenia Fiodarava
 * @see ItemDialogContext
 * @see AccessDialogViewer
 */
public abstract class StageAndSceneSetupper {
    /**
     * The Primary stage.
     */
    protected Stage primaryStage;

    /**
     * Instantiates a new Stage and scene setupper.
     *
     * @param primaryStage the primary stage
     */
    public StageAndSceneSetupper(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Sets stage and scene.
     *
     * @param root  the root
     * @param title the title
     * @return the stage and scene
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
}
