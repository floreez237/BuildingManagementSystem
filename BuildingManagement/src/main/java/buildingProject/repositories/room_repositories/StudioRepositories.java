package buildingProject.repositories.room_repositories;

import buildingProject.model.rooms.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepositories extends JpaRepository<StudioEntity, Long> {
}
