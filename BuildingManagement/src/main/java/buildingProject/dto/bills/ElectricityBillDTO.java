package buildingProject.dto.bills;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ElectricityBillDTO {
    private Long id;
    private Date dateOfIssue;
    private Date dateOfDue;
    private Date dateOfPayment;
    private double amount;
    private boolean isPaid;
    private Long roomId;
}
