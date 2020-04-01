package buildingProject.map_configs.property_maps.entity_to_dto.bills;

import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.model.rooms.RoomEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class WaterBillEntitytoDTO extends PropertyMap<WaterBillEntity, WaterBillDTO> {
    private Converter<RoomEntity, Long> entityLongConverter = context -> context.getSource() == null ? null : context.getSource().getId();

    @Override
    protected void configure() {
        using(entityLongConverter).map(source.getRoom(), destination.getId());
    }
}
