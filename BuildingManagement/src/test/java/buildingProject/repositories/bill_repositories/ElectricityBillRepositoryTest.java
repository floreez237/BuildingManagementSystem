package buildingProject.repositories.bill_repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
@ComponentScan(basePackages = {"buildingProject.map_configs"})
class ElectricityBillRepositoryTest {

    @Autowired
    private ElectricityBillRepository electricityBillRepository;

    @Test
    void deleteAllByRoomId() {
        electricityBillRepository.deleteAllByRoomId(1L);
        assertEquals(1,electricityBillRepository.count());
    }

    @Test
    void countAllIsPaidFalse() {
        long count = electricityBillRepository.countAllByIsPaidFalse();
        assertEquals(0, count);
    }
}