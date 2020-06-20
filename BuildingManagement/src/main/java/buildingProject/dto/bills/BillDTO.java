package buildingProject.dto.bills;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class BillDTO {
    private Long id;
    private LocalDate dateOfIssue;
    private LocalDate dateOfDue;
    private LocalDate dateOfPayment;
    private double amount;
    private boolean isPaid;
    private Long roomId;
}
