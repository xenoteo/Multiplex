package agh.alleviation.presentation;

import agh.alleviation.controller.edit_dialog.EditDialogController;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;

public class ItemDialogContext<Item, Controller extends EditDialogController<Item>> extends StageAndSceneSetupper {
    Class<Controller> controllerClass;
    FxWeaver fxWeaver;

    public ItemDialogContext(Class<Controller> controllerClass, Stage primaryStage, FxWeaver fxWeaver) {
        super(primaryStage);
        this.controllerClass = controllerClass;
        this.primaryStage = primaryStage;
        this.fxWeaver = fxWeaver;
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
