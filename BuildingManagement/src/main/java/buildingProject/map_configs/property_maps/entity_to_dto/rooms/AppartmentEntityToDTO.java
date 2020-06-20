package buildingProject.map_configs.property_maps.entity_to_dto.rooms;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.model.rooms.AppartmentEntity;
import org.modelmapper.PropertyMap;

public class AppartmentEntityToDTO extends PropertyMap<AppartmentEntity, AppartmentDTO> {
    //the provider gives an instance of appartment in which all room fields have been mapped
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.isOccupied());
        skip(destination.getRent());
        skip(destination.getDeposit());
        skip(destination.getPaintColor());
        skip(destination.getBuildingID());
        skip(destination.getBuildingLevelId());
        skip(destination.getAdditionalRooms());
        skip(destination.getListOfPersonIds());
        skip(destination.getSetOfFurniture());
        skip(destination.getListOfWaterBillIDs());
        skip(destination.getListOfElectricityBillIds());

    }
}
