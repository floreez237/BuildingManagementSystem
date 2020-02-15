/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.io.Serializable;
import java.util.*;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author flori
 */
//@SuppressWarnings
public class Building implements Serializable {
//this is another test

    /*TODO funtions to get buildings with available rooms
      also to add and remove rooms in building
      add level
     */
    private static List<Building> listOfBuildings = new ArrayList<>();

    private List<Contract> listOfContracts;
    private static long globalBuildingID = 1;
    private int numberOflevels;
    private String buildingID;
    private String buildingName;
    private String location;
    private List<Level> listOfLevels;
    private Set<String> buildingExtra;
    private List<Person> listOfPersons;

  
    public Building(String buildingName, String location) {
        listOfLevels = new ArrayList<>();
        listOfPersons = new ArrayList<>();
        buildingExtra = new HashSet<>();
        listOfContracts = new ArrayList<>();
        this.buildingName = buildingName;
        this.location = location;
        buildingID = "BUILD" + globalBuildingID;
        globalBuildingID++;
        numberOflevels = 0;

    }

    



    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName.toLowerCase();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location.toLowerCase();
    }

    public int getNumberOflevels() {
        return numberOflevels;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public List<Level> getListOfLevels() {
        return listOfLevels;
    }

    public Set<String> getBuildingExtra() {
        return Collections.unmodifiableSet(buildingExtra);
    }

 
   
    public List<Contract> getListOfContracts() {
        return listOfContracts;
    }

   
     
      public List<Person> getListOfPersons() {
        return listOfPersons;
    }

   
    public static List<Building> getListOfBuildings() {
        return listOfBuildings;
    }



    public static long getGlobalBuildingID() {
        return globalBuildingID;
    }


    public static void setGlobalBuildingID(long aGlobalBuildingID) {
        globalBuildingID = aGlobalBuildingID;
    }

   
}
