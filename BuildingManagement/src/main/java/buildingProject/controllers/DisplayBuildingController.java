/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
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
public class DisplayBuildingController implements Initializable {

    @FXML
    private JFXTextField tfBuildingId;

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField tfName;

    @FXML
    private JFXTextField tfLocation;

    @FXML
    private JFXTextField tfNumberOfLevels;

    @FXML
    private JFXTextField tfFreeRooms;

    @FXML
    private JFXTextField tfOccupiedRooms;

    @FXML
    private TitledPane tpFurnitures;

    @FXML
    private JFXButton goback;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane building = FXMLLoader.load(getClass().getResource("/resources/fxml/BuildingManagement.fxml"));
        borderPane.setCenter(building);
    }

    @FXML
    void onDisplayLevels(ActionEvent event) throws IOException {

        //Assign the all levels information to the table
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane levels = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayAllLevels.fxml"));
        borderPane.setCenter(levels);
    }

    @FXML
    void onDisplayPersons(ActionEvent event) throws IOException {

        //Assign the persons in the building information to the table
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane persons = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayAllPersons.fxml"));
        borderPane.setCenter(persons);
    }

    @FXML
    void onUpdate(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
