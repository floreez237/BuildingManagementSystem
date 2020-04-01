package buildingProject.map_configs.property_maps.entity_to_dto.rooms;

import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.rooms.StudioEntity;
import org.modelmapper.PropertyMap;

public class StudioEntityToDTO extends PropertyMap<StudioEntity, StudioDTO> {
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
