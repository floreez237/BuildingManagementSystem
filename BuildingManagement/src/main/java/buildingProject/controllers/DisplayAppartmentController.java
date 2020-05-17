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
import javafx.scene.control.Accordion;
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
public class DisplayAppartmentController implements Initializable {

    @FXML
    private JFXTextField tfStatus;

    @FXML
    private Accordion accordionAppartment;

    @FXML
    private TitledPane tpBedroom;

    @FXML
    private TitledPane tpFurnitures;

    @FXML
    private TitledPane tpKitchen;

    @FXML
    private TitledPane tpAdditionalRooms;

    @FXML
    private TitledPane tpToilet;

    @FXML
    private TitledPane tpParlours;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        //Display back room info either appartment,studio or bedroom   

    }

    @FXML
    void onDisplay(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane bills = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayBills.fxml"));
        borderPane.setCenter(bills);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
