package agh.alleviation.presentation.controller.list;

import agh.alleviation.model.Hall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for displaying list of halls and control panel,
 * which allows to manipulate them (only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/HallList.fxml")
public class HallListController extends GenericListController<Hall> {
    @FXML
    private TableColumn<Hall, Integer> numberColumn;

    @FXML
    private TableColumn<Hall, Integer> capacityColumn;

    @FXML
    protected void initialize() {
        super.initialize();

        serviceManager.fillFromService(Hall.class);
        var sortedList = serviceManager.getActiveElementsList(Hall.class).sorted();
        sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
        itemTable.setItems(sortedList);

        numberColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberProperty().asObject());
        capacityColumn.setCellValueFactory(dataValue -> dataValue.getValue().capacityProperty().asObject());
    }

    @FXML
    protected void handleAddAction(ActionEvent event) {
        viewControllerManager.getHallDialogContext().showAddItemDialog();
    }

    @FXML
    protected void handleEditAction(ActionEvent event) {
        Hall hall = (Hall) itemTable.getSelectionModel().getSelectedItem();
        if (hall != null) {
            viewControllerManager.getHallDialogContext().showEditItemDialog(hall);
        }
    }
}
