package buildingProject.controllers;

import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import buildingProject.services.rooms.BedroomService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.GlobalConstants;
import buildingProject.toolkit.Tools;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXRadioButton;
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
public class DisplayBedRoomController {

    private static BedroomDTO bedroomDTO;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final BedroomService bedroomService;
    private final ViewFlow viewFlow;
    
    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfStatus;

    @FXML
    private JFXTextField tfRent;

    @FXML
    private JFXTextField tfDeposit;

    @FXML
    private JFXTextField tfPaintColor;

    @FXML
    private Accordion accordion;

    @FXML
    private ListView<String> lvFurnitures;

    @FXML
    private TableView<AdditionalRoom> tblAdditionalRooms;

    @FXML
    private TableColumn<AdditionalRoom,String> colAddRoomName;

    @FXML
    private TableColumn<AdditionalRoom,String> colAddRoomArea;

    @FXML
    private JFXRadioButton radNo;

    @FXML
    private JFXRadioButton radYes;

    @FXML
    private JFXTextField tfArea;

    @FXML
    private Label lblRentCurrency;

    @FXML
    private Label lblDepositCurrency;


    public DisplayBedRoomController(ApplicationContext applicationContext, FXMLResources fxmlResources, BedroomService bedroomService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.bedroomService = bedroomService;
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

        bedroomDTO.setRent(Double.parseDouble(tfRent.getText()));
        bedroomDTO.setDeposit(Double.parseDouble(tfDeposit.getText()));
        bedroomDTO.setArea(Double.parseDouble(tfArea.getText()));
        bedroomDTO.setPaintColor(Tools.formatString(tfPaintColor.getText()));
        bedroomDTO.setToiletInternal(radYes.isSelected());
        bedroomDTO.setSetOfFurniture(lvFurnitures.getItems().stream().map(name ->{
            Furniture furniture = new Furniture();
            furniture.setName(name);
            return furniture;
        }).collect(Collectors.toSet()));

        bedroomDTO.getAdditionalRooms().clear();
        bedroomDTO.getAdditionalRooms().addAll(tblAdditionalRooms.getItems());

        bedroomService.save(bedroomDTO);

        Alert updateAlert = new Alert(Alert.AlertType.INFORMATION, "Bedroom Updated");
        updateAlert.setHeaderText("UPDATED");
        updateAlert.showAndWait();
    }

    private boolean areObligatoryFieldsEntered() {
        TextField[] doubleTextFields = {tfRent, tfDeposit,tfArea};
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
    void onDelete(ActionEvent event) {
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
    public void initialize() {
        tfRoomId.setText("ROOM" + bedroomDTO.getId());
        tfRent.setText(String.format("%.1f", bedroomDTO.getRent()));
        tfDeposit.setText(String.format("%.1f", bedroomDTO.getDeposit()));
        tfStatus.setText(bedroomDTO.isOccupied() ? "Occupied" : "Available");
        tfPaintColor.setText(bedroomDTO.getPaintColor());
        tfArea.setText(String.format("%.2f", bedroomDTO.getArea()));
        if (bedroomDTO.isToiletInternal()) {
            radYes.setSelected(true);
        } else {
            radNo.setSelected(true);
        }
        lblDepositCurrency.setText(GlobalConstants.currencySymbol);
        lblRentCurrency.setText(GlobalConstants.currencySymbol);
        Tools.addDoubleValidation(tfRent);
        Tools.addDoubleValidation(tfDeposit);
        Tools.addDoubleValidation(tfArea);


        lvFurnitures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvFurnitures.getItems().addAll(bedroomDTO.getSetOfFurniture().stream().map(Furniture::getName).collect(Collectors.toSet()));
        lvFurnitures.setPlaceholder(new Label("No Furniture Present"));

        tblAdditionalRooms.getItems().addAll(bedroomDTO.getAdditionalRooms());
        tblAdditionalRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        colAddRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddRoomArea.setCellValueFactory(param -> {
            String area = String.format("%.2f", param.getValue().getArea());
            return new ReadOnlyObjectWrapper<>(area);
        });
    }

    public static void setBedroomDTO(BedroomDTO bedroomDTO) {
        DisplayBedRoomController.bedroomDTO = bedroomDTO;
    }
}
