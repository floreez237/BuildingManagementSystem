package buildingProject.map_configs;

import buildingProject.map_configs.property_maps.dto_to_entity.DTOtoEntityConfig;
import buildingProject.map_configs.property_maps.entity_to_dto.EntityToDTOConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


public class CustomMapper {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    //initialiszation of inheritance type maps
    static {
        initialiseMapper();
        EntityToDTOConfig.configure(MODEL_MAPPER);
        DTOtoEntityConfig.configure(MODEL_MAPPER);

    }

    public static ModelMapper getInstance() {
        return MODEL_MAPPER;
    }

    private static void initialiseMapper() {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }


}
