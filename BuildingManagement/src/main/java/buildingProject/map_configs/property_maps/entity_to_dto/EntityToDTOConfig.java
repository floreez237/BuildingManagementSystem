package buildingProject.map_configs.property_maps.entity_to_dto;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.dto.rooms.RoomDTO;
import buildingProject.dto.rooms.StudioDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.bills.ElectricityBillEntityToDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.bills.WaterBillEntitytoDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.rooms.AppartmentEntityToDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.rooms.BedroomEntityToDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.rooms.RoomEntityToDTO;
import buildingProject.map_configs.property_maps.entity_to_dto.rooms.StudioEntityToDTO;
import buildingProject.model.rooms.AppartmentEntity;
import buildingProject.model.rooms.BedroomEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.model.rooms.StudioEntity;
import org.modelmapper.ModelMapper;

public class EntityToDTOConfig {
    public static void configure(ModelMapper modelMapper) {
        modelMapper.createTypeMap(RoomEntity.class, RoomDTO.class)
                .include(AppartmentEntity.class, RoomDTO.class)
                .include(BedroomEntity.class, RoomDTO.class)
                .include(StudioEntity.class, RoomDTO.class)
                .addMappings(new RoomEntityToDTO());

        modelMapper.typeMap(AppartmentEntity.class, RoomDTO.class)
                .setProvider(request -> new AppartmentDTO());

        modelMapper.typeMap(BedroomEntity.class, RoomDTO.class)
                .setProvider(request -> new BedroomDTO());

        modelMapper.typeMap(StudioEntity.class, RoomDTO.class)
                .setProvider(request -> new StudioDTO());

        //try casting request source if error occurs
        modelMapper.typeMap(AppartmentEntity.class, AppartmentDTO.class)
                .setProvider(request -> (AppartmentDTO) modelMapper.map(request.getSource(), RoomDTO.class));

        modelMapper.typeMap(StudioEntity.class, StudioDTO.class)
                .setProvider(request -> (StudioDTO) modelMapper.map(request.getSource(), RoomDTO.class));

        modelMapper.typeMap(BedroomEntity.class, BedroomDTO.class)
                .setProvider(request -> (BedroomDTO) modelMapper.map(request.getSource(), RoomDTO.class));

        modelMapper.addMappings(new ElectricityBillEntityToDTO());
        modelMapper.addMappings(new WaterBillEntitytoDTO());
        modelMapper.addMappings(new AppartmentEntityToDTO());
        modelMapper.addMappings(new BedroomEntityToDTO());
        modelMapper.addMappings(new StudioEntityToDTO());
        modelMapper.addMappings(new BuildingEntityToDTO());
        modelMapper.addMappings(new BuildingLevelEntityToDTO());
        modelMapper.addMappings(new ContractEntityToDTO());
        modelMapper.addMappings(new PersonEntityToDTO());

    }
}
