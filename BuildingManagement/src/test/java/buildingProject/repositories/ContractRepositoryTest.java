package buildingProject.repositories;

import buildingProject.model.ContractEntity;
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
class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Test
    void deleteAllByRoomId() {
        contractRepository.deleteAllByRoomId(1L);
        assertEquals(2,contractRepository.count());
    }

    @Test
    void deleteAllByPersonId() {
        contractRepository.deleteAllByTenantId(1L);
        assertEquals(2,contractRepository.count());
    }

    @Test
    void countAllByDurationLessThan() {
        int count = contractRepository.countAllByDurationLessThan(15);
        assertEquals(2,count);
    }

    @Test
    void findByRoomId() {
        ContractEntity contractEntity = contractRepository.findByRoomId(1L);
        assertEquals(10, contractEntity.getDuration());
        assertEquals(1L,contractEntity.getId());
    }
}