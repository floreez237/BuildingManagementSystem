/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import MenuUI.Tools;
import Model.*;
import Model.Rooms.*;
import java.util.*;

/**
 *
 * @author flori
 */
public class BuildingServices {

    Building building;

    public BuildingServices(Building building) {
        this.building = building;
    }

    public void displayBuildingInfo() {
        System.out.printf("ID: %s\n", building.getBuildingID());
        System.out.printf("Name: %s\n", Tools.formatString(building.getBuildingName()));
        System.out.printf("Location: %s\n", Tools.formatString(building.getLocation()));
        System.out.printf("Number of Levels: %d\n", building.getNumberOflevels());
        System.out.printf("Number of Free Rooms: %d\n", this.getNumberOfFreeRooms());
        System.out.printf("Number of Occupied Rooms: %d\n", this.getNumberOfOccupiedRooms());

        displayExtras();
    }

    public void displayExtras() {
        System.out.println("Extras: ");
        if (building.getBuildingExtra().isEmpty()) {
            System.out.println("\tNO EXTRA");
        }

        building.getBuildingExtra().forEach((extra) -> {
            System.out.println("\t" + Tools.formatString(extra));
        });
    }

    public final void addMultipleLevels(int numberOfLevels) {
        for (int i = 0; i < numberOfLevels; i++) {
            this.addLevel();
        }
    }

    public void addLevel() {
        Level newLevel = new Level(getNumberOflevels());
        newLevel.setBuildingId(building.getBuildingID());
        building.getListOfLevels().add(newLevel);

    }

    public int getNumberOflevels() {
        return building.getListOfLevels().size();
    }

    public void addRoom(int levelNumber, Room room) {
        Level level = building.getListOfLevels().get(levelNumber - 1);
        LevelServices levelServices = new LevelServices(level);
        levelServices.addRoom(room);

    }

    public int getNumberOfFreeRooms() {
        return building.getListOfLevels().stream().mapToInt((level) -> {
            LevelServices levelServices = new LevelServices(level);
            return levelServices.getNumberOfFreeRooms();
        }).sum();
    }

    public int getNumberOfOccupiedRooms() {
        return building.getListOfLevels().stream().mapToInt((level) -> {
            LevelServices levelServices = new LevelServices(level);
            return levelServices.getNumberOfOccupiedRooms();
        }).sum();
    }

    public void removeBuilding() {
        Building.getListOfBuildings().remove(building);
    }

    public boolean addExtra(String extra) {
        return building.getBuildingExtra().add(extra.toLowerCase());
    }

    public boolean removeExtra(String extra) {
        return building.getBuildingExtra().remove(extra);
    }

    public void addPersonToList(Person person) {
        building.getListOfPersons().add(person);
    }

