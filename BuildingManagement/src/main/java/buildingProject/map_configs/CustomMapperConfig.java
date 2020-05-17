package buildingProject.map_configs;

import buildingProject.map_configs.property_maps.dto_to_entity.DTOtoEntityConfig;
import buildingProject.map_configs.property_maps.entity_to_dto.EntityToDTOConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class CustomMapperConfig {
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private DTOtoEntityConfig dtOtoEntityConfig;
    @Autowired
    private EntityToDTOConfig entityToDTOConfig;

    //initialiszation of inheritance type maps

    @Bean
    @Scope("singleton")
    public ModelMapper modelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        entityToDTOConfig.configure(modelMapper);
        dtOtoEntityConfig.configure(modelMapper);
        return modelMapper;
    }


}
