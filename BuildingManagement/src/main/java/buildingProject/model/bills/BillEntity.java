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
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author flori
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public class BillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate dateOfIssue;

    private LocalDate dateOfDue;

    private LocalDate dateOfPayment;
    private double amount;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    public BillEntity(double amount) {
        this.amount = amount;
    }


}
