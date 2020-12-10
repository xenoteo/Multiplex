package agh.alleviation.controller;

import agh.alleviation.model.Hall;
import agh.alleviation.presentation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
@FxmlView("/views/MovieList.fxml")
public class MovieListController extends GenericController{

    @FXML
    private void handleAddAction(ActionEvent event) {

        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        switch (buttonId) {
            case "addMovie" -> System.out.println("Add Movie");     //in the future viewControllerManager.showAddMovieDialog();
            case "addSeance" -> System.out.println("Add Seance");   //in the future viewControllerManager.showAddSeanceDialog();
            default -> System.out.println("");
        }

//        Hall hall = this.viewControllerManager.showAddHallDialog();
//        if(hall != null) {
//            this.hallObservableList.add(hall);
    }

}
