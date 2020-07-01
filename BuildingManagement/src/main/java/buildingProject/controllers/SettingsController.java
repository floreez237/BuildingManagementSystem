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
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField tfCurrentPassword;

    @FXML
    private JFXTextField tfNewPassword;

    @FXML
    private JFXTextField tfPasswordConfirmation;

    @FXML
    void handleOnCancelAction(ActionEvent event) {

    }

    @FXML
    void handleOnSaveAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
