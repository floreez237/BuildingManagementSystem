/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayStudioController implements Initializable {
    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfStatus;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane tpAdditionalRoom;

    @FXML
    private TitledPane tpFurniture;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    private JFXTextField tfBedroomArea;

    @FXML
    private JFXTextField tfParlourArea;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void onDisplay(ActionEvent event) {

    }

    @FXML
    void tfKitchenArea(ActionEvent event) {

    }

    @FXML
    void tftAreaToilet(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
