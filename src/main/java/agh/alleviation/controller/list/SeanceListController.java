package agh.alleviation.controller.list;

import agh.alleviation.controller.GenericController;
import agh.alleviation.model.Seance;
import agh.alleviation.service.SeanceService;
import javafx.event.ActionEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/SeanceList.fxml")
public class SeanceListController extends GenericListController<Seance, SeanceService> {
    @Override
    protected void handleAddAction(ActionEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void handleEditAction(ActionEvent event) {

    }

    @Override
    protected void handleDeleteAction(ActionEvent event) {

    }
}
