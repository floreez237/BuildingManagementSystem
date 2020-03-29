package buildingProject.repositories.roomRepositories;

import buildingProject.model.rooms.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
