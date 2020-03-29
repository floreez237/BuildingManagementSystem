/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.rooms;

import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;

/**
 * @author flori
 */
@Data
@Entity
@Table(name = "room_table")
@Inheritance(strategy = InheritanceType.JOINED)
//it causes some stack overflow error during fetching
@EqualsAndHashCode(exclude = {"additionalRooms", "setOfPersons", "setOfFurniture", "waterBills", "electricityBills"})
public abstract class RoomEntity implements Serializable {
    /*
    todo add appropriate column attributes for each attributes in all entities
    should write function to remove last added bill? Discuss with Yas.
    */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_gen")
    @GenericGenerator(
            name = "room_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "room_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private boolean occupied;
    private double rent;
    private double deposit;
    private String paintColor;

    @ElementCollection
    @CollectionTable(name = "additional_rooms", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<AdditionalRoom> additionalRooms = new HashSet<>();

    @OneToMany(mappedBy = "room")
    private Set<PersonEntity> setOfPersons = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingEntity building;

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private BuildingLevelEntity level;

    @ElementCollection
    @CollectionTable(name = "furniture_table", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<Furniture> setOfFurniture = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @Cascade(ALL)
    private Set<WaterBillEntity> waterBills = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @Cascade(ALL)
    private Set<ElectricityBillEntity> electricityBills = new HashSet<>();

    protected RoomEntity() {
    }

    protected RoomEntity(double price, double caution, String paintColor, BuildingEntity building, BuildingLevelEntity level) {
        this.rent = price;
        this.deposit = caution;
        this.paintColor = paintColor;
        this.building = building;
        this.level = level;
    }


}
