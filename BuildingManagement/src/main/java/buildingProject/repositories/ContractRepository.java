package buildingProject.repositories;

import buildingProject.model.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

    void deleteAllByRoomId(Long roomId);

    void deleteAllByTenantId(Long personId);

    int countAllByDurationLessThan(int duration);

    ContractEntity findByRoomId(Long roomId);

    List<ContractEntity> findAllByIsObsoleteIsFalse();

    List<ContractEntity> findAllByIsObsoleteIsTrue();
}
