package buildingProject;

import buildingProject.toolkit.FXMLResources;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static buildingProject.controllers.InstallationController.InitializationSuccessFulEvent;

@Component
public class InitializationComplete implements ApplicationListener<InitializationSuccessFulEvent> {

    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;

    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;

    public InitializationComplete(FXMLResources fxmlResources, ApplicationContext applicationContext) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(InitializationSuccessFulEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(fxmlResources.getMainViewResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Scene scene1 = new Scene(root);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(logo.getURL().toString()));
            stage.setScene(scene1);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "AN ERROR HAS OCCURED");
            alert.showAndWait();
            Platform.exit();
        }
    }
}
