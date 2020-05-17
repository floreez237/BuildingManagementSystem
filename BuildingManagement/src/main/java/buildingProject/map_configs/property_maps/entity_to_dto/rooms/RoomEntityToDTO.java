package buildingProject.map_configs.property_maps.entity_to_dto.rooms;

import buildingProject.dto.rooms.RoomDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.rooms.RoomEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomEntityToDTO extends PropertyMap<RoomEntity, RoomDTO> {

    @Override
    protected void configure() {
        using(buildingEntityIdConverter).map(source.getBuilding(), destination.getBuildingID());
        using(levelEntityIdConverter).map(source.getLevel(), destination.getBuildingLevelId());
        using(personIdConverter).map(source.getSetOfPersons(), destination.getListOfPersonIds());
        using(waterBillIdConverter).map(source.getWaterBills(), destination.getListOfWaterBillIDs());
        using(electricityBillConverter).map(source.getElectricityBills(), destination.getListOfElectricityBillIds());
    }

    private final Converter<BuildingEntity, Long> buildingEntityIdConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();
    private final Converter<BuildingLevelEntity, Long> levelEntityIdConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();
    private final Converter<Set<PersonEntity>, List<Long>> personIdConverter
            = context -> context.getSource().stream().map(PersonEntity::getId).collect(Collectors.toList());
    private final Converter<Set<WaterBillEntity>, List<Long>> waterBillIdConverter
            = context -> context.getSource().stream().map(WaterBillEntity::getId).collect(Collectors.toList());
    private final Converter<Set<ElectricityBillEntity>, List<Long>> electricityBillConverter
            = context -> context.getSource().stream().map(ElectricityBillEntity::getId).collect(Collectors.toList());

}
