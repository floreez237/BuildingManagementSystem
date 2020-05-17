package com.pkfproject.buildingui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(BuildingUI.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage primaryStage) {
            super(primaryStage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
