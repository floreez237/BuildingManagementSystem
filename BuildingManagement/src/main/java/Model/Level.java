/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Rooms.*;
import java.io.Serializable;
import java.util.*;

/**
 * K
 *
 * @author flori
 */
public class Level implements Serializable {

    private int levelNumber;
    private String buildingId;
    private List<Room> listOfRooms;

    public Level(int levelNumber) {
        this.listOfRooms = new ArrayList<>();
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Room> getListOfRooms() {
        return listOfRooms;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

}
