/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Model.Bill;
import Model.Building;
import Model.Contract;
import Model.Person;
import Model.Rooms.*;
import MenuUI.Tools;
import java.util.stream.Collectors;
/**
 *
 * @author flori
 */
public class RoomServices {
     private Room room;

    public RoomServices(Room room) {
        this.room = room;
    }
     
    public void displayRoomInfo() {
        System.out.println("ROOM INFO: ");
        System.out.println("---------\n");
        System.out.printf("Room ID: %s\n", room.getId());
        System.out.printf("Room Type: %s\n", getType());
        System.out.printf("Level Number: %s\n", room.getLevelNumber());
        System.out.printf("Status: %s\n", room.isOccupied() ? "Occupied" : "Free");
        System.out.printf("Rent(FCFA): %.1f\n", room.getRent());
        System.out.printf("Deposit(FCFA): %.1f\n", room.getDeposit());
        System.out.printf("Paint Color: %s\n", room.getPaintColor());

        displayAdditionalRooms();
        displayFurniture();
        displayPersons();
    }

    public void displayFurniture() {
        System.out.println("Furniture:");
        room.getSetOfFurniture().stream().forEach((furniture) -> {
            System.out.println("\t" + Tools.formatString(furniture));
        });
    }

    public void displayAdditionalRooms() {
        if (!room.getAdditionalRooms().isEmpty()) {
            System.out.println("Additional Rooms: ");
            room.getAdditionalRooms().entrySet().forEach((rom) -> {
                System.out.printf("\tRoom:%s\t\tArea:%.1f\n", Tools.formatString(rom.getKey()), rom.getValue());
            });
        }

    }

    public void displayPersons() {
        if (!room.getSetOfPersons().isEmpty()) {
            System.out.println("Persons in room: ");
            room.getSetOfPersons().forEach((person) -> {
                System.out.printf("\t\tID: %s\t\tName: %s\n", person.getPersonId(), person.getName());
            });
        }
    }
    
    public void addAdditionalRoom(String name, double area) {
        room.getAdditionalRooms().put(name.toLowerCase(), area);// provide error management for if the name already exists
    }

    public void addPerson(Person person) {
        room.getSetOfPersons().add(person);
       // Building.researchBuildingById(room.getBuildingID()).addPersonToList(person);
        
    }

    public void addWaterBill(Bill waterBill) {
        room.getWaterBills().add(waterBill);
    }

    public void addElectricityBill(Bill electricityBill) {
        room.getElectricityBills().add(electricityBill);
    }

    public boolean addFurniture(String furniture) {
        return room.getSetOfFurniture().add(furniture.toLowerCase());
    }

    public void removeAdditionalRoom(String name) {
        room.getAdditionalRooms().remove(name.toLowerCase());
    }

    public boolean removeFurniture(String furniture) {
        return room.getSetOfFurniture().remove(furniture);
    }

    public void addContract(Contract contract) {
        room.getListOfContract().add(contract);
    }
  
    //change parameter to ID
    public boolean removePerson(String personId) {

        return room.getSetOfPersons().removeIf((person) -> {
            return person.getPersonId().equals(personId); //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    public String getType() {
        if (room instanceof Appartment) {
            return "Appartment";
        } else if (room instanceof Bedroom) {
            return "Bedroom";
        } else {
            return "Studio";
        }
    }

    public void initialiseBills() {
        room.getElectricityBills().clear();
        room.getWaterBills().clear();
    }

    public void displayAllBills() {
        System.out.println("Water Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getWaterBills().forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });

        System.out.println("\n\nElectricity Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getElectricityBills().forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });
    }

    public void displayPaidBills() {
        System.out.println("Water Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getWaterBills().stream().filter((bill) -> bill.isPaid()).forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });

        System.out.println("\n\nElectricity Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getElectricityBills().stream().filter((bill) -> bill.isPaid()).forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });
    }

    public void displayUnpaidBills() {
        System.out.println("Water Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getWaterBills().stream().filter((bill) -> !bill.isPaid()).forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });

        System.out.println("\n\nElectricity Bills");
        System.out.println("-----------");
        System.out.println("Bill ID\t\tIssue Date\t\tDue Date\t\tAmount\t\t");
        room.getElectricityBills().stream().filter((bill) -> !bill.isPaid()).forEach((bill) -> {
            System.out.printf("%s\t\t%s\t\t%s\t\t%.1f\n\n", bill.getBillId(), bill.getDateOfIssue().toString(),
                    bill.getDateOfDue().toString(), bill.getAmount());
        });
    }
    
    public Bill researchBillById(String billId){
        for (Bill waterBill : room.getWaterBills()) {
            if (waterBill.getBillId().equals(billId)) {
                return waterBill;
            }
            
        }
        for (Bill electricityBill : room.getElectricityBills()) {
            if(electricityBill.getBillId().equals(billId)){
                return electricityBill;
            }
        }
        return null;
    }
    
    public Bill researchUnpaidBillById(String billId){
        for (Bill waterBill : room.getWaterBills().stream().filter((bill) -> !bill.isPaid()).collect(Collectors.toList())) {
            if (waterBill.getBillId().equals(billId)) {
                return waterBill;
            }
            
        }
        for (Bill electricityBill : room.getElectricityBills().stream().filter((bill) -> !bill.isPaid()).collect(Collectors.toList())) {
            if(electricityBill.getBillId().equals(billId)){
                return electricityBill;
            }
        }
        return null;
    }
}
