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
public class StudioServices {
    private Studio studio;

    public StudioServices(Studio studio) {
        this.studio = studio;
    }
    
    public void displayRoomInfo() {
        RoomServices roomService = new RoomServices(studio);
        roomService.displayRoomInfo();
        System.out.printf("Area of Bedroom: %.1f\n",studio.getAreaOfBedroom());
        System.out.printf("Area of Parlour: %.1f\n",studio.getAreaOfParlour());
        System.out.printf("Area of Toilet: %.1f\n",studio.getAreaOfToilet());
        System.out.printf("Area of Kitchen: %.1f\n",studio.getAreaOfKitchen());//To change body of generated methods, choose Tools | Templates.
    }
}
