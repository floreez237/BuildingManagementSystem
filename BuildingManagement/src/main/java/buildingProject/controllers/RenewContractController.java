/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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
public class RenewContractController implements Initializable {

    @FXML
    private JFXDatePicker paymentDatePicker;

    @FXML
    private JFXDatePicker creationDatePicker;

    @FXML
    private TextField tfDuration;

    @FXML
    void goBack(ActionEvent event) throws IOException {

        //Set the contract fxml with the contract information

        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane contract = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayContract.fxml"));
        borderPane.setCenter(contract);
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        //Set the contract fxml with the contract information

        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane contract = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayContract.fxml"));
        borderPane.setCenter(contract);
    }

    @FXML
    void onSave(ActionEvent event) {

        //Save the new information and update it in object
        //on saving display directly Contract with updated information
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
