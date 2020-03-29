package buildingProject.repositories.roomRepositories;

import buildingProject.model.rooms.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepositories extends JpaRepository<StudioEntity, Long> {
}
