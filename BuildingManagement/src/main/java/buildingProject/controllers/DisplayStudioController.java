package buildingProject.controllers;

import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
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
public class DisplayStudioController {

    private static StudioDTO studioDTO;
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final StudioService studioService;
    private final ViewFlow viewFlow;
    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfStatus;

    @FXML
    private Accordion accordion;

    @FXML
    private TableView<AdditionalRoom> tblAdditionalRoom;

    @FXML
    private TableColumn<AdditionalRoom, String> colAdditionalRoom;

    @FXML
    private TableColumn<AdditionalRoom, String> colAddRoomArea;

    @FXML
    private JFXListView<String> lvFurnitures;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    private JFXTextField tfBedroomArea;

    @FXML
    private JFXTextField tfParlourArea;

    @FXML
    private JFXTextField tfKitchenArea;

    @FXML
    private JFXTextField tfToiletArea;

    @FXML
    private Label lblRentCurrency;

    @FXML
    private Label lblDepositCurrency;

    public DisplayStudioController(FXMLResources fxmlResources, ApplicationContext applicationContext, StudioService studioService, ViewFlow viewFlow) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.studioService = studioService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
       viewFlow.goBack();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        if(!areObligatoryFieldsEntered()){
            new Alert(Alert.AlertType.ERROR, "One or more compulsory field was not entered").showAndWait();
            return;
        }
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
        studioDTO.getAdditionalRooms().addAll(tblAdditionalRoom.getItems());

        studioService.save(studioDTO);

        Alert updateAlert = new Alert(Alert.AlertType.INFORMATION, "Studio Updated");
        updateAlert.setHeaderText("UPDATED");
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
    void onAdd(ActionEvent event) throws IOException {
        TitledPane titledPane = accordion.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();
            FXMLLoader loader;
            if (expanded.equals(lvFurnitures)) {
                AddFurnitureController.setLvFurniture(lvFurnitures);
                loader = new FXMLLoader(fxmlResources.getAddFurnitureResource().getURL());
            }else{
                AddAdditionalRoomController.setTblAdditionalRoom(tblAdditionalRoom);
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
    void onDelete(ActionEvent event) {
        TitledPane titledPane = accordion.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();

            if (expanded.equals(lvFurnitures)) {
                lvFurnitures.getItems().removeAll(lvFurnitures.getSelectionModel().getSelectedItems());
            } else{
                tblAdditionalRoom.getItems().removeAll(tblAdditionalRoom.getSelectionModel().getSelectedItems());
            }
        }

    }

    @FXML
    public void initialize() {
        tfRoomId.setText("ROOM" + studioDTO.getId());
        tfRent.setText(String.format("%.1f", studioDTO.getRent()));
        tfDeposit.setText(String.format("%.1f", studioDTO.getDeposit()));
        tfStatus.setText(studioDTO.isOccupied() ? "Occupied" : "Available");
        tfPaintColor.setText(studioDTO.getPaintColor());
        tfBedroomArea.setText(String.format("%.2f", studioDTO.getAreaOfBedroom()));
        tfToiletArea.setText(String.format("%.2f", studioDTO.getAreaOfToilet()));
        tfKitchenArea.setText(String.format("%.2f", studioDTO.getAreaOfKitchen()));
        tfParlourArea.setText(String.format("%.2f", studioDTO.getAreaOfParlour()));

        //add Validation in Bulk
        TextField[] doubleTextFields = {tfRent, tfDeposit, tfParlourArea, tfToiletArea, tfKitchenArea, tfBedroomArea};
        for (TextField doubleTextField : doubleTextFields) {
            Tools.addDoubleValidation(doubleTextField);
        }

        lblDepositCurrency.setText(GlobalConstants.currencySymbol);
        lblRentCurrency.setText(GlobalConstants.currencySymbol);


        lvFurnitures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvFurnitures.getItems().addAll(studioDTO.getSetOfFurniture().stream().map(Furniture::getName).collect(Collectors.toSet()));
        lvFurnitures.setPlaceholder(new Label("No Furniture Present"));

        tblAdditionalRoom.getItems().addAll(studioDTO.getAdditionalRooms());
        tblAdditionalRoom.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        colAdditionalRoom.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddRoomArea.setCellValueFactory(param -> {
            String area = String.format("%.2f", param.getValue().getArea());
            return new ReadOnlyObjectWrapper<>(area);
        });
    }

    public static void setStudioDTO(StudioDTO studioDTO) {
        DisplayStudioController.studioDTO = studioDTO;
    }
}
