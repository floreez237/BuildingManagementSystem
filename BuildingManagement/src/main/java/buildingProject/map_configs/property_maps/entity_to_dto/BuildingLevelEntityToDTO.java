package buildingProject.map_configs.property_maps.entity_to_dto;

import buildingProject.dto.BuildingLevelDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class BuildingLevelEntityToDTO extends PropertyMap<BuildingLevelEntity, BuildingLevelDTO> {
    private Converter<BuildingEntity, Long> buildingEntityLongConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();

    @Override
    protected void configure() {
        using(buildingEntityLongConverter).map(source.getBuilding(), destination.getBuildindID());
    }
}
