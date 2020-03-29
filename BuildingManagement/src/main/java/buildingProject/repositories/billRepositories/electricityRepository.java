package buildingProject.repositories.billRepositories;

import buildingProject.model.bills.ElectricityBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface electricityRepository extends JpaRepository<ElectricityBillEntity, Long> {
}
