/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author YASMINE
 */
public class MainApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        /*Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));

        Scene scene1 = new Scene(root);
        stage = new Stage(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/resources/images/icons8-building-with-top-view-24.png")));
        stage.setScene(scene1);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();*/
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(BootStrap.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
