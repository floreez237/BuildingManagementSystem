package buildingProject.map_configs.property_maps.entity_to_dto;

import buildingProject.dto.BuildingDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingEntityToDTO extends PropertyMap<BuildingEntity, BuildingDTO> {
    private Converter<List<BuildingLevelEntity>, List<Long>> levelIdConverter
            = context -> context.getSource().stream().map(BuildingLevelEntity::getId).collect(Collectors.toList());

    @Override
    protected void configure() {
        using(levelIdConverter).map(source.getListOfLevels(), destination.getListOfLevelId());
    }
}
