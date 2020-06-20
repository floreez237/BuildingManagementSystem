package buildingProject.dto.rooms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BedroomDTO extends RoomDTO {
    private boolean isToiletInternal;
    private double area;
}
