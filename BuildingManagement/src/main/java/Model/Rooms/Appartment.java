/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Rooms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author flori
 */
public class Appartment extends Room implements Serializable{

    private List<Double> areasOfBedrooms;
    private List<Double> areasOfKitchens;
    private List<Double> areasOfParlours;
    private List<Double> areasOfToilets;
    private int numberOfBedrooms;
    private int numberOfKitchens;
    private int numberOfParlours;
    private int numberOfToilets;

    public Appartment(int levelNumber) {
        super(levelNumber);
        areasOfBedrooms = new ArrayList<>();
        areasOfKitchens = new ArrayList<>();
        areasOfParlours = new ArrayList<>();
        areasOfToilets = new ArrayList<>();

    }

    public Appartment(int numberOfBedrooms, int numberOfKitchens, int numberOfParlours, int numberOfToilets, double rent, double deposit, String paintColor, String buildinID, int levelNumber) {
        super(rent, deposit, paintColor, buildinID, levelNumber);
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfKitchens = numberOfKitchens;
        this.numberOfParlours = numberOfParlours;
        this.numberOfToilets = numberOfToilets;

        if (areasOfBedrooms == null) {
            areasOfBedrooms = new ArrayList<>();
        }

        if (areasOfKitchens == null) {
            areasOfKitchens = new ArrayList<>();
        }

        if (areasOfParlours == null) {
            areasOfParlours = new ArrayList<>();
        }

        if (areasOfToilets == null) {
            areasOfToilets = new ArrayList<>();
        }
    }

    public List<Double> getAreasOfBedrooms() {
        return Collections.unmodifiableList(areasOfBedrooms);
    }

    public List<Double> getAreasOfKitchens() {
        return Collections.unmodifiableList(areasOfKitchens);
    }

    public List<Double> getAreasOfParlours() {
        return Collections.unmodifiableList(areasOfParlours);
    }

    public List<Double> getAreasOfToilets() {
        return Collections.unmodifiableList(areasOfToilets);
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public int getNumberOfKitchens() {
        return numberOfKitchens;
    }

    public void setNumberOfKitchens(int numberOfKitchens) {
        this.numberOfKitchens = numberOfKitchens;
    }

    public int getNumberOfParlours() {
        return numberOfParlours;
    }

    public void setNumberOfParlours(int numberOfParlours) {
        this.numberOfParlours = numberOfParlours;
    }

    public int getNumberOfToilets() {
        return numberOfToilets;
    }

    public void setNumberOfToilets(int numberOfToilets) {
        this.numberOfToilets = numberOfToilets;
    }

    public void addBedroom(double area) {
        areasOfBedrooms.add(area);
    }

    public void addKitchen(double area) {
        areasOfKitchens.add(area);
    }

    public void addParlour(double area) {
        areasOfParlours.add(area);
    }

    public void addToilet(double area) {
        areasOfToilets.add(area);
    }

}
