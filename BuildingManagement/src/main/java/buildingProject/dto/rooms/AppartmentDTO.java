package buildingProject.dto.rooms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"areasOfBedrooms", "areasOfKitchens", "areasOfParlours", "areasOfToilets"}, callSuper = true)
@NoArgsConstructor
public class AppartmentDTO extends RoomDTO {

    private List<Double> areasOfBedrooms = new ArrayList<>();
    private List<Double> areasOfKitchens = new ArrayList<>();
    private List<Double> areasOfParlours = new ArrayList<>();
    private List<Double> areasOfToilets = new ArrayList<>();
}
