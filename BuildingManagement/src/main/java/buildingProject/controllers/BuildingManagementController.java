/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.toolkit.FXMLResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.springframework.context.ApplicationContext;
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
public class BuildingManagementController implements Initializable {

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;

    @FXML
    private TableView<?> tblBuildings;

    @FXML
    private TableColumn<?, ?> buildingIdCol;

    @FXML
    private TableColumn<?, ?> buildingNameCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TableColumn<?, ?> numberOfLevelsCol;

    @FXML
    private TableColumn<?, ?> availableRoomsCol;

    @FXML
    private TextField search;

    public BuildingManagementController(FXMLResources fxmlResources, ApplicationContext applicationContext) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {
        // Delete a selected building from the table
    }

    @FXML
    void onDisplay(ActionEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayBuildingResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Pane building = loader.load();
        borderPane.setCenter(building);
    }

    @FXML
    void onSearch(ActionEvent event) {
        //Search a given building in table
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
