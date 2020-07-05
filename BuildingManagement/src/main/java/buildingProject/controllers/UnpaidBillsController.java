package buildingProject.controllers;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.services.PersonService;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
public class UnpaidBillsController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final WaterBillService waterBillService;
    private final ElectricityBillService electricityBillService;
    private final PersonService personService;
    private final ViewFlow viewFlow;

    @FXML
    private TableView<BillDTO> tblBills;

    @FXML
    private TableColumn<BillDTO, String> colBillId;

    @FXML
    private TableColumn<BillDTO, String> colIssueDate;

    @FXML
    private TableColumn<BillDTO, String> colDueDate;

    @FXML
    private TableColumn<BillDTO, String> colAmount;

    @FXML
    private TableColumn<BillDTO, String> colRoomId;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXComboBox<String> cmbBillType;

    private final List<BillDTO> completeList = new ArrayList<>();

    public UnpaidBillsController(ApplicationContext applicationContext, FXMLResources fxmlResources, WaterBillService waterBillService, ElectricityBillService electricityBillService, PersonService personService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.waterBillService = waterBillService;
        this.electricityBillService = electricityBillService;
        this.personService = personService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleConfirmPayment(ActionEvent event) {
        BillDTO selectedBillDTO = tblBills.getSelectionModel().getSelectedItem();
        if (selectedBillDTO != null) {
            if(!selectedBillDTO.isPaid()){
                Alert confirmPayment = new Alert(Alert.AlertType.CONFIRMATION, "Confirming that bill has been paid today.Continue?", ButtonType.YES, ButtonType.NO);
                confirmPayment.setHeaderText("CONFIRM BILL PAYMENT");
                confirmPayment.resultProperty().addListener(observable -> {
                    if (confirmPayment.getResult() == ButtonType.YES) {
                        selectedBillDTO.setPaid(true);
                        selectedBillDTO.setDateOfPayment(LocalDate.now());
                        tblBills.getItems().remove(selectedBillDTO);
                        if (selectedBillDTO instanceof ElectricityBillDTO) {
                            electricityBillService.save(((ElectricityBillDTO) selectedBillDTO));
                        }else{
                            waterBillService.save(((WaterBillDTO) selectedBillDTO));
                        }
                        tblBills.refresh();
                    }
                });
                confirmPayment.showAndWait();
            }
        }
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
       viewFlow.goBack();
    }


    @FXML
    public void initialize() {
        completeList.clear();
        completeList.addAll(electricityBillService.findAllUnPaidBills());
        completeList.addAll(waterBillService.findAllUnPaidBills());

        colBillId.setComparator((billId1, billId2) ->{
            long id1 = Long.parseLong(billId1.substring(6));
            long id2 = Long.parseLong(billId2.substring(6));
            return Long.compare(id1, id2);
        } );

        colBillId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(formId(param.getValue())));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colIssueDate.setCellValueFactory(param ->{
            LocalDate issueDate = param.getValue().getDateOfIssue();
            if (issueDate == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            return new ReadOnlyObjectWrapper<>(issueDate.format(formatter));
        });
        colRoomId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>("ROOM" + param.getValue().getRoomId()));

        colDueDate.setCellValueFactory(param ->{
            LocalDate dueDate = param.getValue().getDateOfDue();
            if (dueDate == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            return new ReadOnlyObjectWrapper<>(dueDate.format(formatter));
        });

        colAmount.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(String.format("%.1f", param.getValue().getAmount())));

        tblBills.getSortOrder().add(colBillId);
        tblBills.getItems().addAll(completeList);

        cmbBillType.getItems().addAll("ALL","ELECTRICITY","WATER");
        cmbBillType.getSelectionModel().select(0);
        cmbBillType.getSelectionModel().selectedItemProperty().addListener(observable -> {
            tblBills.getItems().clear();
            List<BillDTO> newList = performTypeFilter(performSearch(tfSearch.getText(), completeList));
            tblBills.getItems().addAll(newList);
        });
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            tblBills.getItems().clear();
            List<BillDTO> newList = performSearch(newValue, performTypeFilter(completeList));
            tblBills.getItems().addAll(newList);
        });
    }

    private List<BillDTO> performSearch(String toSearch,List<BillDTO> listToSearch) {
        if (toSearch == null) {
            return listToSearch;
        }
        return listToSearch.stream().filter(billDTO -> {
            String billId = formId(billDTO);
            return billId.toLowerCase().contains(toSearch.toLowerCase());
        }).collect(Collectors.toList());
    }

    private List<BillDTO> performTypeFilter(List<BillDTO> listToFilter) {
        String type = cmbBillType.getSelectionModel().getSelectedItem();
        if (type.equals("ELECTRICITY")) {
            return listToFilter.stream().filter(billDTO -> billDTO instanceof ElectricityBillDTO).collect(Collectors.toList());
        } else if (type.equals("WATER")) {
            return listToFilter.stream().filter(billDTO -> billDTO instanceof WaterBillDTO).collect(Collectors.toList());
        }else{
            return listToFilter;
        }
    }

    private String formId(BillDTO billDTO) {
        if (billDTO instanceof ElectricityBillDTO) {
            return "E_BILL" + billDTO.getId();
        }else{
            return "W_BILL" + billDTO.getId();
        }
    }
}
