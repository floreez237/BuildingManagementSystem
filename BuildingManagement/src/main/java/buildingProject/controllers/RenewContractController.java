package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.ContractService;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.Tools;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class RenewContractController {

    private static ContractDTO previousContract;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final PersonService personService;
    private final ContractService contractService;

    @FXML
    private JFXDatePicker paymentDatePicker;

    @FXML
    private JFXDatePicker creationDatePicker;

    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfDuration;

    @FXML
    private JFXTextField tfTenantName;

    public RenewContractController(ApplicationContext applicationContext, FXMLResources fxmlResources, PersonService personService, ContractService contractService) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.personService = personService;
        this.contractService = contractService;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getContractsManagementResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (!areAllFieldsEntered()) {
            new Alert(Alert.AlertType.ERROR, "Some fields were not entered").showAndWait();
            return;
        }
        if (paymentDatePicker.getValue().isBefore(creationDatePicker.getValue())) {
            new Alert(Alert.AlertType.ERROR, "The date of payment cannot occur before the date of creation").showAndWait();
            return;
        }

        ContractDTO newContract = new ContractDTO();
        newContract.setRoomId(previousContract.getRoomId());
        newContract.setTenantId(previousContract.getTenantId());
        newContract.setDuration(Integer.parseInt(tfDuration.getText()));
        newContract.setDateOfPayment(paymentDatePicker.getValue());
        newContract.setDateOfCreation(creationDatePicker.getValue());
        long id = contractService.save(newContract);
        Alert creationAlert = new Alert(Alert.AlertType.INFORMATION, String.format("NEW ID: CON%d", id));
        creationAlert.setHeaderText("SUCCESSFUL RENEWAL");
        creationAlert.showAndWait();
    }

    private boolean areAllFieldsEntered() {
        return creationDatePicker.getValue() != null && paymentDatePicker.getValue() != null && tfDuration.getText() != null && !tfDuration.getText().isEmpty();
    }

    @FXML
    public void initialize() {
        tfRoomId.setText("ROOM" + previousContract.getRoomId());
        tfTenantName.setText(personService.findPerson(previousContract.getTenantId()).getName());
        Tools.addNaturalNumberValidation(tfDuration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Tools.changeDateConverter(formatter,creationDatePicker);
        Tools.changeDateConverter(formatter,paymentDatePicker);
    }

    public static void setPreviousContract(ContractDTO previousContract) {
        RenewContractController.previousContract = previousContract;
    }
}
