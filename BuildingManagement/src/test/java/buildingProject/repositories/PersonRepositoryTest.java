package buildingProject.repositories;

import buildingProject.model.PersonEntity;
import buildingProject.repositories.PersonRepository.IdOnly;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
@ComponentScan(basePackages = {"buildingProject.map_configs"})
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ContractRepository contractRepository;

    @Test
    void findAllByRoomId() {
        List<IdOnly> idOnlyList = personRepository.findAllByRoomId(1L);
        assertEquals(2, idOnlyList.size());
        assertEquals(1L, idOnlyList.get(0).getId());
    }

    @Test
    void deleteAllByRoomId() {
        //first delete all associated contracts
        List<IdOnly> idOnlyList = personRepository.findAllByRoomId(1L);
        idOnlyList.forEach(idOnly -> contractRepository.deleteAllByTenantId(idOnly.getId()));

        personRepository.deleteAllByRoomId(1L);
        assertEquals(3,personRepository.count());
    }

    @Test
    void getAllByRoomId() {
        assertEquals(2,personRepository.getAllByRoomId(1L).size());
    }

    @Test
    void findTenantByRoomId() {
        PersonEntity personEntity = personRepository.findTenantByRoomId(1L);
        assertEquals(1L, personEntity.getId());
        assertEquals("florian lowe",personEntity.getName());
    }

}