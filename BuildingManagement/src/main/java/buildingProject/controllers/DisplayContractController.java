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
public class DisplayContractController implements Initializable {

    @FXML
    private JFXTextField tfContractId;

    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfDateOfPayment;

    @FXML
    private JFXTextField tfDateOfCreation;

    @FXML
    private JFXTextField tfDuratiion;

    @FXML
    private JFXTextField tfTenantName;

    @FXML
    void displayTenant(ActionEvent event) throws IOException {

        //Get the person object from tenant name and display person information

        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane person = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayPerson.fxml"));
        borderPane.setCenter(person);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane contracts = FXMLLoader.load(getClass().getResource("/resources/fxml/ContractsManagement.fxml"));
        borderPane.setCenter(contracts);
    }

    @FXML
    void onRenew(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane contract = FXMLLoader.load(getClass().getResource("/resources/fxml/RenewContract.fxml"));
        borderPane.setCenter(contract);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
