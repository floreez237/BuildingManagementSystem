package buildingProject.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractDTO {
    private Long id;
    private Long tenantId;
    private Long roomId;
    private LocalDate dateOfPayment;
    private LocalDate dateOfCreation;
    private int duration;
    private boolean isObsolete;

}
