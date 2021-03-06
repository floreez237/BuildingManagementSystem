/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model;

import buildingProject.model.embeddables.BuildingExtra;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;

/**
 * @author flori
 */
//@SuppressWarnings
@Data
@NoArgsConstructor
@Entity
@Table(name = "building_table")
@EqualsAndHashCode(exclude = {"listOfLevels", "buildingExtraSet"})
public class BuildingEntity implements Serializable {

    private static final long serialVersionUID = 11L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_gen")
    @GenericGenerator(
            name = "building_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "building_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;


    private String buildingName;
    private String location;

    @OneToMany(mappedBy = "building",orphanRemoval = true)
    @Cascade(ALL)
    private List<BuildingLevelEntity> listOfLevels = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "building_extras")
    /*@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", referencedColumnName = "id")*/
    private Set<BuildingExtra> buildingExtraSet = new HashSet<>();


    public BuildingEntity(String buildingName, String location) {
        this.buildingName = buildingName;
        this.location = location;

        //numberOflevels = 0;

    }


}
