/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.toolkit.FXMLResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */

@Component
public class DashboardController implements Initializable {

    final NumberAxis revenue = new NumberAxis(0, 1000000, 100000);
    final CategoryAxis MonthAxis = new CategoryAxis();

    private final FXMLResources fxmlResources;

    @FXML
    private final LineChart<String, Number> lineChart = new LineChart<>(MonthAxis, revenue);

    @FXML
    private PieChart piechart;

    public DashboardController(FXMLResources fxmlResources) {
        this.fxmlResources = fxmlResources;
    }

    @FXML
    void displayExpiredContracts(MouseEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        Pane expiredContracts = FXMLLoader.load(fxmlResources.getExpiredContractsResource().getURL());
        borderPane.setCenter(expiredContracts);

    }

    @FXML
    void displaybills(MouseEvent event) throws IOException {
        BorderPane borderPane = MainViewController.getGlobalMainPage();
        AnchorPane unpaidBills = FXMLLoader.load(fxmlResources.getUnpaidBillsResource().getURL());
        borderPane.setCenter(unpaidBills);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialise the pie chart with the values of free and occupied rooms
        //for all the buildings registered

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Occupied", 2), new PieChart.Data("Free", 10));

        piechart.setData(pieChartData);

        //initialise the line chart with the revenue of the different months
        Series<String, Number> series = new Series<>();
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
