package buildingProject.dto.rooms;

import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.model.embeddables.Furniture;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public abstract class RoomDTO {
    private Long id;
    private boolean occupied;
    private double rent;
    private double deposit;
    private String paintColor;
    private Long buildingID;
    private Long buildingLevelId;

    @EqualsAndHashCode.Exclude
    private Set<AdditionalRoom> additionalRooms = new HashSet<>();
    @EqualsAndHashCode.Exclude
    private List<Long> listOfPersonIds = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    private Set<Furniture> setOfFurniture = new HashSet<>();
    @EqualsAndHashCode.Exclude
    private List<Long> listOfWaterBillIDs = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    private List<Long> listOfElectricityBillIds = new ArrayList<>();
}
