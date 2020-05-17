/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
public class DisplayAllLevelsController implements Initializable {

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<?> tblLevels;

    @FXML
    private TableColumn<?, ?> levelNumberCol;

    @FXML
    private TableColumn<?, ?> totalRoomCol;

    @FXML
    private TableColumn<?, ?> occupiedRoomsCol;

    @FXML
    private TableColumn<?, ?> freeRoomsCol;


    @FXML
    void goBack(ActionEvent event) throws IOException {

        //Initialise the table with the appropriate levels of the building
        //Or just keep track of the previous page all the times
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane levels = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayBuilding.fxml"));
        borderPane.setCenter(levels);
    }

    @FXML
    void onDisplay(ActionEvent event) throws IOException {

        // //Initialise the table with the appropriate level information
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane level = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayLevel.fxml"));
        borderPane.setCenter(level);
    }

    @FXML
    void onSearch(ActionEvent event) {

        //Search for a level in the table
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
