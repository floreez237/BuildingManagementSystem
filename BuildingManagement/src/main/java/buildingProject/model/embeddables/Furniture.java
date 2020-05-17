package buildingProject.model.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Furniture implements Serializable {
    private static final long serialVersionUID = 6L;
    private String name;
}
