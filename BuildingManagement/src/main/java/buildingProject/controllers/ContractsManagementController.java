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
public class ContractsManagementController implements Initializable {

    @FXML
    private TableView<?> tblContracts;

    @FXML
    private TableColumn<?, ?> contractIdCol;

    @FXML
    private TableColumn<?, ?> dateOfCreationCol;

    @FXML
    private TableColumn<?, ?> durationCol;

    @FXML
    private TableColumn<?, ?> expirationDateCol;

    @FXML
    private TextField search;

    @FXML
    void onAdd(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane addContract = FXMLLoader.load(getClass().getResource("/resources/fxml/AddContract.fxml"));
        borderPane.setCenter(addContract);
    }

    @FXML
    void onDelete(ActionEvent event) {
        //Implement how you wish to delete a contract from a table and eventually database
    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onDisplay(ActionEvent event) throws IOException {
        // Make sure you select row from the table first and get the object
        //then set the different textfields

        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane addContract = FXMLLoader.load(getClass().getResource("/resources/fxml/DisplayContract.fxml"));
        borderPane.setCenter(addContract);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
