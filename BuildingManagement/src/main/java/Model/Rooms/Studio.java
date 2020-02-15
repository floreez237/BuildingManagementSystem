/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Rooms;

import java.io.Serializable;

/**
 *
 * @author flori
 */
public class Studio extends Room implements Serializable{

    private double areaOfBedroom;
    private double areaOfToilet;
    private double areaOfKitchen;
    private double areaOfParlour;

    public Studio(double areaOfBedroom, double areaOfToilet, double areaOfKitchen, double areaOfParlour, double price,
            double caution, String paintColor, String buildinID, int levelNumber) {
        
        super(price, caution, paintColor, buildinID, levelNumber);
        this.areaOfBedroom = areaOfBedroom;
        this.areaOfToilet = areaOfToilet;
        this.areaOfKitchen = areaOfKitchen;
        this.areaOfParlour = areaOfParlour;
    }

    public Studio(int levelNumber) {
        super(levelNumber);
    }

    public double getAreaOfBedroom() {
        return areaOfBedroom;
    }

    public void setAreaOfBedroom(double areaOfBedroom) {
        this.areaOfBedroom = areaOfBedroom;
    }

    public double getAreaOfToilet() {
        return areaOfToilet;
    }

    public void setAreaOfToilet(double areaOfToilet) {
        this.areaOfToilet = areaOfToilet;
    }

    public double getAreaOfKitchen() {
        return areaOfKitchen;
    }

    public void setAreaOfKitchen(double areaOfKitchen) {
        this.areaOfKitchen = areaOfKitchen;
    }

    public double getAreaOfParlour() {
        return areaOfParlour;
    }

    public void setAreaOfParlour(double areaOfParlour) {
        this.areaOfParlour = areaOfParlour;
    }

   

   
}
