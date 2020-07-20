package buildingProject.controllers;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.GlobalConstants;
import buildingProject.toolkit.Tools;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AddBillController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final WaterBillService waterBillService;
    private final ElectricityBillService electricityBillService;
    private final ViewFlow viewFlow;

    private static Long roomId;

    @FXML
    private JFXDatePicker issueDatePicker;

    @FXML
    private JFXDatePicker dueDatePicker;

    @FXML
    private JFXTextField tfAmount;

    @FXML
    private JFXComboBox<String> cmbBillType;

    @FXML
    private Label lblCurrency;

    public AddBillController(ApplicationContext applicationContext, FXMLResources fxmlResources, WaterBillService waterBillService, ElectricityBillService electricityBillService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.waterBillService = waterBillService;
        this.electricityBillService = electricityBillService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
         viewFlow.goBack();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (!areAllFieldsEntered()) {
            new Alert(Alert.AlertType.ERROR, "NOT ALL FIELDS WERE ENTERED").showAndWait();
            return;
        }

        if (dueDatePicker.getValue().isBefore(issueDatePicker.getValue())) {
            new Alert(Alert.AlertType.ERROR, "Due date cannot occur before issue date.").showAndWait();
            return;
        }

        if (issueDatePicker.getValue().isAfter(LocalDate.now())) {
            new Alert(Alert.AlertType.ERROR, "Issue date cannot occur before date of today.").showAndWait();
            return;
        }

        BillDTO billDTO;
        if (cmbBillType.getSelectionModel().getSelectedIndex() == 0) {
            billDTO = new ElectricityBillDTO();
        } else {
            billDTO = new WaterBillDTO();
        }
        billDTO.setRoomId(roomId);
        billDTO.setPaid(false);
        billDTO.setDateOfIssue(issueDatePicker.getValue());
        billDTO.setDateOfDue(dueDatePicker.getValue());
        billDTO.setAmount(Double.parseDouble(tfAmount.getText()));

        long id;
        if (billDTO instanceof ElectricityBillDTO) {
            id = electricityBillService.create(((ElectricityBillDTO) billDTO));
            Alert billSaved = new Alert(Alert.AlertType.INFORMATION, String.format("Bill ID: E_BILL%d", id));
            billSaved.setHeaderText("Electricity Bill Saved.");
            billSaved.showAndWait();
        } else {
            id = waterBillService.create(((WaterBillDTO) billDTO));
            Alert billSaved = new Alert(Alert.AlertType.INFORMATION, String.format("Bill ID: W_BILL%d", id));
            billSaved.setHeaderText("Water Bill Saved.");
            billSaved.showAndWait();
        }

    }

    private boolean areAllFieldsEntered() {
        return tfAmount.getText() != null && !tfAmount.getText().isEmpty() && dueDatePicker.getValue() != null && dueDatePicker.getValue() != null;
    }

    @FXML
    public void initialize() {
        lblCurrency.setText(GlobalConstants.currencySymbol);
        cmbBillType.getItems().addAll("Electricity", "Water");
        cmbBillType.getSelectionModel().selectFirst();
        Tools.addDoubleValidation(tfAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Tools.changeDateConverter(formatter, issueDatePicker);
        Tools.changeDateConverter(formatter, dueDatePicker);

    }

    public static void setRoomId(Long roomId) {
        AddBillController.roomId = roomId;
    }
}
