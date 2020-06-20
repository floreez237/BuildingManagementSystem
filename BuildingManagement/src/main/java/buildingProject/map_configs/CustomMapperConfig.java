package buildingProject.map_configs;

import buildingProject.map_configs.property_maps.dto_to_entity.DTOtoEntityConfig;
import buildingProject.map_configs.property_maps.entity_to_dto.EntityToDTOConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class CustomMapperConfig {
    private final ModelMapper modelMapper = new ModelMapper();
    private final DTOtoEntityConfig dtOtoEntityConfig;
    private final EntityToDTOConfig entityToDTOConfig;

    public CustomMapperConfig(DTOtoEntityConfig dtOtoEntityConfig, EntityToDTOConfig entityToDTOConfig) {
        this.dtOtoEntityConfig = dtOtoEntityConfig;
        this.entityToDTOConfig = entityToDTOConfig;
    }

    @Bean
    @Scope("singleton")
    public ModelMapper modelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        entityToDTOConfig.configure(modelMapper);
        dtOtoEntityConfig.configure(modelMapper);
        return modelMapper;
    }


}
