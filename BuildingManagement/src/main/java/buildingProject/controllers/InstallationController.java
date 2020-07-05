package buildingProject.controllers;

import buildingProject.BootStrap;
import buildingProject.security.PasswordUtils;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.postgresql.util.PSQLException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.prefs.Preferences;

public class InstallationController {

    @FXML
    private JFXPasswordField tfPassword;

    @FXML
    private JFXPasswordField tfConfirmation;

    @FXML
    private JFXTextField tfDBUsername;

    @FXML
    private JFXPasswordField tfDBPassword;

    @FXML
    private JFXTextField tfDBUrl;

    private ConfigurableApplicationContext applicationContext;

    @FXML
    void handleCancel(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void handleSave(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (tfPassword.getText().isEmpty()) {
            alert.setContentText("Login Password not entered");
            alert.showAndWait();
            return;
        } else if (tfConfirmation.getText().isEmpty()) {
            alert.setContentText("Login Password Confirmation not entered");
            alert.showAndWait();
            return;
        } else if (tfDBUsername.getText().isEmpty()) {
            alert.setContentText("DB Username not entered");
            alert.showAndWait();
            return;
        } else if (tfDBPassword.getText().isEmpty()) {
            alert.setContentText("DB Password not entered");
            alert.showAndWait();
            return;
        } else if (tfDBUrl.getText().isEmpty()) {
            alert.setContentText("DB URL not entered");
            alert.showAndWait();
            return;
        } else if (!isPasswordValid()) {
            alert.setContentText("Login Password does not match confirmation");
            alert.showAndWait();
            return;
        }

        try {
            System.setProperty("spring.datasource.url", "jdbc:postgresql://" + tfDBUrl.getText());
            System.setProperty("spring.datasource.username", tfDBUsername.getText());
            System.setProperty("spring.datasource.password", tfDBPassword.getText());

            Preferences preferences = Preferences.userNodeForPackage(InstallationController.class);

            Platform.runLater(() -> tfConfirmation.getScene().setCursor(Cursor.WAIT));
            applicationContext = new SpringApplicationBuilder(BootStrap.class).run();
            applicationContext.publishEvent(new InitializationSuccessFulEvent(this));

            preferences.put("db_url", "jdbc:postgresql://" + tfDBUrl.getText());
            preferences.put("db_user", tfDBUsername.getText());
            preferences.put("db_password", tfDBPassword.getText());
            PasswordUtils.storePassword(tfPassword.getText());
            preferences.putBoolean("isInstalled", true);
            ((Stage) tfConfirmation.getScene().getWindow()).close();
        } catch (Exception e) {
            Exception rootException = getRootException(e);
            if (rootException.getClass() == PSQLException.class) {
                String message = rootException.getMessage();
                if (!message.contains("password")) {
                    alert.setContentText("Incorrect Database Url");
                } else {
                    alert.setContentText("Incorrect DB Credentials");
                }
            } else {
                alert.setContentText("AN UNEXPECTED ERROR HAS OCCURRED");
            }
            alert.showAndWait();
        }
        tfConfirmation.getScene().setCursor(Cursor.DEFAULT);

    }

    private Exception getRootException(Exception e) {
        Exception originalCause = e;
        while (originalCause.getCause() != null) {
            originalCause = ((Exception) originalCause.getCause());
        }
        return originalCause;
    }

    private boolean isPasswordValid() {
        return tfPassword.getText().equals(tfConfirmation.getText());
    }


    public static class InitializationSuccessFulEvent extends ApplicationEvent {
        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public InitializationSuccessFulEvent(Object source) {
            super(source);
        }
    }
}
