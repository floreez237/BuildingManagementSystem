/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.security.PasswordUtils;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    void handleOnSaveAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (tfCurrentPassword.getText().isEmpty()) {
            alert.setContentText("Current Password not entered");
            alert.showAndWait();
            return;
        }
        if (tfNewPassword.getText().isEmpty()) {
            alert.setContentText("New Password not entered");
            alert.showAndWait();
            return;
        }
        if (tfPasswordConfirmation.getText().isEmpty()) {
            alert.setContentText("Password Confirmation not entered");
            alert.showAndWait();
            return;
        }
        if (!tfNewPassword.getText().equals(tfPasswordConfirmation.getText())) {
            alert.setContentText("Confirmation does not match New Password");
            alert.showAndWait();
            return;
        }

        PasswordUtils.storePassword(tfNewPassword.getText());
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Successful Password Update");
        alert.showAndWait();
        TextField[] textFields = {tfPasswordConfirmation, tfNewPassword, tfCurrentPassword};
        for (TextField textField : textFields) {
            textField.clear();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
