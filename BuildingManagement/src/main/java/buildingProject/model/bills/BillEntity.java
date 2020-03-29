/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.bills;

import buildingProject.model.rooms.RoomEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author flori
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public class BillEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_gen")
    @GenericGenerator(
            name = "bill_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "bill_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(updatable = false)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfIssue;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfDue;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfPayment;
    private double amount;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    public BillEntity(double amount) {
        this.amount = amount;
    }


}
