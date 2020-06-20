package buildingProject.map_configs.property_maps.entity_to_dto;

import buildingProject.dto.ContractDTO;
import buildingProject.model.ContractEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.rooms.RoomEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class ContractEntityToDTO extends PropertyMap<ContractEntity, ContractDTO> {
    private Converter<RoomEntity, Long> roomEntityIdConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();
    private Converter<PersonEntity, Long> personEntityIdConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();

    @Override
    protected void configure() {
        using(roomEntityIdConverter).map(source.getRoom(), destination.getRoomId());
        using(personEntityIdConverter).map(source.getTenant(), destination.getTenantId());
    }
}
