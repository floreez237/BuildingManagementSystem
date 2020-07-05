/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.dto.BuildingDTO;
import buildingProject.model.embeddables.BuildingExtra;
import buildingProject.services.BuildingService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayBuildingController implements Initializable {

    private static BuildingDTO currentBuildingDTO;

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final BuildingService buildingService;
    private final ViewFlow viewFlow;

    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;

    @FXML
    private JFXTextField tfBuildingId;

    @FXML
    private JFXTextField tfName;

    @FXML
    private JFXTextField tfLocation;

    @FXML
    private JFXTextField tfNumberOfLevels;

    @FXML
    private JFXTextField tfFreeRooms;

    @FXML
    private JFXTextField tfOccupiedRooms;

    @FXML
    private JFXListView<String> lvExtra;


    public DisplayBuildingController(FXMLResources fxmlResources, ApplicationContext applicationContext, BuildingService buildingService, ViewFlow viewFlow) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.buildingService = buildingService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAddExtra(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(fxmlResources.getAddExtraResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(logo.getURL().toString()));
        AddExtraController.setLvExtra(lvExtra);
        stage.showAndWait();


    }

    @FXML
    void handleDeleteExtra(ActionEvent event) {
        lvExtra.getItems().removeAll(lvExtra.getSelectionModel().getSelectedItems());
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();
    }

    @FXML
    void onDisplayLevels(ActionEvent event) throws IOException {
        //Assign the all levels information to the table
        DisplayAllLevelsController.setBuildingDTO(currentBuildingDTO);
        viewFlow.loadResource(fxmlResources.getDisplayBuildingResource(),fxmlResources.getDisplayAllLevels());

    }

    @FXML
    void onDisplayPersons(ActionEvent event) throws IOException {
        //Assign the persons in the building information to the table
        DisplayAllPersonsController.initializeCompleteList(buildingService.findAllPersons(currentBuildingDTO));
        DisplayAllPersonsController.setRoomId(null);
        viewFlow.loadResource(fxmlResources.getDisplayBuildingResource(),fxmlResources.getDisplayAllPersonsResource());
//        controller.setAddButtonVisible(false);
    }

    @FXML
    void onUpdate(ActionEvent event) {
        currentBuildingDTO.setBuildingName(tfName.getText());
        currentBuildingDTO.setLocation(tfLocation.getText());
        updateExtra();
        buildingService.save(currentBuildingDTO);
        new Alert(AlertType.INFORMATION, "BUILDING UPDATED").showAndWait();
    }

    private void updateExtra() {
        Set<BuildingExtra> newExtraSet = lvExtra.getItems().stream().map(this::createExtra).collect(Collectors.toSet());
        currentBuildingDTO.getBuildingExtraSet().clear();
        currentBuildingDTO.getBuildingExtraSet().addAll(newExtraSet);
    }
    private BuildingExtra createExtra(String name) {
        BuildingExtra extra = new BuildingExtra();
        extra.setName(name);

        return extra;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfBuildingId.setText(String.format("BUILD%d", currentBuildingDTO.getId()));
        tfNumberOfLevels.setText("" + currentBuildingDTO.getListOfLevelId().size());
        tfName.setText(currentBuildingDTO.getBuildingName());
        tfLocation.setText(currentBuildingDTO.getLocation());
        tfFreeRooms.setText("" + buildingService.getNumberOfAvailableRooms(currentBuildingDTO));
        tfOccupiedRooms.setText("" + buildingService.getNumberOfOccupiedRooms(currentBuildingDTO));
        lvExtra.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvExtra.getItems().addAll(currentBuildingDTO.getBuildingExtraSet().stream().map(BuildingExtra::getName).collect(Collectors.toSet()));
        lvExtra.setPlaceholder(new Label("NO EXTRA PRESENT"));

    }

    public static void setCurrentBuildingDTO(BuildingDTO currentBuildingDTO) {
        DisplayBuildingController.currentBuildingDTO = currentBuildingDTO;
    }
}
