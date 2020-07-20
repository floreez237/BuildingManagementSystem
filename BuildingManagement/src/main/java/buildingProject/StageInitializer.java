package buildingProject;

import buildingProject.toolkit.FXMLResources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static buildingProject.MainApp.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;

    public StageInitializer(ApplicationContext applicationContext, FXMLResources fxmlResources) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(fxmlResources.getLoginResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Scene scene1 = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(logo.getURL().toString()));
            stage.setScene(scene1);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
