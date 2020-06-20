package buildingProject.dto;

import buildingProject.model.embeddables.BuildingExtra;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BuildingDTO {
    private Long id;
    private String buildingName;
    private String location;
    @EqualsAndHashCode.Exclude
    private List<Long> listOfLevelId = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    private Set<BuildingExtra> buildingExtraSet = new HashSet<>();

}
