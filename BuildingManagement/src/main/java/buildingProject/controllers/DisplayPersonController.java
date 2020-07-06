package buildingProject.controllers;

import buildingProject.dto.PersonDTO;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.Tools;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static javafx.scene.control.Alert.AlertType;

@Component
public class DisplayPersonController {
    private static PersonDTO personDTO;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final PersonService personService;
    private final ViewFlow viewFlow;


    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField tfPhoneNumber;

    @FXML
    private JFXTextField tfNationalIdNumber;

    @FXML
    private JFXDatePicker dateOfBirth;

    @FXML
    private JFXTextField tfAge;

    @FXML
    private JFXRadioButton radMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton radFemale;

    @FXML
    private JFXTextField tfMaritalStatus;

    @FXML
    private JFXTextField tfName;

    @FXML
    private JFXTextField tfPersonId;

    @FXML
    private JFXTextField tfRoomId;

    public DisplayPersonController(ApplicationContext applicationContext, FXMLResources fxmlResources, PersonService personService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.personService = personService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
      viewFlow.goBack();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        personDTO.setName(tfName.getText());
        personDTO.setMaritalStatus(tfMaritalStatus.getText());
        personDTO.setPhoneNumber(tfPhoneNumber.getText());
        personDTO.setNationalIdNumber(tfNationalIdNumber.getText());
        char sex = radFemale.isSelected() ? 'F' : 'M';
        personDTO.setSex(sex);
        personDTO.setDateOfBirth(dateOfBirth.getValue());

        personService.savePerson(personDTO);
        new Alert(AlertType.INFORMATION, "Person Updated").showAndWait();

    }

    public void activateDisplayOnlyOption() {
        TextField[] textFields = {tfPhoneNumber, tfNationalIdNumber, tfNationalIdNumber, tfAge,
                tfMaritalStatus, tfName, tfPersonId, tfRoomId};
        Button[] buttons = {btnUpdate};
        for (Button button : buttons) {
            button.setVisible(false);
        }

        for (TextField textField : textFields) {
            textField.setEditable(false);
        }
        dateOfBirth.setEditable(false);
        radFemale.setDisable(true);
        radMale.setDisable(true);
    }

    @FXML
    public void initialize() {
        tfPersonId.setText("PERS" + personDTO.getId());
        tfName.setText(personDTO.getName());
        tfNationalIdNumber.setText(personDTO.getNationalIdNumber());
        tfPhoneNumber.setText(personDTO.getPhoneNumber());
        tfRoomId.setText("ROOM" + personDTO.getRoomId());
        tfMaritalStatus.setText(personDTO.getMaritalStatus());
        if (personDTO.getSex() == 'M') {
            radMale.setSelected(true);
        }else{
            radFemale.setSelected(true);
        }
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        Tools.changeDateConverter(formatter,dateOfBirth);
        if (personDTO.getDateOfBirth() != null) {
            LocalDate date = personDTO.getDateOfBirth();
            dateOfBirth.setValue(date);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            tfAge.setText("" + (currentYear - date.getYear()));
        }

        dateOfBirth.valueProperty().addListener(observable -> {
            LocalDate date = dateOfBirth.getValue();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            tfAge.setText("" + (currentYear - date.getYear()));
        });
    }

    public static void setPersonDTO(PersonDTO personDTO) {
        DisplayPersonController.personDTO = personDTO;
    }
}
