/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import buildingProject.toolkit.FXMLResources;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */

@Component
public class MainViewController implements Initializable {

    public static BorderPane globalMainPage;
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    @FXML
    private BorderPane mainPage;

    public MainViewController(FXMLResources fxmlResources, ApplicationContext applicationContext) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
    }

    public static BorderPane getGlobalMainPage() {
        return globalMainPage;
    }

    @FXML
    void handleDashboardBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDashboardResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Pane view = loader.load();
        mainPage.setCenter(view);
    }

    @FXML
    void handleExitBtn(ActionEvent event) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setHeaderText("Confirm Exit");
        alert1.setContentText("Are you sure you wish to quit ?");
        alert1.resultProperty().addListener(new ChangeListener<ButtonType>() {

            @Override
            public void changed(ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) {
                if (newValue == ButtonType.OK) {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
        });
        alert1.showAndWait();
    }

    @FXML
    void handleHelpBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getHelpResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        AnchorPane view = loader.load();
        mainPage.setCenter(view);
    }

    @FXML
    void handleSettingsBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getSettingsResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        AnchorPane view = loader.load();
        mainPage.setCenter(view);
    }

    @FXML
    void handleBuildingManagementBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getBuildingManagementResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Pane view = loader.load();
        mainPage.setCenter(view);
    }

    @FXML
    void handleContractManagementBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getContractsManagementResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        AnchorPane view = loader.load();
        mainPage.setCenter(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane view;
        try {
            FXMLLoader loader = new FXMLLoader(fxmlResources.getDashboardResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            view = loader.load();
            mainPage.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        globalMainPage = mainPage;
    }

}
