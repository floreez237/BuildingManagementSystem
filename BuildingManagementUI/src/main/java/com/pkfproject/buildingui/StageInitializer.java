package com.pkfproject.buildingui;

import com.pkfproject.buildingui.MainApp.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext applicationContext;
    private final Constants constants;
/*
    @Value("classpath:/test.fxml")
    private Resource resource;
*/

    public StageInitializer(ApplicationContext applicationContext, Constants constants) {
        this.applicationContext = applicationContext;
        this.constants = constants;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(constants.getFXML().getURL());
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent root = loader.load();
            Stage stage = stageReadyEvent.getStage();
            stage.setScene(new Scene(root, 400, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
