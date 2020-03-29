package buildingProject.repositories.billRepositories;

import buildingProject.model.bills.WaterBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface waterRepository extends JpaRepository<WaterBillEntity, Long> {

}
