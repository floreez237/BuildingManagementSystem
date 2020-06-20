package buildingProject.controllers;

import buildingProject.toolkit.FXMLResources;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddRoomController {

    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;

    @FXML
    private JFXRadioButton radStudio;

    @FXML
    private ToggleGroup tgRoomType;

    @FXML
    private JFXRadioButton radAppartment;

    @FXML
    private JFXRadioButton radBedroom;

    public AddRoomController(ApplicationContext applicationContext, FXMLResources fxmlResources) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
    }

    @FXML
    void handleProceed(ActionEvent event) throws IOException {
        FXMLLoader loader;
        if (radAppartment.isSelected()) {
            AddAppartmentController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            loader = new FXMLLoader(fxmlResources.getAddAppartmentResource().getURL());
        } else if (radStudio.isSelected()) {
            AddStudioController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            loader = new FXMLLoader(fxmlResources.getAddStudioResource().getURL());
        } else {
            addBedroomController.setLevelId(DisplayLevelController.getLevelDTO().getId());
            loader = new FXMLLoader(fxmlResources.getAddBedroomResource().getURL());
        }
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

}
