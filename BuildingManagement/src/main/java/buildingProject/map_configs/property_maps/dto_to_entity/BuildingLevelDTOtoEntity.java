package buildingProject.map_configs.property_maps.dto_to_entity;


import buildingProject.dto.BuildingLevelDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.repositories.BuildingRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class BuildingLevelDTOtoEntity extends PropertyMap<BuildingLevelDTO, BuildingLevelEntity> {
    private final BuildingRepository buildingRepository;
    private final Converter<Long, BuildingEntity> idBuildingEntityConverter =
            new Converter<Long, BuildingEntity>() {
                @Override
                public BuildingEntity convert(MappingContext<Long, BuildingEntity> context) {
                    return buildingRepository.getOne(context.getSource());
                }
            };

    public BuildingLevelDTOtoEntity(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    protected void configure() {
        using(idBuildingEntityConverter).map(source.getBuildingID(), destination.getBuilding());
    }
}