    public Person researchPersonById(String personId) {
        for (Person person : building.getListOfPersons()) {
            if (person.getPersonId().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public void addContractToList(Contract contract) {
        building.getListOfContracts().add(contract);
    }

    public Contract searchContractById(String contractId) {
        for (Contract contract : building.getListOfContracts()) {
            if (contract.getContractId().equals(contractId)) {
                return contract;
            }
        }
        return null;
    }

    public void removePerson(Person person) {
        building.getListOfPersons().remove(person);
    }

    public Person selectPerson() {
        this.displayAllPersons();
        System.out.print("\n\nEnter an ID: ");
        String personId = Tools.getID("PER");
        return researchPersonById(personId);
    }

    public void displayAllPersons() {
        PersonServices.displayPersonsInList(building.getListOfPersons());
    }

    public List<Person> getListOfPersons() {
        return building.getListOfPersons();
    }

    public Contract selectContract() {
        this.displayAllContract();
        System.out.println("Enter the Id of the contract");
        String contractId = Tools.getID("CONT");
        return this.searchContractById(contractId);

    }

    public void displayAllContract() {
        BuildingServices.displayListOfContracts(building.getListOfContracts());
    }

    public List<Contract> listOfExpiredContracts() {
        List<Contract> listOfExpiredContracts = new ArrayList<>();
        building.getListOfContracts().stream().filter((contract) -> {
            ContractServices contractServices = new ContractServices(contract);
            return contractServices.isContractValid();
        }).forEachOrdered((contract) -> {
            listOfExpiredContracts.add(contract);
        });
        return listOfExpiredContracts;

    }

    public void removeContract(Contract contract) {
        building.getListOfContracts().remove(contract);
    }

    public static Building researchBuildingById(String ID) {
        for (Building building : Building.getListOfBuildings()) {
            if (building.getBuildingID().equals(ID)) {
                return building;
            }
        }
        return null;
    }

    public static HashSet<Building> researchBuildingByLocation(String location) {
        HashSet<Building> setOfBuildings = new HashSet<>();
        Building.getListOfBuildings().forEach((building) -> {
            if (building.getLocation().equals(location)) {
                setOfBuildings.add(building);
            }
        });
        return setOfBuildings;
    }

    public static HashSet<Building> researchBuildingByLevels(int numberOfLevels) {
        HashSet<Building> setOfBuildings = new HashSet<>();
        Building.getListOfBuildings().forEach((building) -> {
            if (building.getNumberOflevels() == numberOfLevels) {
                setOfBuildings.add(building);
            }
        });
        return setOfBuildings;

    }

    public static Building researchBuildingByName(String name) {
        for (Building building : Building.getListOfBuildings()) {
            if (building.getBuildingName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public static List<Building> getListOfBuildings() {
        return new ArrayList<>(Building.getListOfBuildings());
    }

    public static void removeBuilding(String buildingId) {
        Building.getListOfBuildings().removeIf((building) -> {
            return building.getBuildingID().equals(buildingId);
        });

    }

    public static void addBuildingToList(Building building) {
        Building.getListOfBuildings().add(building);
    }

    public static void displayAllBuilding() {
        if (Building.getListOfBuildings().isEmpty()) {
            System.out.println("No Building in List");
            return;
        }
        displayBuildingList(Building.getListOfBuildings());
    }

    public static void displayBuildingWithFreeRooms() {
        long countBuildingsWIthFreeRooms
                = Building.getListOfBuildings().stream().filter((building) -> {
                    BuildingServices buildingServices = new BuildingServices(building);
                    return buildingServices.getNumberOfFreeRooms() > 0;
                }).count();

        if (countBuildingsWIthFreeRooms
                == 0) {
            System.out.println("No buildings with free rooms.");
            return;
        }

        System.out.println(
                "Building Id\t\tName\t\t    Location\t\tLevels\t\tTotal Free Rooms");
        System.out.println(
                "-----------\t\t----\t\t    --------\t\t------\t\t----------------");
        Building.getListOfBuildings()
                .stream().map((building) -> {
                    System.out.print(building.getBuildingID() + "\t\t\t");
                    return building;
                }
                ).map(
                        (building) -> {
                            System.out.printf("%-20s%-20s", Tools.formatString(building.getBuildingName()), Tools.formatString(building.getLocation()));
                            return building;
                        }
                ).forEachOrdered(
                        (building) -> {
                            BuildingServices buildingServices = new BuildingServices(building);
                            System.out.print(building.getNumberOflevels() + "\t\t" + buildingServices.getNumberOfFreeRooms() + "\n");
                        }
                );

    }

    public static void displayBuildingList(List<Building> listOfBuildings) {
        if (listOfBuildings.isEmpty()) {
            System.out.println("No Building in List");
            return;
        }
        System.out.println("Building Id\t\tName\t\t    Location\t\tLevels\t\tTotal Free Rooms\tTotal Occupied Rooms");

        listOfBuildings.stream().forEachOrdered((building) -> {
            BuildingServices buildingServices = new BuildingServices(building);
            System.out.print(building.getBuildingID() + "\t\t\t");
            System.out.printf("%-20s%-20s", Tools.formatString(building.getBuildingName()), Tools.formatString(building.getLocation()));
            System.out.print(building.getNumberOflevels() + "\t\t" + buildingServices.getNumberOfFreeRooms() + "\t\t\t" + buildingServices.getNumberOfOccupiedRooms() + "\n");
        });
    }

    public static void displayListOfContracts(List<Contract> listOfContracts) {

        if (listOfContracts.isEmpty()) {
            System.out.println("No contracts in List");
            return;
        }
        System.out.printf("Contract Id\tRoom Id\t\tTenant name\t\t\tDate of Creation\t\tDate of payment\t\tRent duration(Months)\t\tExpiration date\n");
        System.out.println("-----------\t-------\t\t-----------\t\t\t----------------\t\t--------------\t\t---------------------\t\t---------------\n");
        listOfContracts.forEach((contract) -> {
            ContractServices contractServices = new ContractServices(contract);
            System.out.print(contract.getContractId() + "\t\t" + contract.getRoomId() + "\t\t");
            System.out.printf("%-32s", contract.getTenant().getName());
            Tools.displayDate(contract.getDateOfCreation());
            System.out.printf("%10s", " ");
            Tools.displayDate(contract.getDateOfPayment());
            System.out.print("\t");
            System.out.printf("%-24d", contract.getDuration());
            Tools.displayDate(contractServices.getExpirationDate());
            System.out.println("\n");
        });
    }

    public static void addMultipleBuildings(List<Building> listOfBuildings) {
        Building.getListOfBuildings().addAll(listOfBuildings);
    }

    public static Building selectBuildingId() {
        displayAllBuilding();
        System.out.print("\n\nEnter a Building ID: ");
        String buildingId = Tools.getID("BUILD");
        return researchBuildingById(buildingId);
    }

}
