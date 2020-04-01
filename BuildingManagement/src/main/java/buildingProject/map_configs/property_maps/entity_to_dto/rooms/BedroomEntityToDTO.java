package buildingProject.map_configs.property_maps.entity_to_dto.rooms;

import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.model.rooms.BedroomEntity;
import org.modelmapper.PropertyMap;

public class BedroomEntityToDTO extends PropertyMap<BedroomEntity, BedroomDTO> {
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
