/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.model.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author flori
 */

@Data
@NoArgsConstructor
@Embeddable
public class AdditionalRoom implements Serializable {
    private static final long serialVersionUID = 4L;
    private String name;
    private double area;

}
