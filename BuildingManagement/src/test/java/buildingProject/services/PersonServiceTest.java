package buildingProject.services;

import buildingProject.dto.PersonDTO;
import buildingProject.model.PersonEntity;
import buildingProject.repositories.PersonRepository;
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
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelMapper mapper;


    @Test
    void deleteAllByRoomId() {
        personService.deleteAllByRoomId(1L);
        assertEquals(3,personRepository.count());
    }

    @Test
    void savePerson() {
        PersonDTO personDTO = mapper.map(personRepository.getOne(1L), PersonDTO.class);
        personDTO.setName("NJIYIM");
        personDTO.setPhoneNumber("698223844");
        personService.savePerson(personDTO);
        PersonEntity personEntity = personRepository.getOne(1L);
        assertEquals("NJIYIM", personEntity.getName());
        assertEquals("698223844",personEntity.getPhoneNumber());
    }

    @Test
    void findTenantByRoomId() {
        PersonDTO personDTO = personService.findTenantByRoomId(1L);
        assertEquals("florian lowe",personDTO.getName());

    }
}