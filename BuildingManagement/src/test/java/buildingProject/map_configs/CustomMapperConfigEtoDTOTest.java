package buildingProject.map_configs;

import buildingProject.dto.BuildingDTO;
import buildingProject.dto.BuildingLevelDTO;
import buildingProject.dto.ContractDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.dto.rooms.RoomDTO;
import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.ContractEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.rooms.AppartmentEntity;
import buildingProject.model.rooms.BedroomEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.model.rooms.StudioEntity;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.format_sql=true",
        "logging.level.org.hibernate.SQL=DEBUG",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ComponentScan(basePackages = {"buildingProject.map_configs"})
class CustomMapperConfigEtoDTOTest {
    //it is better to use the entity manager
    //it is of no use to wire all the repositories
    @Autowired
    EntityManager entityManager;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepo;

    @BeforeEach
    void setUp() {

    }

    @Test
    void electricityBill() {
        modelMapper.validate();
        TypedQuery<ElectricityBillEntity> query = entityManager.createQuery("select b from ElectricityBillEntity b where b.id = ?1", ElectricityBillEntity.class);
        ElectricityBillEntity billEntity = query.setParameter(1, 1L).getSingleResult();
        ElectricityBillDTO billDTO = modelMapper.map(billEntity, ElectricityBillDTO.class);
        assertEquals(new Long(1), billDTO.getRoomId());
        assertEquals(new Long(1), billDTO.getId());
        assertNull(billDTO.getDateOfIssue());
        assertNull(billDTO.getDateOfPayment());
        assertTrue(billDTO.isPaid());
        assertEquals(12000, billDTO.getAmount(), 1E-14);

    }

    @Test
    void waterBill() {
        TypedQuery<WaterBillEntity> query = entityManager.createQuery("select b from WaterBillEntity b where b.id = ?1", WaterBillEntity.class);
        WaterBillEntity billEntity = query.setParameter(1, 1L).getSingleResult();
        WaterBillDTO waterBillDTO = modelMapper.map(billEntity, WaterBillDTO.class);

        assertEquals(1L, waterBillDTO.getId().longValue());
        assertEquals(15000, waterBillDTO.getAmount(), 1E-14);
        assertNull(waterBillDTO.getDateOfIssue());
        assertFalse(waterBillDTO.isPaid());
    }

    @Test
    void apartmentEntityToDO() {
        TypedQuery<AppartmentEntity> query = entityManager.createQuery("select a from AppartmentEntity a where a.id = ?1", AppartmentEntity.class);
        AppartmentEntity entity = query.setParameter(1, 2L).getSingleResult();

        AppartmentDTO dto = modelMapper.map(entity,AppartmentDTO.class);

        assertEquals(2, dto.getAreasOfBedrooms().size());
        assertEquals(1, dto.getAreasOfKitchens().size());
        assertEquals(1, dto.getAreasOfParlours().size());
        assertEquals(25000, dto.getRent(), 1E-14);
        assertFalse(dto.isOccupied());
        assertEquals(1, dto.getSetOfFurniture().size());
        assertTrue(dto.getSetOfFurniture().stream().
                anyMatch(furniture -> furniture.getName().equals("table")));
        assertEquals(1, dto.getListOfElectricityBillIds().size());
        assertEquals(1, dto.getListOfWaterBillIDs().size());
        assertEquals(1, dto.getListOfPersonIds().size());


    }

    @Test
    void bedroomEntityToDTO() {
        TypedQuery<BedroomEntity> query = entityManager.createQuery("select b from BedroomEntity b where b.id = ?1", BedroomEntity.class);
        BedroomEntity entity = query.setParameter(1, 1L).getSingleResult();
        BedroomDTO dto = modelMapper.map(entity, BedroomDTO.class);

        assertTrue(dto.isToiletInternal());
        assertEquals(1, dto.getAdditionalRooms().size());
        assertTrue(dto.getAdditionalRooms().stream().anyMatch(
                additionalRoom -> additionalRoom.getName().equals("ceiling room")
        ));
        assertEquals(45, dto.getArea(), 1E-14);
    }

    @Test
    void studioEntityToDTO() {
        TypedQuery<StudioEntity> query = entityManager.createQuery("select s from StudioEntity s where s.id = ?1", StudioEntity.class);
        StudioEntity entity = query.setParameter(1, 3L).getSingleResult();
        StudioDTO dto = modelMapper.map(entity, StudioDTO.class);

        assertEquals(35000, dto.getRent(), 1E-14);
        assertEquals(10, dto.getAreaOfToilet(), 1E-14);
        assertEquals(10, dto.getAreaOfKitchen(), 1E-14);
        assertFalse(dto.isOccupied());
    }

    @Test
    void buildingEntityToDTO() {
        TypedQuery<BuildingEntity> query = entityManager.createQuery("select b from BuildingEntity b where b.id = ?1", BuildingEntity.class);
        BuildingEntity entity = query.setParameter(1, 1L).getSingleResult();
        BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);

        assertEquals(dto.getBuildingName(), "florian");
        assertEquals(1, dto.getBuildingExtraSet().size());
        assertEquals(2, dto.getListOfLevelId().size());
    }

    @Test
    void levelEntityToDTO() {
        TypedQuery<BuildingLevelEntity> query = entityManager.createQuery("select l from BuildingLevelEntity l where l.id = ?1", BuildingLevelEntity.class);
        BuildingLevelEntity entity = query.setParameter(1, 1L).getSingleResult();
        BuildingLevelDTO dto = modelMapper.map(entity, BuildingLevelDTO.class);
        assertEquals(1L, dto.getLevelNumber().longValue());
    }

    @Test
    void personEntityToDTO() {
        TypedQuery<PersonEntity> query = entityManager.createQuery("select p from PersonEntity p where p.id = ?1", PersonEntity.class);
        PersonEntity entity = query.setParameter(1, 1L).getSingleResult();
        PersonDTO dto = modelMapper.map(entity, PersonDTO.class);

        assertEquals("florian lowe", dto.getName());
        assertEquals(1, dto.getRoomId().longValue());
        assertEquals('M', dto.getSex());
    }

    @Test
    void contractEntityToDTO() {
        TypedQuery<ContractEntity> query = entityManager.createQuery("select c from ContractEntity c  where c.id = ?1", ContractEntity.class);
        ContractEntity entity = query.setParameter(1, 1L).getSingleResult();
        ContractDTO dto = modelMapper.map(entity, ContractDTO.class);

        assertEquals(10, dto.getDuration());
        assertEquals(1, dto.getRoomId().longValue());
        assertEquals(1, dto.getTenantId().longValue());
    }

    @Test
    void roomEntityToDto() {
        TypedQuery<RoomEntity> query = entityManager.createQuery("select a from AppartmentEntity a where a.id = ?1", RoomEntity.class);
        RoomEntity entity = query.setParameter(1, 2L).getSingleResult();
        RoomDTO roomDTO = modelMapper.map(entity, RoomDTO.class);
        assertTrue(roomDTO instanceof AppartmentDTO);
    }
}

