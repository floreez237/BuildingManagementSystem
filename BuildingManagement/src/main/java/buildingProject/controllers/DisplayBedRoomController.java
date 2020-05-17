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
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayBedRoomController implements Initializable {

    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField tfStatus;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane tpAdditionalRooms;

    @FXML
    private TitledPane tpFurnitures;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    private RadioButton radYes;

    @FXML
    private ToggleGroup isToiletInternal;

    @FXML
    private RadioButton radNo;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void onDisplay(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
