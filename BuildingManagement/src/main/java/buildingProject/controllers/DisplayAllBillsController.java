package buildingProject.controllers;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.dto.rooms.RoomDTO;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType;

@SuppressWarnings("DuplicatedCode")
@Component
public class DisplayAllBillsController {

    private static Long roomId;

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final WaterBillService waterBillService;
    private final ElectricityBillService electricityBillService;
    private final RoomService roomService;
    private final ViewFlow viewFlow;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<BillDTO> tblBills;

    @FXML
    private TableColumn<BillDTO, String> colBillId;

    @FXML
    private TableColumn<BillDTO, String> colType;

    @FXML
    private TableColumn<BillDTO, String> colIssueDate;

    @FXML
    private TableColumn<BillDTO, String> colDueDate;

    @FXML
    private TableColumn<BillDTO, String> colAmount;

    @FXML
    private TableColumn<BillDTO, String> colStatus;

    @FXML
    private JFXComboBox<String> cmbBillType;

    private final List<BillDTO> completeList = new ArrayList<>();

    public DisplayAllBillsController(ApplicationContext applicationContext, FXMLResources fxmlResources, WaterBillService waterBillService, ElectricityBillService electricityBillService, RoomService roomService, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.waterBillService = waterBillService;
        this.electricityBillService = electricityBillService;
        this.roomService = roomService;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleGoBack (ActionEvent event) throws IOException {
      viewFlow.goBack();
    }

    @FXML
    void handleAddBill(ActionEvent event) throws IOException {
        AddBillController.setRoomId(roomId);
        viewFlow.loadResource(fxmlResources.getDisplayAllBillsResource(),fxmlResources.getAddBillResource());
    }

    @FXML
    void handleConfirmPayment(ActionEvent event) {
        BillDTO selectedBillDTO = tblBills.getSelectionModel().getSelectedItem();
        if (selectedBillDTO != null) {
            if(!selectedBillDTO.isPaid()){
                Alert confirmPayment = new Alert(AlertType.CONFIRMATION, "Confirming that bill has been today.Continue?", ButtonType.YES, ButtonType.NO);
                confirmPayment.setHeaderText("CONFIRM BILL PAYMENT");
                confirmPayment.resultProperty().addListener(observable -> {
                    if (confirmPayment.getResult() == ButtonType.YES) {
                        selectedBillDTO.setPaid(true);
                        selectedBillDTO.setDateOfPayment(LocalDate.now());
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
    void handleDeleteBill(ActionEvent event) {
        BillDTO selectedDto = tblBills.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            Alert deleteAlert = new Alert(AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
            deleteAlert.setHeaderText("DELETE BILL");
            deleteAlert.resultProperty().addListener(observable -> {
                if (deleteAlert.getResult() == ButtonType.YES) {
                    if (selectedDto instanceof ElectricityBillDTO) {
                        electricityBillService.deleteAllByRoomId(selectedDto.getId());
                    }else {
                        waterBillService.delete(selectedDto.getId());
                    }
                    tblBills.getItems().remove(selectedDto);
                }
            });
            deleteAlert.showAndWait();
        }
    }

    @FXML
    void handleDisplayBill(ActionEvent event) throws IOException {
        BillDTO selectedDto = tblBills.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayBillController.setBillDTO(selectedDto);
            viewFlow.loadResource(fxmlResources.getDisplayAllBillsResource(),fxmlResources.getDisplayBillResource());
        }

    }

    @FXML
    public void initialize() {
        RoomDTO roomDTO = roomService.getRoom(roomId);
        completeList.clear();
        completeList.addAll(waterBillService.findAllBills(roomDTO.getListOfWaterBillIDs()));
        completeList.addAll(electricityBillService.findAllBills(roomDTO.getListOfElectricityBillIds()));
        colBillId.setComparator((billId1, billId2) ->{
            long id1 = Long.parseLong(billId1.substring(6));
            long id2 = Long.parseLong(billId2.substring(6));
            return Long.compare(id1, id2);
        } );

        colBillId.setCellValueFactory(param -> {
            BillDTO billDTO = param.getValue();
            String billId;
            if (billDTO instanceof WaterBillDTO) {
                billId = "W_BILL" + billDTO.getId();
            }else{
                billId = "E_BILL" + billDTO.getId();
            }
            return new ReadOnlyObjectWrapper<>(billId);
        });

        colType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(getType(param.getValue())));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colIssueDate.setCellValueFactory(param ->{
            LocalDate issueDate = param.getValue().getDateOfIssue();
            if (issueDate == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            return new ReadOnlyObjectWrapper<>(issueDate.format(formatter));
        });

        colDueDate.setCellValueFactory(param ->{
            LocalDate dueDate = param.getValue().getDateOfDue();
            if (dueDate == null) {
                return new ReadOnlyObjectWrapper<>("");
            }
            return new ReadOnlyObjectWrapper<>(dueDate.format(formatter));
        });

        colAmount.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(String.format("%.1f", param.getValue().getAmount())));

        colStatus.setCellValueFactory(param -> {
            String status = param.getValue().isPaid() ? "PAID" : "NOT PAID";
            return new ReadOnlyObjectWrapper<>(status);
        });

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
            String billId;
            if (billDTO instanceof WaterBillDTO) {
                billId = "w_bill" + billDTO.getId();
            }else{
                billId = "e_bill" + billDTO.getId();
            }
            return billId.contains(toSearch.toLowerCase());
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

    private String getType(BillDTO billDTO) {
        if (billDTO instanceof ElectricityBillDTO) {
            return "Electricity";
        }else{
            return "Water";
        }
    }

    public static void setRoomDTO(Long roomId) {
        DisplayAllBillsController.roomId = roomId;
    }
}
