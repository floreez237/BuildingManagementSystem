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
import javafx.scene.control.ToggleGroup;
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
public class ExpiredContractsController implements Initializable {

    //Initialise all the data types of the different colunmns and table view
    @FXML
    private ToggleGroup selection;

    @FXML
    private TableView<?> tblExpiredContracts;

    @FXML
    private TableColumn<?, ?> contractIdCol;

    @FXML
    private TableColumn<?, ?> dateOfCreationCol;

    @FXML
    private TableColumn<?, ?> durationCol;

    @FXML
    private TableColumn<?, ?> roomId;

    @FXML
    private TableColumn<?, ?> tenantName;

    @FXML
    private TableColumn<?, ?> expirationDate;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane dashboard = FXMLLoader.load(getClass().getResource("/resources/fxml/Dashboard.fxml"));
        borderPane.setCenter(dashboard);
    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
