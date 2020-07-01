package buildingProject.repositories.bill_repositories;

import buildingProject.model.bills.ElectricityBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectricityBillRepository extends JpaRepository<ElectricityBillEntity, Long> {
    void deleteAllByRoomId(Long roomId);

    List<ElectricityBillEntity> findAllByIsPaidFalse();

    Long countAllByIsPaidFalse();
}
