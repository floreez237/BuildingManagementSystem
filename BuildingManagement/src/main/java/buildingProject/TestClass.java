package buildingProject;

import buildingProject.controllers.InstallationController;

import java.util.prefs.Preferences;

//class which implements command line runner for testing
public class TestClass {
    public static void main(String[] args) {
        Preferences preferences = Preferences.userNodeForPackage(InstallationController.class);
        preferences.remove("isInstalled");
        preferences.remove("db_user");
        preferences.remove("db_password");
        preferences.remove("db_url");
    }
}