package agh.alleviation.presentation;

import agh.alleviation.controller.edit_dialog.EditDialogController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;

public class ItemDialogContext<Item, Controller extends EditDialogController<Item>> {
    Class<Controller> controllerClass;
    Stage primaryStage;
    FxWeaver fxWeaver;

    public ItemDialogContext(Class<Controller> controllerClass, Stage primaryStage, FxWeaver fxWeaver) {
        this.controllerClass = controllerClass;
        this.primaryStage = primaryStage;
        this.fxWeaver = fxWeaver;
    }

    private Stage setupStageAndScene(Pane root, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private Item showItemDialog(String title, Item item) {
        FxControllerAndView<Controller, Pane> controllerAndView = fxWeaver.load(controllerClass);
        Stage stage = setupStageAndScene(controllerAndView.getView().get(), title);
        Controller controller = controllerAndView.getController();
        controller.setDialogStage(stage);
        if(item != null) controller.setEditedItem(item);
        stage.showAndWait();
        return controller.getEditedItem();
    }

    public Item showAddItemDialog() {
        return this.showItemDialog("Add item", null);
    }

    public void showEditItemDialog(Item item) {
        this.showItemDialog("Edit item", item);
    }
}
