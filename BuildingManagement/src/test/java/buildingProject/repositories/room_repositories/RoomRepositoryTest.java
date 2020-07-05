package buildingProject.repositories.room_repositories;

import buildingProject.repositories.ContractRepository;
import buildingProject.repositories.PersonRepository;
import buildingProject.repositories.bill_repositories.ElectricityBillRepository;
import buildingProject.repositories.bill_repositories.WaterBillRepository;
import buildingProject.repositories.room_repositories.RoomRepository.IdOnly;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.format_sql=true",
        "logging.level.org.hibernate.SQL=DEBUG",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@Rollback
@Transactional
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ContractRepository contractRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private WaterBillRepository waterBillRepository;
    @Autowired
    private ElectricityBillRepository electricityBillRepository;

    @Test
    void findAllRoomIdByLevelAndBuildingId() {
        List<IdOnly> list = new ArrayList<>(roomRepository.findAllByBuildingIdAndLevelLevelNumber(1L, 1L));
        assertEquals(1,list.size());
        assertEquals(1L, list.get(0).getId());
    }

    @Test
    void deleteRoomByLevelAndBuildingId() {
        //first delete all associated contracts and persons
        long buildingId = 1,levelNumber = 1;
        Set<IdOnly> roomIdList = roomRepository.findAllByBuildingIdAndLevelLevelNumber(buildingId, levelNumber);
        roomIdList.forEach(idOnly -> {
            contractRepo.deleteAllByRoomId(idOnly.getId());
            personRepo.deleteAllByRoomId(idOnly.getId());
            waterBillRepository.deleteAllByRoomId(idOnly.getId());
            electricityBillRepository.deleteAllByRoomId(idOnly.getId());

        });
        roomRepository.deleteAllByBuildingIdAndLevelLevelNumber(buildingId,levelNumber);
        assertEquals(3,roomRepository.count());
    }

    @Test
    void countAllByLevelIdAndOccupiedFalse() {
        assertEquals(0,roomRepository.countAllByLevelIdAndOccupiedFalse(2L));
    }

    @Test
    void countAllByLevelId() {
        assertEquals(1L,roomRepository.countAllByLevelId(1L));
    }
}