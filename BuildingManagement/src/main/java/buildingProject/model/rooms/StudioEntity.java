/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.rooms;

import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author flori
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "studio_table")
@EqualsAndHashCode(callSuper = true)
public class StudioEntity extends RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private double areaOfBedroom;
    private double areaOfToilet;
    private double areaOfKitchen;
    private double areaOfParlour;

    public StudioEntity(double areaOfBedroom, double areaOfToilet, double areaOfKitchen, double areaOfParlour, double price,
                        double caution, String paintColor, BuildingEntity building, BuildingLevelEntity level) {

        super(price, caution, paintColor, building, level);
        this.areaOfBedroom = areaOfBedroom;
        this.areaOfToilet = areaOfToilet;
        this.areaOfKitchen = areaOfKitchen;
        this.areaOfParlour = areaOfParlour;
    }

}
