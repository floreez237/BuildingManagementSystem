package buildingProject.repositories.room_repositories;

import buildingProject.model.rooms.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Set<IdOnly> findAllByBuildingIdAndLevelLevelNumber(Long buildingId, Long levelNumber);

    Set<RoomEntity> findAllByLevelId(Long levelId);

    void deleteAllByBuildingIdAndLevelLevelNumber(Long buildingId, Long levelNumber);

    Long countAllByLevelIdAndOccupiedFalse(Long levelId);

    Long countAllByLevelId(Long levelId);

    Long countAllByOccupiedFalse();

    List<RoomEntity> findAllByOccupiedFalse();

    interface IdOnly{
        Long getId();
    }
}
