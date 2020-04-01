package buildingProject.repositories.bill_repositories;

import buildingProject.model.bills.ElectricityBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricityBillRepository extends JpaRepository<ElectricityBillEntity, Long> {
}
