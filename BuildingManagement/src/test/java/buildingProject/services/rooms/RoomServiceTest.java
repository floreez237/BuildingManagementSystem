package buildingProject.services.rooms;

import buildingProject.dto.rooms.RoomDTO;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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
@ComponentScan(basePackages = {"buildingProject.services","buildingProject.map_configs"})
class RoomServiceTest {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ModelMapper mapper;

    @Test
    void deleteAllByBuildingIdAndLevelNumber() {
        roomService.deleteAllByBuildingIdAndLevelNumber(1L,1L);
        assertEquals(3,roomRepository.count());
    }

    @Test
    void findAllInRoomsInLevel() {
        Set<RoomDTO> roomDTOS = roomService.findAllInRoomsInLevel(1L);
        assertEquals(1,roomDTOS.size());
    }

    @Test
    void deleteRoom() {
        roomService.deleteRoom(1L);
        assertEquals(3, roomRepository.count());
    }
}