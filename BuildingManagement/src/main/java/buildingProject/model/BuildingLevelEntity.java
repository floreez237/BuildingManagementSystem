/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * K
 *
 * @author flori
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "buildinglevel_table")
public class BuildingLevelEntity implements Serializable {
    private static final long serialVersionUID = 12L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_level_gen")
    @GenericGenerator(
            name = "building_level_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "building_level_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private Long levelNumber;
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingEntity building;


    public BuildingLevelEntity(Long levelNumber) {
        this.levelNumber = levelNumber;
    }


}
