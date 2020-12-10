package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.service.HallService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller responsible for displaying list of halls and control panel,
 * which allows to manipulate them (only adding at the moment)
 *
 * @author Kamil Krzempek
 */
@Component
@FxmlView("/views/HallList.fxml")
public class HallListController extends GenericListController<Hall, HallService> {
    @FXML
    private TableColumn<Hall, Integer> numberColumn;

    @FXML
    private TableColumn<Hall, Integer> capacityColumn;

    @FXML
    protected void initialize() {
        super.initialize();

        numberColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberProperty().asObject());
        capacityColumn.setCellValueFactory(dataValue -> dataValue.getValue().capacityProperty().asObject());

        this.itemObservableList.addAll(service.getAllHalls());
    }

    @FXML
    protected void handleAddAction(ActionEvent event) {
        Hall hall = this.viewControllerManager.getHallDialogContext().showAddItemDialog();
        if(hall != null) {
            itemObservableList.add(hall);
        }
    }

    @FXML
    protected void handleEditAction(ActionEvent event) {
        Hall hall = itemTable.getSelectionModel().getSelectedItem();
        if(hall != null) {
            this.viewControllerManager.getHallDialogContext().showEditItemDialog(hall);
        }
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
        Hall hall = itemTable.getSelectionModel().getSelectedItem();
        if(hall != null) {
            itemObservableList.remove(hall);
        }
    }

    /**
     * Instantiates a new Hall list controller.
     *
     * @param hallService the hall service
     */
    @Autowired
    public HallListController(HallService hallService) {
        this.service = hallService;
    }
}
