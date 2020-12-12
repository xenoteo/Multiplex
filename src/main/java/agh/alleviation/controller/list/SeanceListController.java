package agh.alleviation.controller.list;

import agh.alleviation.model.Seance;
import agh.alleviation.service.SeanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@FxmlView("/views/SeanceList.fxml")
public class SeanceListController extends GenericListController<Seance, SeanceService> {

    @FXML
    public TableColumn<Seance, String> movieColumn;

    @FXML
    public TableColumn<Seance, Number> hallColumn;

    @FXML
    public TableColumn<Seance, LocalDateTime> dateColumn;

    @FXML
    public TableColumn<Seance, Number> priceColumn;

    @FXML
    protected void initialize() {
        super.initialize();
        serviceManager.fillFromService(Seance.class);
        itemTable.setItems(serviceManager.getActiveElementsList(Seance.class));
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().priceProperty());
    }

    @Override
    protected void handleAddAction(ActionEvent event) {
        viewControllerManager.getSeanceDialogContext().showAddItemDialog();
    }

    @Override
    protected void handleEditAction(ActionEvent event) {
        Seance seance = (Seance) itemTable.getSelectionModel().getSelectedItem();
        if (seance != null) {
            viewControllerManager.getSeanceDialogContext().showEditItemDialog(seance);
        }
    }
}
