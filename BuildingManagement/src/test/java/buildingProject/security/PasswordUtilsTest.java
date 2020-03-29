package buildingProject.security;

import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordUtilsTest {


    @Test
    void storePassword() {
        String password = "MyPassword";
        PasswordUtils.storePassword(password);
        assertTrue(PasswordUtils.checkPassword(password));
        Preferences userPreferences = Preferences.userNodeForPackage(PasswordUtils.class);
        userPreferences.remove("buildPassword");
        assertNull(userPreferences.get("buildPassword", null));
    }


}