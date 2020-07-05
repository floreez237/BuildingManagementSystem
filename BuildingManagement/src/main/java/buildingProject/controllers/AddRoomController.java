package buildingProject.controllers;

import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddRoomController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    private final ViewFlow viewFlow;

    @FXML
    private JFXRadioButton radStudio;

    @FXML
    private JFXRadioButton radAppartment;

    @FXML
    private JFXRadioButton radBedroom;

    public AddRoomController(ApplicationContext applicationContext, FXMLResources fxmlResources, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleProceed(ActionEvent event) throws IOException {
        Resource next;
        if (radAppartment.isSelected()) {
            AddAppartmentController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            next = fxmlResources.getAddAppartmentResource();
        } else if (radStudio.isSelected()) {
            AddStudioController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            next = fxmlResources.getAddStudioResource();
        } else {
            AddBedroomController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            next = fxmlResources.getAddBedroomResource();
        }
        viewFlow.loadResource(fxmlResources.getDisplayLevelResource(),next);
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

}
