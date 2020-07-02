package buildingProject.controllers;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import buildingProject.services.BuildingLevelService;
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
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

import static buildingProject.toolkit.Tools.formatString;

@SuppressWarnings({"DuplicatedCode", "unchecked"})
@Component
public class AddAppartmentController {

    private static Long levelId;

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final AppartmentService appartmentService;
    private final BuildingLevelService levelService;
    private final ViewFlow viewFlow;

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

    public AddAppartmentController(FXMLResources fxmlResources, ApplicationContext applicationContext, AppartmentService appartmentService, BuildingLevelService levelService, ViewFlow viewFlow) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.appartmentService = appartmentService;
        this.levelService = levelService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAdd(ActionEvent event) throws IOException {
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
            } else {
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
    void handleDelete(ActionEvent event) {
        TitledPane titledPane = accordionAppartment.getExpandedPane();
        if (titledPane != null) {
            Node expanded = titledPane.getContent();

            if (expanded.equals(lvFurnitures)) {
                lvFurnitures.getItems().removeAll(lvFurnitures.getSelectionModel().getSelectedItems());
            } else if (expanded.equals(tblAdditionalRooms)) {
                tblAdditionalRooms.getItems().removeAll(tblAdditionalRooms.getSelectionModel().getSelectedItems());
            } else {
                //this is for the areas
                TableView<MyDoubleArea> tblArea = ((TableView<MyDoubleArea>) expanded);
                tblArea.getItems().removeAll(tblArea.getSelectionModel().getSelectedItems());
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

        if (tfRent.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "RENT FIELD NOT FILLED").showAndWait();
            return;
        }

        if (tfDeposit.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "DEPOSIT FIELD NOT ENTERED").showAndWait();
            return;
        }
        AppartmentDTO appartmentDTO = new AppartmentDTO();
        appartmentDTO.setBuildingID(BuildingId);
        appartmentDTO.setBuildingLevelId(levelId);
        appartmentDTO.setRent(Double.parseDouble(tfRent.getText()));
        appartmentDTO.setDeposit(Double.parseDouble(tfDeposit.getText()));
        appartmentDTO.setPaintColor(formatString(tfPaintColor.getText()));
        addRoomsAreas(appartmentDTO);
        addFurniture(appartmentDTO);
        addAdditionalRooms(appartmentDTO);
        Long id = appartmentService.save(appartmentDTO);

        Alert createAlert = new Alert(Alert.AlertType.INFORMATION, String.format("ID: ROOM%d", id));
        createAlert.setHeaderText("Appartment Created");
        createAlert.showAndWait();
    }

    private void addRoomsAreas(AppartmentDTO appartmentDTO) {
        appartmentDTO.setAreasOfBedrooms(tblBedRoom.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfKitchens(tblKitchen.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfParlours(tblParlours.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
        appartmentDTO.setAreasOfToilets(tblToilets.getItems().stream().map(myDoubleArea -> Double.parseDouble(myDoubleArea.getArea()))
                .collect(Collectors.toList()));
    }

    private void addFurniture(AppartmentDTO appartmentDTO) {
        appartmentDTO.getSetOfFurniture().clear();
        appartmentDTO.getSetOfFurniture().addAll(lvFurnitures.getItems().stream().map(furnitureName -> {
            Furniture furniture = new Furniture();
            furniture.setName(furnitureName);
            return furniture;
        }).collect(Collectors.toSet()));
    }

    private void addAdditionalRooms(AppartmentDTO appartmentDTO) {
        appartmentDTO.getAdditionalRooms().clear();
        appartmentDTO.getAdditionalRooms().addAll(tblAdditionalRooms.getItems());
    }

    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        handleGoBack(event);
    }

    @FXML
    public void initialize() {
        lblDepositCurrency.setText(GlobalConstants.currencySymbol);
        lblRentCurrency.setText(GlobalConstants.currencySymbol);

        configureAreasTable(tblBedRoom);
        configureAreasTable(tblKitchen);
        configureAreasTable(tblToilets);
        configureAreasTable(tblParlours);

        lvFurnitures.setPlaceholder(new Label("No Furniture Present"));
        lvFurnitures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tblAdditionalRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colAddRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));

        colAddRoomArea.setCellValueFactory(param -> {
            String area = String.format("%.2f", param.getValue().getArea());
            return new ReadOnlyObjectWrapper<>(area);
        });

        Tools.addDoubleValidation(tfRent);
        Tools.addDoubleValidation(tfDeposit);
    }

    public void configureAreasTable(TableView<MyDoubleArea> areaTableView) {
        TableColumn<MyDoubleArea, Long> idColumn = (TableColumn<MyDoubleArea, Long>) areaTableView.getColumns().get(0);
        TableColumn<MyDoubleArea, String> areaColumn = (TableColumn<MyDoubleArea, String>) areaTableView.getColumns().get(1);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
    }

    public static void setLevelId(Long levelId) {
        AddAppartmentController.levelId = levelId;
    }
}
