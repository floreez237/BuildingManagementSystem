package buildingProject.map_configs.property_maps.dto_to_entity;


import buildingProject.dto.BuildingLevelDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.repositories.BuildingRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingLevelDTOtoEntity extends PropertyMap<BuildingLevelDTO, BuildingLevelEntity> {
    @Autowired
    private BuildingRepository buildingRepository;
    private Converter<Long, BuildingEntity> idBuildingEntityConverter =
            new Converter<Long, BuildingEntity>() {
                @Override
                public BuildingEntity convert(MappingContext<Long, BuildingEntity> context) {
                    return buildingRepository.getOne(context.getSource());
                }
            };

    @Override
    protected void configure() {
        using(idBuildingEntityConverter).map(source.getBuildindID(), destination.getBuilding());
    }
}
