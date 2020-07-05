package buildingProject;

import buildingProject.controllers.InstallationController;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.prefs.Preferences;

@SpringBootApplication
public class BootStrap {
    public static void main(String[] args) {
        Preferences preferences = Preferences.userNodeForPackage(InstallationController.class);
        if (!preferences.getBoolean("isInstalled", false)) {
            Application.launch(InstallationApp.class);
        } else {
            System.setProperty("spring.datasource.url", preferences.get("db_url", ""));
            System.setProperty("spring.datasource.username", preferences.get("db_user", ""));
            System.setProperty("spring.datasource.password", preferences.get("db_password", ""));
            Application.launch(MainApp.class);
        }

    }
}
