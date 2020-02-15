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
public class AppartmentServices {

    private Appartment apt;

    public AppartmentServices(Appartment apt) {
        this.apt = apt;
    }

    public void displayRoomInfo() {
        RoomServices roomService = new RoomServices(apt);
        roomService.displayRoomInfo();
        System.out.printf("Number of Bedrooms: %d\n", apt.getNumberOfBedrooms());
        for (int i = 0; i < apt.getAreasOfBedrooms().size(); i++) {
            Double area = apt.getAreasOfBedrooms().get(i);
            System.out.printf("\t\tArea Bedroom %d: %.1f\n", i + 1, area);

        }

        System.out.printf("Number of Kitchens: %d\n", apt.getNumberOfKitchens());
        for (int i = 0; i < apt.getAreasOfKitchens().size(); i++) {
            Double area = apt.getAreasOfKitchens().get(i);
            System.out.printf("\t\tArea Kitchen %d: %.1f\n", i + 1, area);

        }
        System.out.printf("Number of Parlours: %d\n", apt.getNumberOfParlours());
        for (int i = 0; i < apt.getAreasOfParlours().size(); i++) {
            Double area = apt.getAreasOfParlours().get(i);
            System.out.printf("\t\tArea Parlour %d: %.1f\n", i + 1, area);
        }

        System.out.printf("Number of Toilets: %d\n", apt.getNumberOfToilets());
        for (int i = 0; i < apt.getAreasOfToilets().size(); i++) {
            Double area = apt.getAreasOfToilets().get(i);
            System.out.printf("\t\tArea Toilet %d: %.1f\n", i + 1, area);
        }

    }

    public void addBedroom(double area) {
        apt.getAreasOfBedrooms().add(area);
    }

    public void addKitchen(double area) {
        apt.getAreasOfKitchens().add(area);
    }

    public void addParlour(double area) {
        apt.getAreasOfParlours().add(area);
    }

    public void addToilet(double area) {
            apt.getAreasOfToilets().add(area);
    }
}
