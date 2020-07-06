package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.ContractService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Component
public class ObsoleteContractController {

    private final ViewFlow viewFlow;
    private final ContractService contractService;
    private final FXMLResources fxmlResources;


    @FXML
    private TableView<ContractDTO> tblContracts;

    @FXML
    private TableColumn<ContractDTO, String> colId;

    @FXML
    private TableColumn<ContractDTO, String> colDateOfPayment;

    @FXML
    private TableColumn<ContractDTO, Long> colDuration;

    @FXML
    private TableColumn<ContractDTO, String> colExpirationDate;

    @FXML
    private TextField tfSearch;

    private List<ContractDTO> completeList;

    public ObsoleteContractController(ViewFlow viewFlow, ContractService contractService, FXMLResources fxmlResources) {
        this.viewFlow = viewFlow;
        this.contractService = contractService;
        this.fxmlResources = fxmlResources;
    }

    @FXML
    void handleDelete(ActionEvent event) {
        ContractDTO selectedDto = tblContracts.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            contractService.delete(selectedDto);
            new Alert(Alert.AlertType.CONFIRMATION, "Contract Successfully Deleted");
            tblContracts.getItems().remove(selectedDto);
            tblContracts.refresh();
        }
    }

    @FXML
    void handleDisplay(ActionEvent event) throws IOException {
        ContractDTO selectedDto = tblContracts.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayContractController.setContractDTO(selectedDto);
            viewFlow.loadResource(fxmlResources.getObsoleteContractsResource(), fxmlResources.getDisplayContractResource());
        }
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();
    }

    @FXML
    public void initialize() {
        completeList = contractService.findAllObsolete();
        colId.setComparator((contractId1, contractId2) -> {
            long id1 = Long.parseLong(contractId1.substring(ContractsManagementController.idPrefix.length()));
            long id2 = Long.parseLong(contractId2.substring(ContractsManagementController.idPrefix.length()));
            return Long.compare(id1, id2);
        });
        colId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(ContractsManagementController.idPrefix + param.getValue().getId()));

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
                String conId = ContractsManagementController.idPrefix + contractDTO.getId();
                return conId.toLowerCase().contains(newValue.toLowerCase());
            }).collect(Collectors.toList());
            tblContracts.getItems().clear();
            tblContracts.getItems().addAll(result);
        });
    }


}
