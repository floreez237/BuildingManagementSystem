package buildingProject.controllers;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.GlobalConstants;
import buildingProject.toolkit.Tools;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class DisplayBillController {

    private static BillDTO billDTO;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final ElectricityBillService electricityBillService;
    private final WaterBillService waterBillService;

    @FXML
    private JFXTextField tfBillId;

    @FXML
    private JFXTextField tfBillType;

    @FXML
    private JFXTextField tfAmount;

    @FXML
    private Label lblCurrency;

    @FXML
    private JFXDatePicker issueDatePicker;

    @FXML
    private JFXDatePicker dueDatePicker;

    @FXML
    private JFXRadioButton radYes;

    @FXML
    private JFXRadioButton radNo;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private JFXDatePicker paymentDatePicker;

    public DisplayBillController(ApplicationContext applicationContext, FXMLResources fxmlResources, ElectricityBillService electricityBillService, WaterBillService waterBillService) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.electricityBillService = electricityBillService;
        this.waterBillService = waterBillService;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayAllBillsResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        if (!areAllFieldsEntered()) {
            new Alert(Alert.AlertType.ERROR,"SOME FIELDS ARE NOT ENTERED").showAndWait();
            return;
        }

        if (dueDatePicker.getValue().isBefore(issueDatePicker.getValue())) {
            new Alert(Alert.AlertType.ERROR, "Due date cannot occur before issue date.").showAndWait();
            return;
        }

        if (paymentDatePicker.isVisible() && paymentDatePicker.getValue().isBefore(issueDatePicker.getValue())) {
            new Alert(Alert.AlertType.ERROR, "Due date cannot occur before payment date.").showAndWait();
            return;
        }
        billDTO.setPaid(radYes.isSelected());
        billDTO.setDateOfPayment(paymentDatePicker.getValue());
        billDTO.setDateOfDue(dueDatePicker.getValue());
        billDTO.setDateOfIssue(issueDatePicker.getValue());
        billDTO.setAmount(Double.parseDouble(tfAmount.getText()));
        if (tfBillType.getText().equals("Electricity")) {
            electricityBillService.save(((ElectricityBillDTO) billDTO));
        }else{
            waterBillService.save(((WaterBillDTO) billDTO));
        }
        new Alert(Alert.AlertType.INFORMATION, "BILL UPDATED").showAndWait();
    }

    private boolean areAllFieldsEntered() {
        boolean isPaymentDateEntered = (paymentDatePicker.isVisible() && paymentDatePicker.getValue() != null) || !paymentDatePicker.isVisible();
        return issueDatePicker.getValue() != null && dueDatePicker.getValue() != null && !tfAmount.getText().isEmpty() && isPaymentDateEntered;
    }

    @FXML
    public void initialize() {
        lblCurrency.setText(GlobalConstants.currencySymbol);
        String billPrefix = billDTO instanceof ElectricityBillDTO ? "E_BILL" : "W_BILL";
        tfBillId.setText(billPrefix + billDTO.getId());
        String billType = billPrefix.equals("E_BILL") ? "Electricity" : "Water";
        tfBillType.setText(billType);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Tools.changeDateConverter(formatter, dueDatePicker);
        Tools.changeDateConverter(formatter, issueDatePicker);
        Tools.changeDateConverter(formatter,paymentDatePicker);
        paymentDatePicker.setValue(billDTO.getDateOfPayment());
        if (billDTO.getDateOfIssue() != null) {
            issueDatePicker.setValue(billDTO.getDateOfDue());
        }

        if (billDTO.getDateOfDue() != null) {
            dueDatePicker.setValue(billDTO.getDateOfDue());
        }

        tfAmount.setText(String.format("%.1f",billDTO.getAmount()));

        radYes.selectedProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                setPaymentDateVisible(radYes.isSelected());
            }
        });
        radYes.setSelected(billDTO.isPaid());
        setPaymentDateVisible(billDTO.isPaid());

    }

    public void setPaymentDateVisible(boolean visible) {
        lblPaymentDate.setVisible(visible);
        paymentDatePicker.setVisible(visible);
    }

    public static void setBillDTO(BillDTO billDTO) {
        DisplayBillController.billDTO = billDTO;
    }
}
