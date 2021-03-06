/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model;

import buildingProject.model.rooms.RoomEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author flori
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "contract_table")
public class ContractEntity implements Serializable {

    private static final long serialVersionUID = 13L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_gen")
    @GenericGenerator(
            name = "contract_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "contract_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity tenant;
    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;
    private LocalDate dateOfPayment;
    private LocalDate dateOfCreation;
    private int duration;
    private boolean isObsolete;

    public ContractEntity(RoomEntity room, int duration, LocalDate dateOfPayment, PersonEntity tenant) {

        this.tenant = tenant;
        this.room = room;
        this.dateOfPayment = dateOfPayment;
        this.duration = duration;
        this.dateOfCreation = LocalDate.now();

    }


}
