package buildingProject.map_configs;

import buildingProject.dto.BuildingDTO;
import buildingProject.dto.BuildingLevelDTO;
import buildingProject.dto.ContractDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.ContractEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.embeddables.Furniture;
import buildingProject.model.rooms.AppartmentEntity;
import buildingProject.model.rooms.BedroomEntity;
import buildingProject.model.rooms.StudioEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.format_sql=true",
        "logging.level.org.hibernate.SQL=DEBUG",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ComponentScan(basePackages = {"buildingProject.map_configs"})
class CustomMapperDTOtoETest {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntityManager entityManager;

    @Test
    void electricityBillEntityTest() {
        ElectricityBillDTO electricityBillDTO = new ElectricityBillDTO();
        electricityBillDTO.setId(1L);
        electricityBillDTO.setDateOfPayment(LocalDate.now());
        electricityBillDTO.setRoomId(2L);
        electricityBillDTO.setAmount(45000);

        ElectricityBillEntity billEntity = modelMapper.map(electricityBillDTO, ElectricityBillEntity.class);
        assertEquals(billEntity.getDateOfPayment(), electricityBillDTO.getDateOfPayment());
        assertEquals(electricityBillDTO.getAmount(), billEntity.getAmount());
        assertEquals(electricityBillDTO.getRoomId(), billEntity.getRoom().getId());
        assertEquals(electricityBillDTO.getAmount(), billEntity.getAmount(), 1E-14);
    }

    @Test
    void waterBillEntityTest() {
        WaterBillDTO billDTO = new WaterBillDTO();
        billDTO.setId(1L);
        billDTO.setRoomId(1L);
        billDTO.setDateOfIssue(LocalDate.now());
        billDTO.setAmount(48000);

        WaterBillEntity billEntity = modelMapper.map(billDTO, WaterBillEntity.class);
        assertEquals(billDTO.getId(), billEntity.getId());
        assertEquals(billDTO.getRoomId(), billEntity.getRoom().getId());
        assertEquals(billDTO.getDateOfIssue(), billEntity.getDateOfIssue());
        assertEquals(billDTO.getAmount(), billEntity.getAmount(), 1E-14);

    }

    @Test
    void appartmentDTOTest() {
        AppartmentDTO dto = new AppartmentDTO();
        Furniture furniture = new Furniture();
        furniture.setName("desk");
        dto.setId(2L);
        dto.setPaintColor("blue");
        dto.getListOfElectricityBillIds().add(1L);
        dto.getListOfPersonIds().add(1L);
        dto.setBuildingID(1L);
        dto.getSetOfFurniture().add(furniture);

        AppartmentEntity entity = modelMapper.map(dto, AppartmentEntity.class);
        List<ElectricityBillEntity> billEntityList = new ArrayList<>(entity.getElectricityBills());
        List<PersonEntity> personEntityList = new ArrayList<>(entity.getSetOfPersons());
        List<Furniture> furnitureList = new ArrayList<>(entity.getSetOfFurniture());
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getPaintColor(), entity.getPaintColor());
        assertEquals(1, entity.getElectricityBills().size());
        assertTrue(billEntityList.get(0).isPaid());
        assertEquals(1, entity.getSetOfPersons().size());
        assertEquals("florian lowe", personEntityList.get(0).getName());
        assertEquals(1, entity.getSetOfFurniture().size());
        assertEquals("desk", furnitureList.get(0).getName());
        assertEquals("florian", entity.getBuilding().getBuildingName());
    }

    @Test
    void studioEntityTest() {
        StudioDTO dto = new StudioDTO();
        dto.setId(1L);
        dto.setBuildingID(1L);
        dto.setBuildingLevelId(1L);
        dto.setAreaOfBedroom(10);
        dto.getListOfWaterBillIDs().add(1L);

        StudioEntity entity = modelMapper.map(dto, StudioEntity.class);
        List<WaterBillEntity> waterBillEntityList = new ArrayList<>(entity.getWaterBills());
        assertEquals(dto.getId(), entity.getId());
        assertEquals("florian", entity.getBuilding().getBuildingName());
        assertEquals(1, entity.getLevel().getLevelNumber());
        assertEquals(dto.getAreaOfBedroom(), entity.getAreaOfBedroom(), 1E-14);
        assertFalse(waterBillEntityList.get(0).isPaid());

    }

    @Test
    void bedroomEntityTest() {
        BedroomDTO dto = new BedroomDTO();
        dto.setId(1L);
        dto.setToiletInternal(true);
        dto.setArea(10);

        BedroomEntity entity = modelMapper.map(dto, BedroomEntity.class);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.isToiletInternal(), entity.isToiletInternal());
        assertEquals(dto.getArea(), entity.getArea(), 1E-14);
    }

    @Test
    void buildingEntityTest() {
        BuildingDTO dto = new BuildingDTO();
        dto.setId(1L);
        dto.getListOfLevelId().add(2L);
        dto.setLocation("logpom");

        BuildingEntity entity = modelMapper.map(dto, BuildingEntity.class);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getLocation(), entity.getLocation());
        assertEquals(2, entity.getListOfLevels().get(0).getLevelNumber());

    }

    @Test
    void buildingLevelEntityTest() {
        BuildingLevelDTO dto = new BuildingLevelDTO();
        dto.setId(1L);
        dto.setBuildingID(2L);

        BuildingLevelEntity entity = modelMapper.map(dto, BuildingLevelEntity.class);
        assertEquals(dto.getId(), entity.getId());
        assertEquals("yasmine", entity.getBuilding().getBuildingName());
    }

    @Test
    void contractEntityTest() {
        ContractDTO dto = new ContractDTO();
        dto.setId(1L);
        dto.setDateOfPayment(LocalDate.now());
        dto.setRoomId(3L);
        dto.setTenantId(2L);

        ContractEntity entity = modelMapper.map(dto, ContractEntity.class);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getDateOfPayment(), entity.getDateOfPayment());
        assertTrue(entity.getRoom().isOccupied());
        assertEquals("bouloulna yasmine", entity.getTenant().getName());
    }

    @Test
    void personEntityTest() {
        PersonDTO dto = new PersonDTO();
        dto.setDateOfBirth(LocalDate.now());
        dto.setRoomId(1L);
        dto.setName("florian");

        PersonEntity entity = modelMapper.map(dto, PersonEntity.class);
        assertEquals(dto.getDateOfBirth(), entity.getDateOfBirth());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(2000, entity.getRoom().getDeposit(), 1E-14);
    }

}
