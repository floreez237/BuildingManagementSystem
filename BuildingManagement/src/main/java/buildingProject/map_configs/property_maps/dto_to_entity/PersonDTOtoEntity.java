package buildingProject.map_configs.property_maps.dto_to_entity;

import buildingProject.dto.PersonDTO;
import buildingProject.model.PersonEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDTOtoEntity extends PropertyMap<PersonDTO, PersonEntity> {
    @Autowired
    private RoomRepository roomRepository;
    private Converter<Long, RoomEntity> idRoomEntityConverter
            = new Converter<Long, RoomEntity>() {
        @Override
        public RoomEntity convert(MappingContext<Long, RoomEntity> context) {
            return roomRepository.getOne(context.getSource());
        }
    };

    @Override
    protected void configure() {
        using(idRoomEntityConverter).map(source.getRoomId(), destination.getRoom());
    }


}
