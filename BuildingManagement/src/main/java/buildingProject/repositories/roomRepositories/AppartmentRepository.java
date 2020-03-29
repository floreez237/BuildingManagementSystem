package buildingProject.repositories.roomRepositories;

import buildingProject.model.rooms.AppartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppartmentRepository extends JpaRepository<AppartmentEntity, Long> {
}
