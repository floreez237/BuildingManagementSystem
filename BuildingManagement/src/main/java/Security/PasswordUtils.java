/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;


import java.util.prefs.Preferences;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author flori
 */
public class PasswordUtils {
    
    public static void storePassword(String password){
        String hashString = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Preferences userPreferences = Preferences.userNodeForPackage(PasswordUtils.class);
        userPreferences.put("buildPassword", hashString);
        
    }
    
    public static boolean checkPassword(String password){
        Preferences userPreferences = Preferences.userNodeForPackage(PasswordUtils.class);
        String hashedString = userPreferences.get("buildPassword", null);
        return BCrypt.checkpw(password, hashedString);
    }
}
