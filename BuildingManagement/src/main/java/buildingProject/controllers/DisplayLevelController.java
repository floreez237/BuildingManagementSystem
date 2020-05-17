/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayLevelController implements Initializable {

    @FXML
    private JFXTextField tfLevelNumber;

    @FXML
    private TableView<?> tblRooms;

    @FXML
    private TableColumn<?, ?> roomIdCol;

    @FXML
    private TableColumn<?, ?> roomTypeCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    void goBack(ActionEvent event) throws IOException {

        //Initialise with the adequate levels of the building
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane levels = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayAllLevels.fxml"));
        borderPane.setCenter(levels);

    }

    @FXML
    void onDisplay(ActionEvent event) {

        //Display a particular room depending on itsroom type display the appropriate
        //depending on the room type
        //displayBedroom,Studio and Appartment fxml respectively
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
