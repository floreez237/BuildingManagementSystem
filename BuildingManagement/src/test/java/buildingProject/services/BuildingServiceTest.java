package buildingProject.services;

import buildingProject.dto.BuildingDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.embeddables.BuildingExtra;
import buildingProject.repositories.BuildingLevelRepository;
import buildingProject.repositories.BuildingRepository;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class BuildingServiceTest {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BuildingRepository buildingRepo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BuildingLevelRepository levelRepository;
    @Autowired
    private PersonRepository personRepository;


    @Test
    void createBuilding() {
        //to delete all the buildings present in the building
        BuildingDTO dto1 = mapper.map(buildingRepo.getOne(1L),BuildingDTO.class);
        BuildingDTO dto2 = mapper.map(buildingRepo.getOne(2L),BuildingDTO.class);
        buildingService.delete(dto1);
        buildingService.delete(dto2);

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setBuildingName("florianlowe");
        buildingDTO.setLocation("Logpom");
        BuildingExtra extra1= new BuildingExtra();
        extra1.setName("swimming pool");
        buildingDTO.setBuildingExtraSet(new HashSet<>(Collections.singletonList(extra1)));
        long id = buildingService.createBuilding(buildingDTO, 3);

        //verification
        assertTrue(buildingRepo.existsById(id));
        BuildingEntity buildingEntity = buildingRepo.getOne(id);
        assertEquals(buildingDTO.getBuildingName(), buildingEntity.getBuildingName());
        assertEquals(buildingDTO.getLocation(), buildingEntity.getLocation());
        assertEquals(1,buildingEntity.getBuildingExtraSet().size());
        assertTrue(buildingEntity.getBuildingExtraSet().contains(extra1));
        assertEquals(3,buildingEntity.getListOfLevels().size());
    }

    @Test
    void save() {
        BuildingEntity buildingEntity = buildingRepo.getOne(1L);
        BuildingDTO buildingDTO = mapper.map(buildingEntity,BuildingDTO.class);
        buildingDTO.setBuildingName("paul");
        buildingDTO.setLocation("logpom");
        buildingService.save(buildingDTO);
        //verificationd

         buildingEntity = buildingRepo.getOne(1L);
        assertEquals(buildingDTO.getLocation(),buildingEntity.getLocation());
        assertEquals(buildingDTO.getBuildingName(),buildingEntity.getBuildingName());
    }

    @Test
    void getListOfBuildings(){
        List<BuildingDTO> buildingDTOList = buildingService.getListOfBuildings();
        assertEquals(buildingRepo.count(),buildingDTOList.size());
    }

    @Test
    void delete() {
        BuildingEntity buildingEntity = buildingRepo.getOne(1L);
        BuildingDTO buildingDTO = mapper.map(buildingEntity,BuildingDTO.class);
        buildingService.delete(buildingDTO);

        assertEquals(1,buildingRepo.count());
        assertEquals(1,levelRepository.count());
    }

    @Test
    void getNumberOfAvailableRooms() {
        BuildingDTO buildingDTO = mapper.map(buildingRepo.getOne(1L),BuildingDTO.class);
        assertEquals(3L,buildingService.getNumberOfAvailableRooms(buildingDTO));
    }

    @Test
    void getNumberOccupiedRooms() {
        BuildingDTO buildingDTO = mapper.map(buildingRepo.getOne(1L),BuildingDTO.class);
        assertEquals(0,buildingService.getNumberOfOccupiedRooms(buildingDTO));
    }

    @Test
    void findAllPersons() {
        BuildingDTO dto = mapper.map(buildingRepo.getOne(1L),BuildingDTO.class);
        Set<PersonDTO> personDTOS = buildingService.findAllPersons(dto);
        assertEquals(4,personDTOS.size());

        PersonDTO personDTO = mapper.map(personRepository.getOne(1L), PersonDTO.class);
        assertTrue(personDTOS.contains(personDTO));
    }
}