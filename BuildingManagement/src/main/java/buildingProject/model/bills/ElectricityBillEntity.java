/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.bills;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author flori
 */

@Entity
@NoArgsConstructor
@Table(name = "electricity_bill_table")
@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricityBillEntity extends BillEntity {
    private static final long serialVersionUID = 2L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elec_bill_gen")
    @GenericGenerator(
            name = "elec_bill_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "elec_bill_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(updatable = false)
    private Long id;
}
