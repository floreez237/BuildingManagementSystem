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
import java.util.Date;

/**
 *
 * @author flori
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "contract_table")
public class ContractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private PersonEntity tenant;
    private RoomEntity room;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfPayment;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfCreation;
    private int duration;

    public ContractEntity(RoomEntity room, int duration, Date dateOfPayment, PersonEntity tenant) {
        
        this.tenant = tenant;
        this.room = room;
        this.dateOfPayment = dateOfPayment;
        this.duration = duration;
        this.dateOfCreation = new Date();

    }


}
