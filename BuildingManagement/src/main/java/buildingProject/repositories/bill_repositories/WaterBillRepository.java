package buildingProject.repositories.bill_repositories;

import buildingProject.model.bills.WaterBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterBillRepository extends JpaRepository<WaterBillEntity, Long> {

}
