/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.dto.BuildingDTO;
import buildingProject.dto.BuildingLevelDTO;
import buildingProject.services.BuildingLevelService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class DisplayAllLevelsController implements Initializable {
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final BuildingLevelService buildingLevelService;
    private final ViewFlow viewFlow;
    private static BuildingDTO buildingDTO;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<BuildingLevelDTO> tblLevels;

    @FXML
    private TableColumn<BuildingLevelDTO, Long> colLevelNumber;

    @FXML
    private TableColumn<BuildingLevelDTO, Long> colTotalRoom;

    @FXML
    private TableColumn<BuildingLevelDTO, Long> colOccupiedRooms;

    @FXML
    private TableColumn<BuildingLevelDTO, Long> colFreeRooms;

    private final ObservableList<BuildingLevelDTO> completeList = FXCollections.observableArrayList();


    public DisplayAllLevelsController(FXMLResources fxmlResources, ApplicationContext applicationContext, BuildingLevelService buildingLevelService, ViewFlow viewFlow) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.buildingLevelService = buildingLevelService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
      viewFlow.goBack();
    }

    @FXML
    void handleDisplayLevel(ActionEvent event) throws IOException {
        // //Initialise the table with the appropriate level information
        BuildingLevelDTO selectedDto = tblLevels.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayLevelController.setLevelDTO(selectedDto);
            viewFlow.loadResource(fxmlResources.getDisplayAllLevels(),fxmlResources.getDisplayLevelResource());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        completeList.clear();
        buildingDTO.getListOfLevelId().forEach(levelId -> completeList.add(buildingLevelService.getLevelDto(levelId)));
        tblLevels.getSortOrder().add(colLevelNumber);
        tblLevels.getItems().clear();
        tblLevels.getItems().addAll(completeList);
        tblLevels.getSortOrder().add(colLevelNumber);

        colLevelNumber.setCellValueFactory(new PropertyValueFactory<>("levelNumber"));
        colOccupiedRooms.setCellValueFactory(param -> {
            Long occupiedRooms = buildingLevelService.getNumberOfOccupiedRooms(param.getValue().getId());
            return new ReadOnlyObjectWrapper<>(occupiedRooms);
        });
        colFreeRooms.setCellValueFactory(param -> {
            Long freeRooms = buildingLevelService.getNumberOfFreeRooms(param.getValue().getId());
            return new ReadOnlyObjectWrapper<>(freeRooms);
        });
        colTotalRoom.setCellValueFactory(param -> {
            Long totalRooms = buildingLevelService.getTotalNumberOfRooms(param.getValue().getId());
            return new ReadOnlyObjectWrapper<>(totalRooms);
        });

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfSearch.setText(oldValue);
                return;
            }
            long levelNumber = Long.parseLong(newValue);
            List<BuildingLevelDTO> result = completeList.stream().filter(levelDTO -> levelDTO.getLevelNumber() == levelNumber).
                    collect(Collectors.toList());
            tblLevels.getItems().clear();
            tblLevels.getItems().addAll(result);
        });

    }

    public static void setBuildingDTO(BuildingDTO buildingDTO) {
        DisplayAllLevelsController.buildingDTO = buildingDTO;
    }
}
