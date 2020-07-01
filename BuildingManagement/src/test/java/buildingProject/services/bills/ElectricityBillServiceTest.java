package buildingProject.services.bills;

import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.repositories.bill_repositories.ElectricityBillRepository;
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
class ElectricityBillServiceTest {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ElectricityBillService billService;
    @Autowired
    private ElectricityBillRepository billRepository;

    @Test
    void save() {
        ElectricityBillDTO billDTO = new ElectricityBillDTO();
        billDTO.setRoomId(1L);
        billService.create(billDTO);
        assertEquals(2,roomRepository.getOne(1L).getElectricityBills().size());
    }
}