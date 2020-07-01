package buildingProject.map_configs.property_maps.dto_to_entity.rooms;

import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.model.rooms.BedroomEntity;
import org.modelmapper.PropertyMap;

public class BedroomDTOtoEntity extends PropertyMap<BedroomDTO, BedroomEntity> {
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.isOccupied());
        skip(destination.getRent());
        skip(destination.getDeposit());
        skip(destination.getPaintColor());
        skip(destination.getLevel());
        skip(destination.getBuilding());
        skip(destination.getAdditionalRooms());
        skip(destination.getSetOfPersons());
        skip(destination.getSetOfFurniture());
        skip(destination.getWaterBills());
        skip(destination.getElectricityBills());
    }
}
