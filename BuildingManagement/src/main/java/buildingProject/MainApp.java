/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.hibernate.exception.GenericJDBCException;
import org.postgresql.util.PSQLException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author YASMINE
 */
public class MainApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init() {
        try {
            applicationContext = new SpringApplicationBuilder(BootStrap.class).run();
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n" + getRootException(e).getMessage());
            stop();
        }
    }

    @Override
    public void stop() {
        if (applicationContext != null) {
            applicationContext.close();
        }
        Platform.exit();
    }

    public Exception getRootException(Exception e) {
        Exception originalCause = e;
        while (originalCause.getCause() != null) {
            originalCause = ((Exception) originalCause.getCause());
        }
        return originalCause;
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
