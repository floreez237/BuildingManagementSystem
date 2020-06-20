package buildingProject.repositories;

import buildingProject.model.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

}
