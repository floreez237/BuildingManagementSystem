/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.services.ContractService;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
import buildingProject.services.rooms.RoomService;
import buildingProject.toolkit.FXMLResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */

@Component
public class DashboardController{

    private static final NumberAxis revenue = new NumberAxis(0, 1000000, 100000);
    private static final CategoryAxis MonthAxis = new CategoryAxis();

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final ElectricityBillService electricityBillService;
    private final WaterBillService waterBillService;
    private final ContractService contractService;
    private final RoomService roomService;

    @FXML
    private final LineChart<String, Number> lineChart = new LineChart<>(MonthAxis, revenue);

    @FXML
    private PieChart piechart;
    @FXML
    private Label lblUnpaidWater;

    @FXML
    private Label lblUnPaidElectricity;

    @FXML
    private Label lblExpireInFiveDays;

    @FXML
    private Label lblExpiredContracts;

    public DashboardController(FXMLResources fxmlResources, ApplicationContext applicationContext, ElectricityBillService electricityBillService, WaterBillService waterBillService, ContractService contractService, RoomService roomService) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.electricityBillService = electricityBillService;
        this.waterBillService = waterBillService;
        this.contractService = contractService;
        this.roomService = roomService;
    }

    @FXML
    void displayExpiredContracts(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getExpiredContractsResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());

    }

    @FXML
    void displayBills(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getUnpaidBillsResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    public void initialize() {
        //initialise the pie chart with the values of free and occupied rooms
        //for all the buildings registered

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Occupied", roomService.countOccupiedRooms()),
                new PieChart.Data("Free", roomService.countFreeRooms()));

        piechart.setData(pieChartData);

        lblUnPaidElectricity.setText("" + electricityBillService.countUnPaidBills());
        lblUnpaidWater.setText("" + waterBillService.countUnPaidBills());
        lblExpiredContracts.setText("" + contractService.countExpiredContracts());
        lblExpireInFiveDays.setText("" + contractService.countExpireIn(5));

        //lineChart = new LineChart<>(MonthAxis, revenue);
        //initialise the line chart with the revenue of the different months
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");
        series.getData().add(new XYChart.Data<>("Jan", 10000));
        series.getData().add(new XYChart.Data<>("Feb", 550000));
        series.getData().add(new XYChart.Data<>("March", 700000));
        series.getData().add(new XYChart.Data<>("Apr", 360000));
        series.getData().add(new XYChart.Data<>("May", 300000));
        series.getData().add(new XYChart.Data<>("Jun", 28000));
        series.getData().add(new XYChart.Data<>("Jul", 800000));
        series.getData().add(new XYChart.Data<>("Aug", 815000));
        series.getData().add(new XYChart.Data<>("Sep", 150000));
        series.getData().add(new XYChart.Data<>("Oct", 310000));
        series.getData().add(new XYChart.Data<>("Nov", 900000));
        series.getData().add(new XYChart.Data<>("Dec", 640000));

        lineChart.getData().add(series);
    }

}
