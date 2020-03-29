/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.bills;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author flori
 */
@Entity
@NoArgsConstructor
@Table(name = "water_bill_table")
public class WaterBillEntity extends BillEntity {


}
