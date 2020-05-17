/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class DisplayPersonController implements Initializable {

    @FXML
    private JFXTextField tfPhoneNumber;

    @FXML
    private JFXTextField tfNtionalIdNumber;

    @FXML
    private JFXDatePicker birthDatePicker;

    @FXML
    private JFXTextField tfAge;

    @FXML
    private ToggleGroup radGender;

    @FXML
    private JFXTextField tfPersonId1;

    @FXML
    private JFXTextField tfName;

    @FXML
    private JFXTextField tfPersonId;

    @FXML
    private JFXButton back;

    @FXML
    void goBack(ActionEvent event) {

        //this go back depends on the previous fxml file 
        //either in displaycontrcats or display room

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
