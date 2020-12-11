package agh.alleviation.presentation;

import agh.alleviation.controller.edit_dialog.EditDialogController;
import agh.alleviation.model.EntityObject;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;

public class ItemDialogContext<Item, Controller extends EditDialogController<Item>> extends StageAndSceneSetupper {
    FxControllerAndView<Controller, Pane> controllerAndView;

    public ItemDialogContext(Stage primaryStage, FxControllerAndView<Controller, Pane> controllerAndView) {
        super(primaryStage);
        this.controllerAndView = controllerAndView;
    }

    private Item showItemDialog(String title, Item item) {
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
