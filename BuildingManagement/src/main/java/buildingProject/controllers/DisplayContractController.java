package buildingProject.controllers;

import buildingProject.dto.ContractDTO;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class DisplayContractController {
    private static ContractDTO contractDTO;
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final PersonService personService;
    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;
    @FXML
    private JFXTextField tfContractId;

    @FXML
    private JFXTextField tfRoomId;

    @FXML
    private JFXTextField tfDateOfPayment;

    @FXML
    private JFXTextField tfDateOfCreation;

    @FXML
    private JFXTextField tfDuration;

    @FXML
    private JFXTextField tfExpirationDate;

    @FXML
    private JFXTextField tfTenantName;

    public DisplayContractController(ApplicationContext applicationContext, FXMLResources fxmlResources, PersonService personService) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.personService = personService;
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getContractsManagementResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleDisplayTenant(ActionEvent event) throws IOException {
        DisplayPersonController.setPersonDTO(personService.findPerson(contractDTO.getTenantId()));
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayPersonResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(logo.getURL().toString()));
        stage.show();
        DisplayPersonController controller = loader.getController();
        controller.activateDisplayOnlyOption();
    }

    @FXML
    void handleRenew(ActionEvent event) throws IOException {
        RenewContractController.setPreviousContract(contractDTO);
        FXMLLoader loader = new FXMLLoader(fxmlResources.getRenewContractResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfContractId.setText("CON" + contractDTO.getId());
        tfDateOfCreation.setText(contractDTO.getDateOfCreation().format(formatter));
        tfDateOfPayment.setText(contractDTO.getDateOfPayment().format(formatter));
        tfRoomId.setText("ROOM" + contractDTO.getRoomId());
        tfDuration.setText("" + contractDTO.getDuration());
        tfExpirationDate.setText(contractDTO.getDateOfPayment().plusMonths(contractDTO.getDuration()).format(formatter));
        tfTenantName.setText(personService.findPerson(contractDTO.getTenantId()).getName());
    }

    public static void setContractDTO(ContractDTO contractDTO) {
        DisplayContractController.contractDTO = contractDTO;
    }
}
