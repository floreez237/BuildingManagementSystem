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

/**
 *
 * @author flori
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "bedroom_table")
@EqualsAndHashCode(callSuper = true)
public class BedroomEntity extends RoomEntity {
    private static final long serialVersionUID = 8L;
    
    private boolean isToiletInternal;
    private double area;

    public BedroomEntity(boolean isToiletInternal, double area, double price, double caution, String paintColor, BuildingEntity building, BuildingLevelEntity level) {
        super(price, caution, paintColor, building, level);
        this.isToiletInternal = isToiletInternal;
        this.area = area;
    }


}
