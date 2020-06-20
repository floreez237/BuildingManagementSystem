package buildingProject.map_configs.property_maps.dto_to_entity;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.dto.rooms.RoomDTO;
import buildingProject.dto.rooms.StudioDTO;
import buildingProject.map_configs.property_maps.dto_to_entity.bills.ElectricityBillDTOtoEntity;
import buildingProject.map_configs.property_maps.dto_to_entity.bills.WaterBillDTOtoEntity;
import buildingProject.map_configs.property_maps.dto_to_entity.rooms.AppartmentDTOtoEntity;
import buildingProject.map_configs.property_maps.dto_to_entity.rooms.BedroomDTOtoEntity;
import buildingProject.map_configs.property_maps.dto_to_entity.rooms.RoomDTOtoEntity;
import buildingProject.map_configs.property_maps.dto_to_entity.rooms.StudioDTOtoEntity;
import buildingProject.model.rooms.AppartmentEntity;
import buildingProject.model.rooms.BedroomEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.model.rooms.StudioEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOtoEntityConfig {
    @Autowired
    private BuildingLevelDTOtoEntity buildingLevelDTOtoEntity;
    @Autowired
    private RoomDTOtoEntity roomDTOtoEntity;
    @Autowired
    private ElectricityBillDTOtoEntity electricityBillDTOtoEntity;
    @Autowired
    private WaterBillDTOtoEntity waterBillDTOtoEntity;
    @Autowired
    private BuildingDTOtoEntity buildingDTOtoEntity;
    @Autowired
    private ContractDTOtoEntity contractDTOtoEntity;
    @Autowired
    private PersonDTOtoEntity personDTOtoEntity;


    public void configure(ModelMapper modelMapper) {
        modelMapper.createTypeMap(RoomDTO.class, RoomEntity.class)
                .include(AppartmentDTO.class, RoomEntity.class)
                .include(BedroomDTO.class, RoomEntity.class)
                .include(StudioDTO.class, RoomEntity.class)
                .addMappings(roomDTOtoEntity);

        modelMapper.typeMap(AppartmentDTO.class, RoomEntity.class)
                .includeBase(RoomDTO.class, RoomEntity.class)
                .setProvider(request -> new AppartmentEntity());

        modelMapper.typeMap(BedroomDTO.class, RoomEntity.class)
                .includeBase(RoomDTO.class, RoomEntity.class)
                .setProvider(request -> new BedroomEntity());

        modelMapper.typeMap(StudioDTO.class, RoomEntity.class)
                .includeBase(RoomDTO.class, RoomEntity.class)
                .setProvider(request -> new StudioEntity());


        modelMapper.typeMap(AppartmentDTO.class, AppartmentEntity.class)
                .setProvider(request -> {
                    //if an error occurs try casting the get source
                    return (AppartmentEntity) modelMapper.map(request.getSource(), RoomEntity.class);
                });

        modelMapper.typeMap(BedroomDTO.class, BedroomEntity.class)
                .setProvider(request -> (BedroomEntity) modelMapper.map(request.getSource(), RoomEntity.class));

        modelMapper.typeMap(StudioDTO.class, StudioEntity.class)
                .setProvider(request -> (StudioEntity) modelMapper.map(request.getSource(), RoomEntity.class));

        modelMapper.addMappings(electricityBillDTOtoEntity);
        modelMapper.addMappings(waterBillDTOtoEntity);
        modelMapper.addMappings(new AppartmentDTOtoEntity());
        modelMapper.addMappings(new BedroomDTOtoEntity());
        modelMapper.addMappings(new StudioDTOtoEntity());
        modelMapper.addMappings(buildingDTOtoEntity);
        modelMapper.addMappings(contractDTOtoEntity);
        modelMapper.addMappings(personDTOtoEntity);


        modelMapper.addMappings(buildingLevelDTOtoEntity);

    }
}
