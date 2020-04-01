package buildingProject.map_configs.property_maps.dto_to_entity;

import buildingProject.dto.ContractDTO;
import buildingProject.model.ContractEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.PersonRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractDTOtoEntity extends PropertyMap<ContractDTO, ContractEntity> {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoomRepository roomRepository;
    private Converter<Long, PersonEntity> idPersonEntityConverter
            = new Converter<Long, PersonEntity>() {
        @Override
        public PersonEntity convert(MappingContext<Long, PersonEntity> context) {
            return personRepository.getOne(context.getSource());
        }
    };
    private Converter<Long, RoomEntity> idRoomEntityConverter
            = new Converter<Long, RoomEntity>() {
        @Override
        public RoomEntity convert(MappingContext<Long, RoomEntity> context) {
            return roomRepository.getOne(context.getSource());
        }
    };

    @Override
    protected void configure() {
        using(idPersonEntityConverter).map(source.getTenantId(), destination.getTenant());
        using(idRoomEntityConverter).map(source.getId(), destination.getRoom());
    }
}
