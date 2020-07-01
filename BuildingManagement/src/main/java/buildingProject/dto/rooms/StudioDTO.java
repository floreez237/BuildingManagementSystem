package buildingProject.dto.rooms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudioDTO extends RoomDTO {
    private double areaOfBedroom;
    private double areaOfToilet;
    private double areaOfKitchen;
    private double areaOfParlour;
}
