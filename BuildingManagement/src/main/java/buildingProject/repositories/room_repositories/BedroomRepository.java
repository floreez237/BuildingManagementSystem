package buildingProject.repositories.room_repositories;

import buildingProject.model.rooms.BedroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedroomRepository extends JpaRepository<BedroomEntity, Long> {
}
