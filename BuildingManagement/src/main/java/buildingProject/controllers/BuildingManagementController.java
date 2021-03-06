/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.dto.BuildingDTO;
import buildingProject.services.BuildingService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@SuppressWarnings("unchecked")
@Component
public class BuildingManagementController implements Initializable {

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final BuildingService buildingService;
    private final ViewFlow viewFlow;

    @FXML
    private TableView<BuildingDTO> tblBuildings;

    @FXML
    private TableColumn<BuildingDTO, String> colBuildingId;

    @FXML
    private TableColumn<BuildingDTO, String> colBuildingName;

    @FXML
    private TableColumn<BuildingDTO, String> colLocation;

    @FXML
    private TableColumn<BuildingDTO, Long> colNumberOfLevels;

    @FXML
    private TableColumn<BuildingDTO, Long> colAvailableRooms;

    @FXML
    private TextField tfSearch;

    private ObservableList<BuildingDTO> completeList;

    public BuildingManagementController(FXMLResources fxmlResources, ApplicationContext applicationContext, BuildingService buildingService, ViewFlow viewFlow) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.buildingService = buildingService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAddBuilding(ActionEvent event) throws IOException {
        viewFlow.loadResource(fxmlResources.getBuildingManagementResource(), fxmlResources.getAddBuildingResource());
    }

    @FXML
    void onDelete(ActionEvent event) {
        BuildingDTO selectedDto = tblBuildings.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            Alert delete = new Alert(AlertType.WARNING, "This will delete all items related to this building(Contracts,Persons etc.)\n" +
                    "Do you want to remove it?", ButtonType.YES, ButtonType.NO);
            delete.setHeaderText("Remove Building");
            delete.resultProperty().addListener(observable -> {
                if (delete.getResult() == ButtonType.YES) {
                    buildingService.delete(selectedDto);
                    tblBuildings.getItems().remove(selectedDto);
                    completeList.remove(selectedDto);
                    tblBuildings.refresh();
                }
            });
            delete.showAndWait();
        }
    }

    @FXML
    void onDisplay(ActionEvent event) throws IOException {
        BuildingDTO selectedDto = tblBuildings.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayBuildingController.setCurrentBuildingDTO(selectedDto);
            viewFlow.loadResource(fxmlResources.getBuildingManagementResource(), fxmlResources.getDisplayBuildingResource());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize the view stack
        viewFlow.clear();

        colBuildingId.setCellValueFactory(param -> {
            String id = "BUILD" + param.getValue().getId();
            return new ReadOnlyObjectWrapper<>(id);
        });
        colBuildingId.setComparator((buildingId1, buildingId2) -> {
            long id1 = Long.parseLong(buildingId1.substring(5));
            long id2 = Long.parseLong(buildingId2.substring(5));
            return Long.compare(id1, id2);
        });
        colBuildingName.setCellValueFactory(new PropertyValueFactory<>("buildingName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        colAvailableRooms.setCellValueFactory(param -> {
            long numberOfFreeRooms = buildingService.getNumberOfAvailableRooms(param.getValue());
            return new ReadOnlyObjectWrapper<>(numberOfFreeRooms);
        });

        colNumberOfLevels.setCellValueFactory(param -> {
            long numberOfLevels = param.getValue().getListOfLevelId().size();
            return new ReadOnlyObjectWrapper<>(numberOfLevels);
        });

        completeList = FXCollections.observableArrayList(buildingService.getListOfBuildings());
        tblBuildings.setRowFactory(param -> {
            TableRow<BuildingDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    BuildingDTO selectedDto = param.getSelectionModel().getSelectedItem();
                    DisplayBuildingController.setCurrentBuildingDTO(selectedDto);
                    try {
                        viewFlow.loadResource(fxmlResources.getBuildingManagementResource(), fxmlResources.getDisplayBuildingResource());
                    } catch (IOException ignored) {
                    }
                }
            });
            return row;
        });
        tblBuildings.getItems().addAll(completeList);
        tblBuildings.getSortOrder().addAll(colBuildingId);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<BuildingDTO> list = completeList.stream().filter(buildingDTO -> {
                TableColumn idColumn = tblBuildings.getColumns().get(0);
                String buildingId = (String) idColumn.getCellObservableValue(buildingDTO).getValue();
                return buildingId.toLowerCase().contains(newValue.toLowerCase()) || buildingDTO.getBuildingName().toLowerCase().contains(newValue.toLowerCase());
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));
            tblBuildings.setItems(list);
        });

    }

}
