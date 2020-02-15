/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.prefs.Preferences;


/**
 *
 * @author flori
 */
public class TestClass {
     public static void main(String[] args){
         
        Preferences rootPreferences = Preferences.userNodeForPackage(BuildingManagementSystem.class);
         System.out.println(rootPreferences.get("test", null));
    }
    
}
