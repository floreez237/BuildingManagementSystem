package buildingProject.repositories;

import buildingProject.model.BuildingLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingLevelRepository extends JpaRepository<BuildingLevelEntity, Long> {
}
