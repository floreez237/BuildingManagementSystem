package buildingProject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private Date dateOfBirth;
    private String nationalIdNumber;
    private char sex;
    private String maritalStatus;
    private String phoneNumber;
    private Long roomId;
}
