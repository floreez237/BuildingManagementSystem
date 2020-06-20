package buildingProject.map_configs.property_maps.dto_to_entity.bills;

import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WaterBillDTOtoEntity extends PropertyMap<WaterBillDTO, WaterBillEntity> {
    @Autowired
    private RoomRepository roomRepository;

    private Converter<Long, RoomEntity> longRoomEntityConverter = new Converter<Long, RoomEntity>() {
        @Override
        public RoomEntity convert(MappingContext<Long, RoomEntity> context) {
            if (context.getSource() == null) {
                return null;
            } else {
                return roomRepository.getOne(context.getSource());
            }
        }
    };

    @Override
    protected void configure() {
        using(longRoomEntityConverter).map(source.getRoomId(), destination.getRoom());
    }


}
