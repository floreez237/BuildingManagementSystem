package buildingProject.model;

import buildingProject.model.rooms.RoomEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "person_table")
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_gen")
    @GenericGenerator(
            name = "person_level_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "person_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String name;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    private String nationalIdNumber;
    private char sex;
    private String maritalStatus;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;


    public PersonEntity(String name, Date dateOfBirth, char sex) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    public PersonEntity(String name, Date dateOfBirth, String nationalIdNumber, char sex, String maritalStatus, String phoneNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationalIdNumber = nationalIdNumber;
        this.sex = sex;
        this.maritalStatus = maritalStatus;
        this.phoneNumber = phoneNumber;
    }

}
