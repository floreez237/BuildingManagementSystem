/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.rooms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flori
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "appartment_table")
@EqualsAndHashCode(exclude = {"areasOfBedrooms", "areasOfKitchens", "areasOfParlours", "areasOfToilets"}, callSuper = true)
public class AppartmentEntity extends RoomEntity {
    private static final long serialVersionUID = 7L;

    @ElementCollection
    @CollectionTable(name = "bedroom_areas", joinColumns = @JoinColumn(name = "apt_id", referencedColumnName = "id"))
    private List<Double> areasOfBedrooms = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "kitchen_areas", joinColumns = @JoinColumn(name = "apt_id", referencedColumnName = "id"))
    private List<Double> areasOfKitchens = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "parlour_areas", joinColumns = @JoinColumn(name = "apt_id", referencedColumnName = "id"))
    private List<Double> areasOfParlours = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "toilet_areas", joinColumns = @JoinColumn(name = "apt_id", referencedColumnName = "id"))
    private List<Double> areasOfToilets = new ArrayList<>();

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
