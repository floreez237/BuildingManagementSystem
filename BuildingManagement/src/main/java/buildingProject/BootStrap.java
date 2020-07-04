package buildingProject;

import buildingProject.controllers.InstallationController;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.prefs.Preferences;

@SpringBootApplication
public class BootStrap {
    public static void main(String[] args) {
        Preferences userPreferences = Preferences.userNodeForPackage(InstallationController.class);
        if (!userPreferences.getBoolean("isInstalled", false)) {
            Application.launch(InstallationApp.class);

        }
//        Application.launch(MainApp.class);

    }
}
