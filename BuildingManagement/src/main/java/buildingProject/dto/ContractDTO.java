package buildingProject.dto;


import lombok.Data;

import java.util.Date;

@Data
public class ContractDTO {
    private Long id;
    private Long tenantId;
    private Long roomId;
    private Date dateOfPayment;
    private Date dateOfCreation;
    private int duration;
}
