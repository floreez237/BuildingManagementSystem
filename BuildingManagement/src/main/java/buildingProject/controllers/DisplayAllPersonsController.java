package buildingProject.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class DisplayAllPersonsController {

    @FXML
    private TableView<?> expiredContracts;

    @FXML
    private TableColumn<?, ?> contractId;

    @FXML
    private TableColumn<?, ?> dateOfCreation;

    @FXML
    private TableColumn<?, ?> duration;

    @FXML
    private TableColumn<?, ?> tenantName;

    @FXML
    private TableColumn<?, ?> expirationDate;

    @FXML
    private JFXButton back;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void onAddOrEdit(ActionEvent event) {

    }

}
