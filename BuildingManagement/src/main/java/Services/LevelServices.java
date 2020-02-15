/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.*;
import Model.Rooms.*;
import MenuUI.*;
import java.util.*;

/**
 *
 * @author flori
 */
public class LevelServices {

    private Level level;

    public LevelServices(Level level) {
        this.level = level;
    }

    public void addRoom(Room room) {
        room.setBuildingID(level.getBuildingId());
        level.getListOfRooms().add(room);
    }

    public int getNumberOfRooms() {
        return level.getListOfRooms().size();
    }

    public int getNumberOfFreeRooms() {
        int numberOfFreeRooms;
        numberOfFreeRooms = (int) level.getListOfRooms().stream().filter((room)
                -> (!room.isOccupied())).count();
        return numberOfFreeRooms;
    }

    public int getNumberOfOccupiedRooms() {
        int numberOfOccupiedRooms;

        numberOfOccupiedRooms = (int) level.getListOfRooms().stream().filter((room)
                -> (room.isOccupied())).count();
        return numberOfOccupiedRooms;
    }

    public boolean removeRoom(String roomId) {
        return level.getListOfRooms().removeIf((room) -> room.getId().equals(roomId));
    }

    public void displayAllRooms() {

        if (level.getListOfRooms().isEmpty()) {
            System.out.println("No room at this Level");
            return;
        }
        System.out.println("Room Id\t\tRoom Type\t\tRent(FCFA)\t\tDeposit(FCFA)"
                + "\t\t\tStatus");
        level.getListOfRooms().forEach((room) -> {
            System.out.printf("%s\t\t%-24s%-24.1f%-32.1f%s\n", room.getId(),
                    new RoomServices(room).getType(), room.getRent(), room.getDeposit(),
                    (room.isOccupied()) ? "Occupied" : "Free");
        });
    }

    public void displayFreeRooms() {
        System.out.println("Room Id\t\tRoom Type\t\tRent(FCFA)\t\tDeposit(FCFA)\n");
        level.getListOfRooms().stream().filter((room) -> !room.isOccupied()).forEach((room) -> {
            System.out.printf("%s\t\t%-24s%-24.1f%.1f\n", room.getId(),
                    new RoomServices(room).getType(), room.getRent(), room.getDeposit());
        });
    }

    public void displayOccupiedRooms() {
        System.out.println("Room Id\t\tRoom Type\t\tRent(FCFA)\t\tDeposit(FCFA)\n");
        level.getListOfRooms().stream().filter((room) -> room.isOccupied()).forEach((room) -> {
            System.out.printf("%s\t\t%-24s%-24.1f%.1f\n", room.getId(),
                    new RoomServices(room).getType(), room.getRent(), room.getDeposit());
        });
    }

    public Room researchRoomById(String roomId) {
        for (Room room : level.getListOfRooms()) {
            if (room.getId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Room selectRoomId() {
        displayAllRooms();
        System.out.print("\n\nEnter an ID: ");
        String roomId = Tools.getID("R");
        return researchRoomById(roomId);
    }
}
