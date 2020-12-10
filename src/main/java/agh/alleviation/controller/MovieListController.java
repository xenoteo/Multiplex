package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/MovieList.fxml")
public class MovieListController extends GenericController{

    @FXML
    private void handleAddAction(ActionEvent event) {
//        Hall hall = this.viewControllerManager.showAddHallDialog();
//        if(hall != null) {
//            this.hallObservableList.add(hall);
    }

}
