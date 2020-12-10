package agh.alleviation.controller.list;

import agh.alleviation.controller.GenericController;
import agh.alleviation.model.Hall;
import agh.alleviation.model.Seance;
import agh.alleviation.presentation.Screen;
import agh.alleviation.service.SeanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

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
        movieColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovie().nameProperty());
        hallColumn.setCellValueFactory(dataValue -> dataValue.getValue().getHall().numberProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().dateProperty());
        priceColumn.setCellValueFactory(dataValue -> dataValue.getValue().priceProperty());
        itemObservableList.addAll(service.getAllActive());
    }

    @Override
    protected void handleAddAction(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void handleEditAction(ActionEvent event) {

    }


    @Override
    protected void handleDeleteAction(ActionEvent event) {
        Seance seance = itemTable.getSelectionModel().getSelectedItem();
        if(seance != null) {
            itemObservableList.remove(seance);
            service.delete(seance);
        }
    }

    @Autowired
    public SeanceListController(SeanceService seanceService){ this.service = seanceService; }
}
