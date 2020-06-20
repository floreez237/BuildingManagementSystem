package buildingProject.repositories;

import buildingProject.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<IdOnly> findAllByRoomId(Long roomId);

    void deleteAllByRoomId(Long roomId);

    Set<PersonEntity> getAllByRoomId(Long roomId);

    @Query("select c.tenant from ContractEntity c where c.room.id = ?1")
    PersonEntity findTenantByRoomId(Long roomId);

    interface IdOnly{
        Long getId();
    }
}
