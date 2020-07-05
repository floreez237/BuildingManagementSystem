package buildingProject.controllers;

import buildingProject.dto.BuildingDTO;
import buildingProject.model.embeddables.BuildingExtra;
import buildingProject.services.BuildingService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddBuildingController {

    private final BuildingService buildingService;
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final ViewFlow viewFlow;

    @FXML
    private JFXListView<String> lvExtras;

    @FXML
    private JFXTextField tfBuildingLocation;

    @FXML
    private JFXTextField tfBuildingName;

    @FXML
    private JFXTextField tfNumberOfLevel;

    public AddBuildingController(BuildingService buildingService, FXMLResources fxmlResources, ApplicationContext applicationContext, ViewFlow viewFlow) {
        this.buildingService = buildingService;
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.viewFlow = viewFlow;
    }

    @FXML
    void onAddExtra(ActionEvent event) throws IOException {
        AddExtraController.setLvExtra(lvExtras);
        FXMLLoader loader = new FXMLLoader(fxmlResources.getAddExtraResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void onDeleteExtras(ActionEvent event) {
        lvExtras.getItems().removeAll(lvExtras.getSelectionModel().getSelectedItems());
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (tfNumberOfLevel.getText().isEmpty()) {
            Alert noLevel = new Alert(AlertType.ERROR, "NUMBER OF LEVELS NOT ENTERED", ButtonType.OK);
            noLevel.showAndWait();
        }else{
            BuildingDTO buildingDTO = new BuildingDTO();
            int numberOfLevel = Integer.parseInt(tfNumberOfLevel.getText());
            buildingDTO.setBuildingName(tfBuildingName.getText());
            buildingDTO.setLocation(tfBuildingLocation.getText());
            lvExtras.getItems().forEach(extra -> buildingDTO.getBuildingExtraSet().add(createExtra(extra)));
            long id = buildingService.createBuilding(buildingDTO, numberOfLevel);
            Alert success = new Alert(AlertType.INFORMATION, String.format("BUILDING CREATED SUCCESSFULLY\nBUILDING ID: %d", id));
            success.showAndWait();
            initializeFields();
        }
    }

    private BuildingExtra createExtra(String name){
        BuildingExtra extra = new BuildingExtra();
        extra.setName(name);

        return extra;
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Alert cancel = new Alert(AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
        cancel.showAndWait();
        cancel.resultProperty().addListener(observable -> {
            if (cancel.getResult() == ButtonType.YES) {
                try {
                    handleGoBack(new ActionEvent());
                } catch (IOException e) {
                    Alert error = new Alert(AlertType.ERROR, "AN ERROR HAS OCCURRED");
                    error.showAndWait();
                    Platform.exit();
                }
            }
        });
    }

    @FXML
    public void initialize() {
        tfNumberOfLevel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNumberOfLevel.setText(oldValue);
            }
        });
    }

    public void initializeFields() {
        tfNumberOfLevel.clear();
        tfBuildingName.clear();
        tfBuildingLocation.clear();
        lvExtras.getItems().clear();
    }
}
