package buildingProject.services;

import buildingProject.dto.BuildingLevelDTO;
import buildingProject.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
@ComponentScan(basePackages = {"buildingProject.map_configs","buildingProject.services"})
class BuildingLevelServiceTest {

    @Autowired
    private BuildingLevelService buildingLevelService;

    @Test
    void getLevelDto() {
        BuildingLevelDTO dto = buildingLevelService.getLevelDto(1L);
        assertEquals(1L, dto.getId());
        assertEquals(1, dto.getLevelNumber());
        assertEquals(1L,dto.getBuildingID());
    }

    @Test
    void findAllPersons() {
        Set<PersonDTO> set = buildingLevelService.findAllPersons(1L);
        assertEquals(2,set.size());
    }
}