package buildingProject.controllers;

import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import buildingProject.services.BuildingLevelService;
import buildingProject.services.rooms.StudioService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.GlobalConstants;
import buildingProject.toolkit.Tools;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Component
public class AddStudioController {

    private static Long levelId;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final BuildingLevelService levelService;
    private final StudioService studioService;
    private final ViewFlow viewFlow;



    @FXML
    private Accordion accordion;

    @FXML
    private JFXListView<String> lvFurnitures;

    @FXML
    private TableView<AdditionalRoom> tblAdditionalRooms;

    @FXML
    private TableColumn<AdditionalRoom,String> colAddRoomName;

    @FXML
    private TableColumn<AdditionalRoom,String> colAddRoomArea;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    private JFXTextField tfBedroomArea;

    @FXML
    private JFXTextField tfKitchenArea;

    @FXML
    private JFXTextField tfParlourArea;

    @FXML
    private JFXTextField tfToiletArea;

    @FXML
    private Label lblRentCurrency;

    @FXML
    private Label lblDepositCurrency;

    public AddStudioController(ApplicationContext applicationContext, FXMLResources fxmlResources, BuildingLevelService levelService, StudioService studioService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.levelService = levelService;
        this.studioService = studioService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAdd(ActionEvent event) throws IOException {
        TitledPane titledPane = accordion.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();
            FXMLLoader loader;
            if (expanded.equals(lvFurnitures)) {
                AddFurnitureController.setLvFurniture(lvFurnitures);
                loader = new FXMLLoader(fxmlResources.getAddFurnitureResource().getURL());
            }else{
                AddAdditionalRoomController.setTblAdditionalRoom(tblAdditionalRooms);
                loader = new FXMLLoader(fxmlResources.getAddAdditionalRoomResource().getURL());
            }

            loader.setControllerFactory(applicationContext::getBean);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        handleGoBack(event);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        TitledPane titledPane = accordion.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();

            if (expanded.equals(lvFurnitures)) {
                lvFurnitures.getItems().removeAll(lvFurnitures.getSelectionModel().getSelectedItems());
            } else{
                tblAdditionalRooms.getItems().removeAll(tblAdditionalRooms.getSelectionModel().getSelectedItems());
            }
        }
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();

    }

    @FXML
    void handleSave(ActionEvent event) {
        Long BuildingId = levelService.getLevelDto(levelId).getBuildingID();
        if(!areObligatoryFieldsEntered()){
            new Alert(Alert.AlertType.ERROR, "One or more compulsory field was not entered").showAndWait();
            return;
        }
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setBuildingLevelId(levelId);
        studioDTO.setBuildingID(BuildingId);
        studioDTO.setRent(Double.parseDouble(tfRent.getText()));
        studioDTO.setDeposit(Double.parseDouble(tfDeposit.getText()));
        studioDTO.setAreaOfBedroom(Double.parseDouble(tfBedroomArea.getText()));
        studioDTO.setAreaOfKitchen(Double.parseDouble(tfKitchenArea.getText()));
        studioDTO.setAreaOfParlour(Double.parseDouble(tfParlourArea.getText()));
        studioDTO.setAreaOfToilet(Double.parseDouble(tfToiletArea.getText()));
        studioDTO.setPaintColor(Tools.formatString(tfPaintColor.getText()));

        studioDTO.setSetOfFurniture(lvFurnitures.getItems().stream().map(name ->{
            Furniture furniture = new Furniture();
            furniture.setName(name);
            return furniture;
        }).collect(Collectors.toSet()));

        studioDTO.getAdditionalRooms().clear();
        studioDTO.getAdditionalRooms().addAll(tblAdditionalRooms.getItems());


        Long roomId = studioService.save(studioDTO);

        Alert updateAlert = new Alert(Alert.AlertType.INFORMATION, String.format("ID: ROOM%d",roomId));
        updateAlert.setHeaderText("ROOM CREATED");
        updateAlert.showAndWait();
    }

    private boolean areObligatoryFieldsEntered() {
        TextField[] doubleTextFields = {tfRent, tfDeposit, tfParlourArea, tfToiletArea, tfKitchenArea, tfBedroomArea};
        for (TextField doubleTextField : doubleTextFields) {
            if (doubleTextField.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    public void initialize() {
        lblDepositCurrency.setText(GlobalConstants.currencySymbol);
        lblRentCurrency.setText(GlobalConstants.currencySymbol);

        //add Validation in Bulk
        TextField[] doubleTextFields = {tfRent, tfDeposit, tfParlourArea, tfToiletArea, tfKitchenArea, tfBedroomArea};
        for (TextField doubleTextField : doubleTextFields) {
            Tools.addDoubleValidation(doubleTextField);
        }

        lvFurnitures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvFurnitures.setPlaceholder(new Label("No Furniture Present"));

        tblAdditionalRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        colAddRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddRoomArea.setCellValueFactory(param -> {
            String area = String.format("%.2f", param.getValue().getArea());
            return new ReadOnlyObjectWrapper<>(area);
        });
    }

    public static void setLevelId(Long levelId) {
        AddStudioController.levelId = levelId;
    }
}
