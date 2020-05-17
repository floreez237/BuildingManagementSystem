/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayBillsController implements Initializable {

    @FXML
    private TableColumn<?, ?> billIdCol;

    @FXML
    private TableColumn<?, ?> issueDateCol;

    @FXML
    private TableColumn<?, ?> dueDateCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
