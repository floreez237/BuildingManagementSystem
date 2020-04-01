package buildingProject.map_configs.property_maps.dto_to_entity.rooms;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.model.rooms.AppartmentEntity;
import org.modelmapper.PropertyMap;

public class AppartmentDTOtoEntity extends PropertyMap<AppartmentDTO, AppartmentEntity> {

    @Override
    protected void configure() {
        //The provider provides an instance of Appartment with Room Properties already matched
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
