package buildingProject.model.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Embeddable
public class BuildingExtra implements Serializable {
    private static final long serialVersionUID = 5L;
    private String name;

}
