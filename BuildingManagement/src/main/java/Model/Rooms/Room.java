/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Rooms;

import Model.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author flori
 */
public abstract class Room implements Serializable{
    //TODO think about identifier to remove a bill
    //should write function to remove last added bill? Discuss with Yas.

    private static long globalRoomId = 1;

    public static long getGlobalRoomId() {
        return globalRoomId;
    }

    public static void setGlobalRoomId(long aGlobalId) {
        globalRoomId = aGlobalId;
    }
    
    private List<Contract> ListOfContract;
    private String Id;
    private String buildingID;
    private int levelNumber;
    private boolean occupied;
    private Set<String> setOfFurniture;
    private double rent;
    private double deposit;
    private String paintColor;
    private Map<String, Double> additionalRooms;
    private Set<Person> setOfPersons;
    private List<Bill> waterBills;
    private List<Bill> electricityBills;

    protected Room(int levelNumber) {
        setOfFurniture = new HashSet<>();
        additionalRooms = new HashMap<>();
        setOfPersons = new HashSet<>();
        waterBills = new ArrayList<>();
        electricityBills = new ArrayList<>();
        ListOfContract = new ArrayList<>();

        this.levelNumber = levelNumber;
        Id = "R" + globalRoomId; // in the program we may ask user to enter room in format
        // B#L#R# for us to search

        globalRoomId++;

    }

    protected Room(double price, double caution, String paintColor, String buildinID, int levelNumber) {
        this.rent = price;
        this.deposit = caution;
        this.paintColor = paintColor;
        this.buildingID = buildinID;
        this.levelNumber = levelNumber;

        Id = "R" + globalRoomId; // in the program we may ask user to enter room in format
        // B#L#R# for us to search

        globalRoomId++;

        setOfFurniture = new HashSet<>();
        additionalRooms = new HashMap<>();
        setOfPersons = new HashSet<>();
        waterBills = new ArrayList<>();
        electricityBills = new ArrayList<>();
        ListOfContract = new ArrayList<>();
    }

    public String getId() {
        return Id;
    }

    public String getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(String paintColor) {
        this.paintColor = paintColor;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public Set<String> getSetOfFurniture() {

        return setOfFurniture;
    }

    public Map<String, Double> getAdditionalRooms() {
        return additionalRooms;
    }

    public Set<Person> getSetOfPersons() {
        return setOfPersons;
    }

    public List<Bill> getWaterBills() {
        return waterBills;
    }

    public List<Bill> getElectricityBills() {
        return electricityBills;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public List<Contract> getListOfContract() {
        return ListOfContract;
    }
    
    
}
