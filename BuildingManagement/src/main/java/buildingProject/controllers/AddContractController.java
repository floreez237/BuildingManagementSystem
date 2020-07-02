package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.ContractService;
import buildingProject.services.PersonService;
import buildingProject.services.rooms.RoomService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.Tools;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Controller
public class AddContractController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final ContractService contractService;
    private final RoomService roomService;
    private final PersonService personService;
    private final ViewFlow viewFlow;

    @FXML
    private JFXDatePicker paymentDatePicker;

    @FXML
    private JFXDatePicker creationDatePicker;

    @FXML
    private JFXComboBox<String> cmbRoomId;

    @FXML
    private JFXComboBox<String> cmbPersonId;

    @FXML
    private JFXTextField tfDuration;

    public AddContractController(ApplicationContext applicationContext, FXMLResources fxmlResources, ContractService contractService, RoomService roomService, PersonService personService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.contractService = contractService;
        this.roomService = roomService;
        this.personService = personService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
       viewFlow.goBack();
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (!areAllFieldsEntered()) {
            new Alert(Alert.AlertType.ERROR, "Some fields are not entered.").showAndWait();
            return;
        }
        if (paymentDatePicker.getValue().isBefore(creationDatePicker.getValue())) {
            new Alert(Alert.AlertType.ERROR, "The date of payment cannot occur before the date of creation").showAndWait();
            return;
        }
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setDateOfPayment(paymentDatePicker.getValue());
        contractDTO.setDateOfCreation(creationDatePicker.getValue());
        contractDTO.setDuration(Integer.parseInt(tfDuration.getText()));
        long roomId = Long.parseLong(cmbRoomId.getValue().substring(4));
        contractDTO.setRoomId(roomId);
        contractDTO.setTenantId(Long.parseLong(cmbPersonId.getValue().substring(4)));
        contractDTO.setObsolete(false);

        long id = contractService.save(contractDTO);
        Alert creationAlert = new Alert(Alert.AlertType.INFORMATION, String.format("ID: CON%d", id));
        creationAlert.setHeaderText("CONTRACT CREATED");
        cmbRoomId.getItems().remove(cmbRoomId.getSelectionModel().getSelectedItem());
        creationAlert.showAndWait();
    }

    private boolean areAllFieldsEntered() {
        return creationDatePicker.getValue()!=null && paymentDatePicker.getValue()!=null && tfDuration.getText()!=null && !tfDuration.getText().isEmpty();
    }

    @FXML
    public void initialize() {
        cmbRoomId.getItems().addAll(roomService.findAllFreeRooms().stream().map(roomDTO -> "ROOM" + roomDTO.getId()).collect(Collectors.toList()));
        cmbPersonId.getItems().addAll(personService.findAll().stream().map(personDTO -> "PERS" + personDTO.getId()).collect(Collectors.toList()));
        cmbPersonId.getSelectionModel().selectFirst();
        cmbRoomId.getSelectionModel().selectFirst();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Tools.changeDateConverter(formatter, creationDatePicker);
        Tools.changeDateConverter(formatter, paymentDatePicker);
        Tools.addNaturalNumberValidation(tfDuration);
        Tools.addValidationToComboBox(cmbRoomId);
        Tools.addValidationToComboBox(cmbPersonId);
    }

}
