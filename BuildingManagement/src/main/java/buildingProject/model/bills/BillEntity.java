/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.bills;

import buildingProject.model.rooms.RoomEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
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
