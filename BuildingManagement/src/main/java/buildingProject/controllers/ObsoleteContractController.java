package buildingProject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ObsoleteContractController {

    @FXML
    private TableView<?> tblContracts;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDateOfPayment;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colExpirationDate;

    @FXML
    private TextField tfSearch;

    @FXML
    void handleDelete(ActionEvent event) {

    }

    @FXML
    void handleDisplay(ActionEvent event) {

    }

    @FXML
    void handleGoBack(ActionEvent event) {

    }

}
