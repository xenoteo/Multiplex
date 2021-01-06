package agh.alleviation.presentation;

import agh.alleviation.presentation.controller.edit_dialog.EditDialogController;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;

/**
 * The type Item dialog context.
 *
 * @param <Item>       the type parameter
 * @param <Controller> the type parameter
 */
public class ItemDialogContext<Item, Controller extends EditDialogController<Item>> extends StageAndSceneSetupper {
    /**
     * The Controller and view.
     */
    FxControllerAndView<Controller, Pane> controllerAndView;

    /**
     * Instantiates a new Item dialog context.
     *
     * @param primaryStage      the primary stage
     * @param controllerAndView the controller and view
     */
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

    /**
     * Show add item dialog item.
     *
     * @return the item
     */
    public Item showAddItemDialog() {
        return this.showItemDialog("Add item", null);
    }

    /**
     * Show edit item dialog.
     *
     * @param item the item
     */
    public void showEditItemDialog(Item item) {
        this.showItemDialog("Edit item", item);
    }
}
