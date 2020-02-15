/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Rooms;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author flori
 */
public class Bedroom extends Room implements Serializable{

    private boolean isToiletInternal;
    private double area;

    public Bedroom(int levelNumber) {
        super(levelNumber);
    }

    public Bedroom(boolean isToiletInternal, double area, double price, double caution, String paintColor, String buildinID, int levelNumber) {
        super(price, caution, paintColor, buildinID, levelNumber);
        this.isToiletInternal = isToiletInternal;
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean isToiletInternal() {
        return isToiletInternal;
    }

    public void setIsToiletInternal(boolean isToiletInternal) {
        this.isToiletInternal = isToiletInternal;
    }

   
}
