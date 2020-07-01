package buildingProject.map_configs.property_maps.entity_to_dto;

import buildingProject.dto.PersonDTO;
import buildingProject.model.PersonEntity;
import buildingProject.model.rooms.RoomEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class PersonEntityToDTO extends PropertyMap<PersonEntity, PersonDTO> {
    private Converter<RoomEntity, Long> roomEntityIdConverter
            = context -> context.getSource() == null ? null : context.getSource().getId();

    @Override
    protected void configure() {
        using(roomEntityIdConverter).map(source.getRoom(), destination.getRoomId());
    }
}
