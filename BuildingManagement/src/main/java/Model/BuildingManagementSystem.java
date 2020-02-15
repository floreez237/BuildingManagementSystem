/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.prefs.*;


/**
 *
 * @author YASMINE
 */

public class BuildingManagementSystem {
    //je maime
    /**
     * @param args the command line arguments 
     * @throws java.text.ParseException 
     */

    public static void main(String[] args){
        Preferences rootPreferences = Preferences.userNodeForPackage(BuildingManagementSystem.class);
        System.out.println(rootPreferences.get("test", null));
        System.out.println(rootPreferences.absolutePath());
    }
    
   
    }