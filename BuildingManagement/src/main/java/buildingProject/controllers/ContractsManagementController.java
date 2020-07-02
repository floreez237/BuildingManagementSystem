package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.ContractService;
import buildingProject.services.rooms.RoomService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Component
public class ContractsManagementController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final ContractService contractService;
    private final RoomService roomService;
    private final ViewFlow viewFlow;

    private static final String idPrefix = "CON";

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private TableView<ContractDTO> tblContracts;

    @FXML
    private TableColumn<ContractDTO, String> colId;

    @FXML
    private TableColumn<ContractDTO, String> colDateOfPayment;

    @FXML
    private TableColumn<ContractDTO, String> colExpirationDate;

    @FXML
    private TableColumn<ContractDTO, Long> colDuration;

    @FXML
    private TextField tfSearch;

    private List<ContractDTO> completeList;

    public ContractsManagementController(ApplicationContext applicationContext, FXMLResources fxmlResources, ContractService contractService, RoomService roomService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.contractService = contractService;
        this.roomService = roomService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAdd(ActionEvent event) throws IOException {
//first check if there is a free room
        long numberOfFreeRooms = roomService.countFreeRooms();
        if (numberOfFreeRooms == 0) {
            new Alert(Alert.AlertType.ERROR, "NO NEW FREE ROOM AVAILABLE").showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlResources.getAddContractResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleDelete(ActionEvent event) {
        ContractDTO selectedDto = tblContracts.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Continue?", ButtonType.YES, ButtonType.NO);
            deleteAlert.setHeaderText("DELETING CONTRACT");
            deleteAlert.resultProperty().addListener(observable -> {
                if (deleteAlert.getResult() == ButtonType.YES) {
                    contractService.delete(selectedDto);
                    tblContracts.getItems().remove(selectedDto);
                    new Alert(Alert.AlertType.INFORMATION,"CONTRACT DELETED.").showAndWait();
                    tblContracts.refresh();
                }
            });
            deleteAlert.showAndWait();
        }
    }

    @FXML
    void handleDisplay(ActionEvent event) throws IOException {
        ContractDTO selectedDto = tblContracts.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayContractController.setContractDTO(selectedDto);
            FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayContractResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            MainViewController.getGlobalMainPage().setCenter(loader.load());
        }
    }

    @FXML
    void handleDisplayObsoleteContracts(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        //initialize the stack
        viewFlow.clear();
        viewFlow.add(fxmlResources.getContractsManagementResource());

        completeList = contractService.findAllNonObsolete();
        cmbType.getItems().addAll("ALL", "RUNNING", "EXPIRED");
        cmbType.getSelectionModel().select(0);

        cmbType.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            String selected = cmbType.getValue();
            List<ContractDTO> newList = new ArrayList<>(completeList);
            if (selected.equals("RUNNING")) {
                newList = newList.stream().filter(contractDTO -> !contractService.isContractExpired(contractDTO)).collect(Collectors.toList());
            } else if (selected.equals("EXPIRED")) {
                newList = newList.stream().filter(contractService::isContractExpired).collect(Collectors.toList());
            }
            tblContracts.getItems().clear();
            tblContracts.getItems().addAll(newList);
        });

        colId.setComparator((contractId1, contractId2) -> {
            long id1 = Long.parseLong(contractId1.substring(idPrefix.length()));
            long id2 = Long.parseLong(contractId2.substring(idPrefix.length()));
            return Long.compare(id1, id2);
        });
        colId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(idPrefix + param.getValue().getId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colDateOfPayment.setCellValueFactory(param -> {
            LocalDate dateOfPayment = param.getValue().getDateOfPayment();
            if (dateOfPayment == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            return new ReadOnlyObjectWrapper<>(dateOfPayment.format(formatter));
        });

        colExpirationDate.setCellValueFactory(param -> {
            long duration = param.getValue().getDuration();
            LocalDate dateOfCreation = param.getValue().getDateOfPayment();
            if (dateOfCreation == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            LocalDate expirationDate = dateOfCreation.plusMonths(duration);


            return new ReadOnlyObjectWrapper<>(expirationDate.format(formatter));
        });

        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tblContracts.getItems().addAll(completeList);
        tblContracts.getSortOrder().add(colId);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            List<ContractDTO> result = completeList.stream().filter(contractDTO -> {
                String conId = idPrefix + contractDTO.getId();
                return conId.toLowerCase().contains(newValue.toLowerCase());
            }).collect(Collectors.toList());
            tblContracts.getItems().clear();
            tblContracts.getItems().addAll(result);
        });
    }

    public static String getIdPrefix() {
        return idPrefix;
    }
}
