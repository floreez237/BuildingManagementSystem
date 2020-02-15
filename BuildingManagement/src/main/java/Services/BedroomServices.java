/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Rooms.*;
/**
 *
 * @author flori
 */
public class BedroomServices {
    private Bedroom bedroom;

    public BedroomServices(Bedroom bedroom) {
        this.bedroom = bedroom;
    }
    
    public void displayRoomInfo() {
        RoomServices roomService = new RoomServices(bedroom);
        roomService.displayRoomInfo();
        System.out.printf("Area: %.1f\n", bedroom.getArea());
        System.out.println("Position of toilets: " + ((bedroom.isToiletInternal()) ? "Internal" : "External"));
    }
    
}
