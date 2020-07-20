package buildingProject.controllers;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import buildingProject.services.rooms.AppartmentService;
import buildingProject.toolkit.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static buildingProject.toolkit.Tools.formatString;
import static javafx.scene.control.Alert.AlertType;

@SuppressWarnings({"unchecked", "DuplicatedCode"})
@Component
@Slf4j
public class DisplayAppartmentController {

    private static AppartmentDTO appartmentDTO;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final AppartmentService appartmentService;
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
    private Label lblRentCurrency;

    @FXML
    private Label lblDepositCurrency;

    @FXML
    private Accordion accordionAppartment;

    @FXML
    private TableView<MyDoubleArea> tblBedRoom;

    @FXML
    private TableView<MyDoubleArea> tblKitchen;

    @FXML
    private TableView<MyDoubleArea> tblParlours;


    @FXML
    private TableView<MyDoubleArea> tblToilets;

    @FXML
    private JFXListView<String> lvFurnitures;

    @FXML
    private TableView<AdditionalRoom> tblAdditionalRooms;

    @FXML
    private TableColumn<AdditionalRoom, String> colAddRoomName;

    @FXML
    private TableColumn<AdditionalRoom, String> colAddRoomArea;

    public DisplayAppartmentController(ApplicationContext applicationContext, FXMLResources fxmlResources, AppartmentService appartmentService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.appartmentService = appartmentService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        if (tfRent.getText().isEmpty()) {
            new Alert(AlertType.ERROR, "RENT FIELD NOT FILLED").showAndWait();
            return;
        }

        if (tfDeposit.getText().isEmpty()) {
            new Alert(AlertType.ERROR, "DEPOSIT FIELD NOT ENTERED").showAndWait();
            return;
        }

        appartmentDTO.setRent(Double.parseDouble(tfRent.getText()));
        appartmentDTO.setDeposit(Double.parseDouble(tfDeposit.getText()));
        appartmentDTO.setPaintColor(formatString(tfPaintColor.getText()));
        updateRoomsAreas();
        updateFurniture();
        updateAdditionalRooms();
        appartmentService.save(appartmentDTO);

        Alert updateAlert = new Alert(AlertType.INFORMATION, "Appartment Updated");
        updateAlert.setHeaderText("UPDATED");
        updateAlert.showAndWait();
    }

    private void updateRoomsAreas() {
        appartmentDTO.setAreasOfBedrooms(tblBedRoom.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfKitchens(tblKitchen.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfParlours(tblParlours.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfToilets(tblToilets.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
    }

    private void updateFurniture() {
        appartmentDTO.getSetOfFurniture().clear();
        appartmentDTO.getSetOfFurniture().addAll(lvFurnitures.getItems().stream().map(furnitureName ->{
            Furniture furniture = new Furniture();
            furniture.setName(furnitureName);
            return furniture;
        }).collect(Collectors.toSet()));
    }

    private void updateAdditionalRooms() {
        appartmentDTO.getAdditionalRooms().clear();
        appartmentDTO.getAdditionalRooms().addAll(tblAdditionalRooms.getItems());
    }

    @FXML
    void onAdd(ActionEvent event) throws IOException {
        TitledPane titledPane = accordionAppartment.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();
            FXMLLoader loader;
            if (expanded.equals(lvFurnitures)) {
                AddFurnitureController.setLvFurniture(lvFurnitures);
                loader = new FXMLLoader(fxmlResources.getAddFurnitureResource().getURL());
            } else if (expanded.equals(tblAdditionalRooms)) {
                AddAdditionalRoomController.setTblAdditionalRoom(tblAdditionalRooms);
                loader = new FXMLLoader(fxmlResources.getAddAdditionalRoomResource().getURL());
            }else{
                //this is for the areas
                AddAreaController.setTblArea(((TableView<MyDoubleArea>) expanded));
                loader = new FXMLLoader(fxmlResources.getAddAreaResource().getURL());
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
        TitledPane titledPane = accordionAppartment.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();

            if (expanded.equals(lvFurnitures)) {
                lvFurnitures.getItems().removeAll(lvFurnitures.getSelectionModel().getSelectedItems());
            } else if (expanded.equals(tblAdditionalRooms)) {
                tblAdditionalRooms.getItems().removeAll(tblAdditionalRooms.getSelectionModel().getSelectedItems());
            }else{
                //this is for the areas
                TableView<MyDoubleArea> tblArea = ((TableView<MyDoubleArea>) expanded);
                tblArea.getItems().removeAll(tblArea.getSelectionModel().getSelectedItems());

            }
        }
    }


    @FXML
    public void initialize() {
        tfRoomId.setText("ROOM" + appartmentDTO.getId());
        tfRent.setText("" + appartmentDTO.getRent());
        tfDeposit.setText("" + appartmentDTO.getDeposit());
        tfPaintColor.setText(appartmentDTO.getPaintColor());
        tfStatus.setText(appartmentDTO.isOccupied() ? "Occupied" : "Available");
        lblDepositCurrency.setText(GlobalConstants.currencySymbol);
        lblRentCurrency.setText(GlobalConstants.currencySymbol);

        Tools.addDoubleValidation(tfRent);
        Tools.addDoubleValidation(tfDeposit);

        initializeAreasTable(tblBedRoom, appartmentDTO.getAreasOfBedrooms());
        initializeAreasTable(tblKitchen, appartmentDTO.getAreasOfKitchens());
        initializeAreasTable(tblParlours, appartmentDTO.getAreasOfParlours());
        initializeAreasTable(tblToilets,appartmentDTO.getAreasOfToilets());

        lvFurnitures.getItems().addAll(appartmentDTO.getSetOfFurniture().stream().map(Furniture::getName).collect(Collectors.toSet()));
        lvFurnitures.setPlaceholder(new Label("No Furniture Present"));
        lvFurnitures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        tblAdditionalRooms.getItems().addAll(appartmentDTO.getAdditionalRooms());
        tblAdditionalRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colAddRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));

        colAddRoomArea.setCellValueFactory(param -> {
            String area = String.format("%.2f", param.getValue().getArea());
            return new ReadOnlyObjectWrapper<>(area);
        });
    }

    private void initializeAreasTable(TableView<MyDoubleArea> areaTableView, List<Double> values) {
        areaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<MyDoubleArea> myDoubleAreas = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            myDoubleAreas.add(new MyDoubleArea((long) (i + 1), values.get(i)));
        }
        areaTableView.getItems().addAll(myDoubleAreas);
        TableColumn<MyDoubleArea, Long> idColumn = (TableColumn<MyDoubleArea, Long>) areaTableView.getColumns().get(0);
        TableColumn<MyDoubleArea, String> areaColumn = (TableColumn<MyDoubleArea, String>) areaTableView.getColumns().get(1);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
    }


    public static void setAppartmentDTO(AppartmentDTO appartmentDTO) {
        DisplayAppartmentController.appartmentDTO = appartmentDTO;
    }

}



