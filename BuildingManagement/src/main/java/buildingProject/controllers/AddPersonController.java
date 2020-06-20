package buildingProject.controllers;

import buildingProject.dto.PersonDTO;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.Tools;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static buildingProject.toolkit.Tools.formatString;
import static buildingProject.toolkit.Tools.isNaturalNumber;
import static javafx.scene.control.Alert.AlertType;

@Component
public class AddPersonController {

    public static Long roomId;

    private final PersonService personService;
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    @FXML
    private JFXTextField tfPhone;

    @FXML
    private JFXTextField tfNationalId;

    @FXML
    private JFXDatePicker dateOfBirth;

    @FXML
    private JFXRadioButton radFemale;

    @FXML
    private JFXTextField tfName;

    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfMaritalStatus;

    public AddPersonController(PersonService personService, FXMLResources fxmlResources, ApplicationContext applicationContext) {
        this.personService = personService;
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        DisplayAllPersonsController.initializeCompleteList(personService.findAllByRoomId(roomId));
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayAllPersonsResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleSave(ActionEvent event) {
        String name = formatString(tfName.getText());
        LocalDate birthDate = dateOfBirth.getValue();
        String nationalId = tfNationalId.getText();
        char sex = radFemale.isSelected() ? 'F' : 'M';
        String maritalStatus = formatString(tfMaritalStatus.getText());
        String phoneNumber = tfPhone.getText();

        Alert alert = new Alert(AlertType.ERROR, "One or more of the fields was not entered");
        alert.setHeaderText("MISSING FIELD");
        String[] allStrings = {name, nationalId, maritalStatus, phoneNumber};
        for (String string : allStrings) {
            if (string.isEmpty()) {
                alert.showAndWait();
                return;
            }
        }

        if (!isNaturalNumber(nationalId)) {
            new Alert(AlertType.ERROR, "NATIONAL ID ENTERED IS INVALID").showAndWait();
            return;
        }

        if (!isNaturalNumber(phoneNumber)) {
            new Alert(AlertType.ERROR, "PHONE NUMBER ENTERED IS INVALID").showAndWait();
            return;
        }

        if (birthDate == null) {
            alert.showAndWait();
            return;
        }

        PersonDTO personDTO = new PersonDTO();
        personDTO.setSex(sex);
        personDTO.setRoomId(roomId);
        personDTO.setNationalIdNumber(nationalId);
        personDTO.setName(name);
        personDTO.setDateOfBirth(birthDate);
        personDTO.setPhoneNumber(phoneNumber);
        personDTO.setMaritalStatus(maritalStatus);

        Long personId = personService.createPerson(personDTO);
        new Alert(AlertType.INFORMATION, String.format("PERSON CREATED WITH ID: PERS%d", personId)).showAndWait();
        reInitializeFields();
    }

    private void reInitializeFields(){
        JFXTextField[] textFields = {tfName,tfPhone,tfNationalId,tfMaritalStatus};
        for (JFXTextField textField : textFields) {
            textField.clear();
        }
    }

    @FXML
    public void initialize() {
        tfRoomId.setText("ROOM" + roomId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Tools.changeDateConverter(formatter, dateOfBirth);
    }

    public static void setRoomId(Long roomId) {
        AddPersonController.roomId = roomId;
    }
}
