package buildingProject.repositories.bill_repositories;

import buildingProject.model.bills.WaterBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaterBillRepository extends JpaRepository<WaterBillEntity, Long> {

    void deleteAllByRoomId(Long roomId);

    List<WaterBillEntity> findAllByIsPaidFalse();

    Long countAllByIsPaidFalse();
}
