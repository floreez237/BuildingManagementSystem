
package buildingProject.map_configs.property_maps.dto_to_entity.rooms;

import buildingProject.dto.rooms.RoomDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.BuildingLevelRepository;
import buildingProject.repositories.BuildingRepository;
import buildingProject.repositories.PersonRepository;
import buildingProject.repositories.bill_repositories.ElectricityBillRepository;
import buildingProject.repositories.bill_repositories.WaterBillRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomDTOtoEntity extends PropertyMap<RoomDTO, RoomEntity> {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WaterBillRepository waterBillRepository;
    @Autowired
    private ElectricityBillRepository electricityBillRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingLevelRepository buildingLevelRepository;
    private Converter<List<Long>, Set<PersonEntity>> idPersonConverter =
            new Converter<List<Long>, Set<PersonEntity>>() {
                @Override
                @Transactional
                public Set<PersonEntity> convert(MappingContext<List<Long>, Set<PersonEntity>> context) {
                    //return set of person entities
                    return context.getSource().stream().map((id) -> personRepository.getOne(id)).collect(Collectors.toSet());
                }
            };
    private Converter<List<Long>, Set<WaterBillEntity>> idWaterBillConverter =
            new Converter<List<Long>, Set<WaterBillEntity>>() {
                @Override
                @Transactional
                public Set<WaterBillEntity> convert(MappingContext<List<Long>, Set<WaterBillEntity>> context) {
                    return context.getSource().stream().map(id -> waterBillRepository.getOne(id)).collect(Collectors.toSet());
                }
            };
    private Converter<List<Long>, Set<ElectricityBillEntity>> idElectricityBillConverter =
            new Converter<List<Long>, Set<ElectricityBillEntity>>() {
                @Override
                @Transactional
                public Set<ElectricityBillEntity> convert(MappingContext<List<Long>, Set<ElectricityBillEntity>> context) {
                    return context.getSource().stream().map((id) -> electricityBillRepository.getOne(id)).collect(Collectors.toSet());
                }
            };
    private Converter<Long, BuildingEntity> idBuildingEntityConverter =
            new Converter<Long, BuildingEntity>() {
                @Override
                public BuildingEntity convert(MappingContext<Long, BuildingEntity> context) {
                    return buildingRepository.getOne(context.getSource());
                }
            };
    private Converter<Long, BuildingLevelEntity> idBuildingLevelEntityConverter =
            new Converter<Long, BuildingLevelEntity>() {
                @Override
                public BuildingLevelEntity convert(MappingContext<Long, BuildingLevelEntity> context) {
                    return buildingLevelRepository.getOne(context.getSource());
                }
            };

    @Override
    protected void configure() {
        using(idPersonConverter).map(source.getListOfPersonIds(), destination.getSetOfPersons());
        using(idWaterBillConverter).map(source.getListOfWaterBillIDs(), destination.getWaterBills());
        using(idElectricityBillConverter).map(source.getListOfElectricityBillIds(), destination.getElectricityBills());
        using(idBuildingEntityConverter).map(source.getBuildingID(), destination.getBuilding());
        using(idBuildingLevelEntityConverter).map(source.getBuildingLevelId(), destination.getLevel());
    }
}
