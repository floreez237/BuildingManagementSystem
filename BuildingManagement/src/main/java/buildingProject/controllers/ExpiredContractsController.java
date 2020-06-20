package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.ContractService;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
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
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Component
public class ExpiredContractsController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final ContractService contractService;
    private final PersonService personService;

    private static final String idPrefix = ContractsManagementController.getIdPrefix();


    @FXML
    private TableView<ContractDTO> tblExpiredContracts;

    @FXML
    private TableColumn<ContractDTO, String> colId;

    @FXML
    private TableColumn<ContractDTO, String> colPaymentDate;

    @FXML
    private TableColumn<ContractDTO, Long> colDuration;

    @FXML
    private TableColumn<ContractDTO, String> colExpirationDate;

    @FXML
    private TableColumn<ContractDTO, String> colRoomId;

    @FXML
    private TableColumn<ContractDTO, String> colTenantName;

    @FXML
    private TextField tfSearch;

    private List<ContractDTO> completeList;

    public ExpiredContractsController(ApplicationContext applicationContext, FXMLResources fxmlResources, ContractService contractService, PersonService personService) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.contractService = contractService;
        this.personService = personService;
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDashboardResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleDelete(ActionEvent event) {
        ContractDTO selectedDto = tblExpiredContracts.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Continue?", ButtonType.YES, ButtonType.NO);
            deleteAlert.setHeaderText("DELETING CONTRACT");
            deleteAlert.resultProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (deleteAlert.getResult() == ButtonType.YES) {
                        contractService.delete(selectedDto.getId());
                        tblExpiredContracts.getItems().remove(selectedDto);
                        new Alert(Alert.AlertType.INFORMATION,"CONTRACT DELETED.").showAndWait();
                        tblExpiredContracts.refresh();
                    }
                }
            });
            deleteAlert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        completeList = contractService.findAllExpiredContracts();
        colId.setComparator((contractId1, contractId2) -> {
            long id1 = Long.parseLong(contractId1.substring(idPrefix.length()));
            long id2 = Long.parseLong(contractId2.substring(idPrefix.length()));
            return Long.compare(id1, id2);
        });
        colId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(idPrefix + param.getValue().getId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colPaymentDate.setCellValueFactory(param -> {
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

        colRoomId.setCellValueFactory(param -> {
            long roomId = param.getValue().getRoomId();
            return new ReadOnlyObjectWrapper<>("ROOM" + roomId);
        });

        colTenantName.setCellValueFactory(param ->{
            String tenantName = personService.findPerson(param.getValue().getTenantId()).getName();
            String[] names = tenantName.split(" ");
            if (names.length < 2) {
                return new ReadOnlyObjectWrapper<>(tenantName);
            } else {
                return new ReadOnlyObjectWrapper<>(String.format("%s %s", names[0], names[1]));
            }
        });

        tblExpiredContracts.getItems().addAll(completeList);
        tblExpiredContracts.getSortOrder().add(colId);

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<ContractDTO> result = completeList.stream().filter(contractDTO -> {
                    String conId = idPrefix + contractDTO.getId();
                    return conId.toLowerCase().contains(newValue.toLowerCase());
                }).collect(Collectors.toList());
                tblExpiredContracts.getItems().clear();
                tblExpiredContracts.getItems().addAll(result);
            }
        });
    }

}
