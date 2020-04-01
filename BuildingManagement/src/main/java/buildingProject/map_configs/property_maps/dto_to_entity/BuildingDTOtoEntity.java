package buildingProject.map_configs.property_maps.dto_to_entity;

import buildingProject.dto.BuildingDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.repositories.BuildingLevelRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingDTOtoEntity extends PropertyMap<BuildingDTO, BuildingEntity> {
    @Autowired
    private BuildingLevelRepository buildingLevelRepository;
    private Converter<List<Long>, List<BuildingLevelEntity>> idBuildinLevelListConverter
            = new Converter<List<Long>, List<BuildingLevelEntity>>() {
        @Override
        public List<BuildingLevelEntity> convert(MappingContext<List<Long>, List<BuildingLevelEntity>> context) {
            return context.getSource().stream().map((id) -> buildingLevelRepository.getOne(id))
                    .collect(Collectors.toList());
        }
    };

    @Override
    protected void configure() {
        using(idBuildinLevelListConverter).map(source.getListOfLevelId(), destination.getListOfLevels());
    }
}
